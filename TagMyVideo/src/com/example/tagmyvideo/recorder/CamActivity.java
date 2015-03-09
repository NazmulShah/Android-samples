package com.example.tagmyvideo.recorder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tagmyvideo.R;
import com.example.tagmyvideo.db.dao.TagDAO;
import com.example.tagmyvideo.db.dao.VideoDAO;
import com.example.tagmyvideo.db.dao.impl.TagDAOImpl;
import com.example.tagmyvideo.db.dao.impl.VideoDAOImpl;
import com.example.tagmyvideo.db.model.Tag;
import com.example.tagmyvideo.db.model.Video;
import com.example.tagmyvideo.utils.UpdateThumbnailTask;
import com.example.tagmyvideo.utils.Utilities;

/**
 * 
 * This activity is for record video and bookmark at the time of recording.
 * 
 * @author Prashant Prabhakar
 * 
 */
public class CamActivity extends Activity implements OnTouchListener,
		SurfaceHolder.Callback, OnInfoListener, OnErrorListener {

	// tag to show in the log
	public static final String TAG = "VIDEO RECORDER";

	// media-recorder to record the video.
	private MediaRecorder recorder;

	// surface holder to show the camera preview.
	private SurfaceHolder holder;

	// recording status
	boolean recording = false;

	// recorder state ready or released.
	boolean recorderState = false;

	// starting time-stamp of the recording
	private long startTime;

	// to show the record time
	private TextView recordTime;

	// maximum recording time.
	private int maxRecordTime;

	// maximum recording size in bytes
	private int maxRecordSize;

	// file object to store the recorded file.
	private File mediafile;

	// video_id of the recorded video.
	private int videoId;

	// Handler to create timer.
	private Handler mHandler = new Handler();

	private VideoDAO videoDao;

	private TagDAO tagDao;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		maxRecordTime = 50000;
		maxRecordSize = 5000000;

		recorder = new MediaRecorder();
		initRecorder();
		setContentView(R.layout.activity_cam);

		SurfaceView cameraView = (SurfaceView) findViewById(R.id.camera_view);
		holder = cameraView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		View captureButton = (View) findViewById(R.id.capture);
		View bookmarkButton = (View) findViewById(R.id.bookmark);

		bookmarkButton.setOnTouchListener(this);
		captureButton.setOnTouchListener(this);

		recordTime = (TextView) findViewById(R.id.record_timer);
		videoDao = new VideoDAOImpl(this);
		tagDao = new TagDAOImpl(this);
		videoId = -1;
	}

	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * videoDao.open(); tagDao.open();
		 */
	}

	@Override
	protected void onStop() {
		super.onStop();
		/*
		 * videoDao.close(); tagDao.close();
		 */
	}

	@Override
	public void onBackPressed() {
		if (recording)
			return;
		closeRecorder();
		super.onBackPressed();
	}

	/**
	 * This is used to initialize the camera for recording.
	 */
	private void initRecorder() {
		recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

		CamcorderProfile cpHigh = CamcorderProfile
				.get(CamcorderProfile.QUALITY_HIGH);
		recorder.setProfile(cpHigh);
		mediafile = getOutputMediaFile();
		recorder.setOutputFile(mediafile.toString());
		recorder.setMaxDuration(maxRecordTime); // 50 seconds
		recorder.setMaxFileSize(maxRecordSize); // Approximately 5 megabytes
		recorder.setOnInfoListener(this);
		recorder.setOnErrorListener(this);
		recorderState = true;
	}

	/**
	 * This is used to prepare the recorder.
	 */
	private void prepareRecorder() {
		recorder.setPreviewDisplay(holder.getSurface());

		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			finish();
		} catch (IOException e) {
			e.printStackTrace();
			finish();
		}
	}

	private void startRecording() {
		startTime = SystemClock.uptimeMillis();
		startTimer();
		recorder.start();
		Log.v(TAG, "Recording Started");

		// saving video to database.
		Video video = new Video();
		Log.d(TAG, mediafile.getPath());
		video.setFileName(mediafile.getName());
		video.setFrequency(0);
		video.setPath(mediafile.getPath());
		video.setIsBookmarked(false);
		video.setIsSync(-1);
		video.setLength(mediafile.length());
		videoDao.open();
		videoId = videoDao.createVideo(video);
		videoDao.close();
		((ImageView) findViewById(R.id.capture))
				.setImageDrawable(getResources().getDrawable(
						R.drawable.record_stop));
		recording = true;
	}

	/**
	 * This is used to close the recorder.
	 */
	private void closeRecorder() {
		if (recorderState) {
			recorderState = !recorderState;
			if (recording) {
				recorder.stop();
				stopTimer();
				videoDao.open();
				videoDao.updateLength(videoId, mediafile.length());
				videoDao.close();
				recording = false;
				Log.v(TAG, "Recording compeleted.");
			}
			recorder.release();
		}
		removeUnrecodedFile();
		Log.d("MediaFile absolute Path", "Video id " + videoId + "::"
				+ mediafile.getAbsolutePath());
		if (videoId != -1) {
			UpdateThumbnailTask updateTask = new UpdateThumbnailTask(this,
					videoId, mediafile);
			updateTask.execute();
		}
		finish();
	}

	/**
	 * This is used to remove 0byte file.
	 */
	private void removeUnrecodedFile() {
		if (mediafile != null && mediafile.length() == 0) {
			Log.d(TAG, mediafile.getName() + " deleted.");
			mediafile.delete();
		}
	}

	/*
	 * to handle touch of control buttons. (non-Javadoc) (non-Javadoc)
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (v.getId()) {
		case R.id.capture:
			if (recording) {
				closeRecorder();

			} else {
				startRecording();
			}
			break;
		case R.id.bookmark:
			if (recording) {
				saveBookmark(calculateMarkTime().intValue(), videoId);
			}
			break;
		}
		return false;
	}

	/**
	 * calculate the mark time relative to start-time.
	 * 
	 * @return {@link Long} mark-time in Mili-Seconds.
	 */
	public Long calculateMarkTime() {
		return SystemClock.uptimeMillis() - startTime;
	}

	/*
	 * to capture the surface view creation event. (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder
	 * )
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		Log.v(TAG, "surfaceCreated");
		prepareRecorder();
	}

	/*
	 * to capture the surface view changing event. (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder
	 * , int, int, int)
	 */
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/*
	 * to capture the surface view removal event. (non-Javadoc)
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.
	 * SurfaceHolder)
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v(TAG, "surfaceDestroyed");
		closeRecorder();
	}

	/**
	 * This is used to start the record timer on recording screen.
	 */
	private void startTimer() {
		recordTime.setVisibility(View.VISIBLE);
		mHandler.removeCallbacks(mUpdateTimeTask);
		mHandler.postDelayed(mUpdateTimeTask, 100);
	}

	/**
	 * This is used to stop the record timer.
	 */
	private void stopTimer() {
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	/**
	 * Thread to update the time on the recording screen.
	 */
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			final long start = startTime;
			long millis = SystemClock.uptimeMillis() - start;
			recordTime.setText((new Utilities()).milliSecondsToTimer(millis));
			mHandler.postAtTime(this, start + millis + 1000);
		}
	};

	/**
	 * This is used to set the out file of recorded video.
	 * 
	 * @return {@link File} object for recording file.
	 */
	private File getOutputMediaFile() {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			return null;

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
				"tagmyvideo");

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(TAG, "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;

		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "VID_" + timeStamp + ".mp4");

		return mediaFile;
	}

	/**
	 * It is used to save the bookmarks into database.
	 * 
	 * @param bookmarkTime
	 *            time of the bookmark in milisecond.
	 * @param videoId
	 *            id of the video to be saved.
	 */
	private void saveBookmark(int bookmarkTime, int videoId) {
		Tag tag = new Tag();
		tag.setCaption("chapter");
		tag.setBookmarkTime(bookmarkTime);
		tag.setVideo_id(videoId);
		tag.setCreationTime(new Date());
		tag.setFrequency(0);
		tagDao.open();
		tagDao.createTag(tag);
		tagDao.close();
	}

	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
		Log.d(TAG, "info :: " + what + " extra :: " + extra);

	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		Log.d(TAG, "error :: " + what + " extra :: " + extra);
	}
}
