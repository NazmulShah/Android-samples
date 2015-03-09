/**
 * 
 */
package com.example.tagmyvideo;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagmyvideo.db.dao.TagDAO;
import com.example.tagmyvideo.db.dao.VideoDAO;
import com.example.tagmyvideo.db.dao.impl.TagDAOImpl;
import com.example.tagmyvideo.db.dao.impl.VideoDAOImpl;
import com.example.tagmyvideo.db.model.Tag;
import com.example.tagmyvideo.db.model.Video;
import com.example.tagmyvideo.ui.widget.CustomSeekBar;
import com.example.tagmyvideo.ui.widget.custom_dialog.CustomDialog;
import com.example.tagmyvideo.ui.widget.custom_dialog.DialogCreatedListener;
import com.example.tagmyvideo.utils.FileNameUtility;
import com.example.tagmyvideo.utils.TagListAdapter;
import com.example.tagmyvideo.utils.TimeComparator;
import com.example.tagmyvideo.utils.UpdateThumbnailTask;
import com.example.tagmyvideo.utils.Utilities;

/**
 * @author Ashish R Agre
 * 
 */
public class VideoPlayerActivity extends FragmentActivity implements
		OnCompletionListener, OnErrorListener, OnInfoListener,
		OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener,
		SurfaceHolder.Callback, OnTouchListener, OnSeekBarChangeListener,
		View.OnClickListener, OnBufferingUpdateListener {

	private final String LOG_TAG = VideoPlayerActivity.class.getName();

	private Display currentDisplay;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private CustomSeekBar progressbar;
	private ImageView playpauseBtn;
	private ImageView bookMarkBtn;
	private ImageView backwardBtn;
	private ImageView forwardBtn;
	private ImageView brwBookmarkBtn;
	private TextView currentPlayingTimeText, totalDurationTimeText;
	private TextView videoFilename;
	private Utilities utils;
	private int playTimeSaved;
	private String videoName;
	private int currentTagPosition;

	/*
	 * power house of Player
	 */
	// / Handler to control progrees of seekbar while song is being played
	private Handler mHandler = new Handler();
	private Handler mcHandler = new Handler();
	private MediaPlayer mediaPlayer;
	private int videoWidth = 0, videoHeight = 0;

	// TODO : Make these as final, as constants.
	private int seekForwardTime = 5000; // 5000 milliseconds
	private int seekBackwardTime = 5000; // 5000 milliseconds
	/*
	 * Floating media Controller layout *
	 */
	private RelativeLayout mediaController;
	private RelativeLayout headerPanel;
	private LinearLayout linearBtnPanel;
	private LinearLayout topPanel;
	private RelativeLayout tagFrame;
	private String videoFilepath;

	private Animation fadeIn, fadeOut;
	private int videoId = 0;
	private List<Tag> tagList;
	private ListView listView = null;
	private boolean viewCall = false;

	private CustomDialog customDialog;

	/*
	 * 
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_player_activity);
		utils = new Utilities();
		playTimeSaved = 0;
	}

	@SuppressWarnings("deprecation")
	private void initViews() {
		// TODO : String constants.
		videoId = getIntent().getIntExtra("video_id", 0);
		System.out.println("video id = " + videoId);
		videoName = getIntent().getStringExtra("video_name");
		/*
		 * Incoming Intent data
		 */

		Intent receivedIntent = getIntent();
		videoFilepath = receivedIntent.getStringExtra("videoPath");

		if (videoName == null || videoFilepath == null
				|| videoName.equalsIgnoreCase("")
				|| videoFilepath.equalsIgnoreCase("")) {
			try {
				videoFilepath = Uri.decode(getIntent().getData().toString());
				Log.d(LOG_TAG, "Path:::" + videoFilepath + "::::");
				FileNameUtility fileUtility = new FileNameUtility(videoFilepath);
				videoName = fileUtility.getFileName();

				videoId = saveVideo(fileUtility.getFilePath(), videoName);
			} catch (Exception e) {

				Log.e(LOG_TAG, "Error ::::\n" + e.toString());
			}

		}
		/*
		 * Initialization of the view
		 */
		surfaceView = (SurfaceView) findViewById(R.id.SurfaceView);
		surfaceView.setOnTouchListener(this);
		/*
		 * get the holder from the surface view
		 */

		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mediaController = (RelativeLayout) findViewById(R.id.mediaController);
		mediaController.setOnTouchListener(this);
		mediaController.setVisibility(View.INVISIBLE);
		headerPanel = (RelativeLayout) findViewById(R.id.headerPanel);
		headerPanel.setOnTouchListener(this);
		headerPanel.setVisibility(View.INVISIBLE);
		progressbar = (CustomSeekBar) mediaController
				.findViewById(R.id.mediaProgressbar);
		progressbar.setOnSeekBarChangeListener(this);
		linearBtnPanel = (LinearLayout) mediaController
				.findViewById(R.id.btnPanel);
		topPanel = (LinearLayout) mediaController.findViewById(R.id.toppanel);
		tagFrame = (RelativeLayout) findViewById(R.id.tagFrame);
		playpauseBtn = (ImageView) linearBtnPanel
				.findViewById(R.id.playpauseButton);
		bookMarkBtn = (ImageView) findViewById(R.id.bookMarkImage);
		backwardBtn = (ImageView) findViewById(R.id.backwardBtn);
		forwardBtn = (ImageView) findViewById(R.id.forwardBtn);
		brwBookmarkBtn = (ImageView) tagFrame
				.findViewById(R.id.browseBookmarks);
		playpauseBtn.setOnTouchListener(this);
		bookMarkBtn.setOnTouchListener(this);
		backwardBtn.setOnTouchListener(this);
		forwardBtn.setOnTouchListener(this);
		brwBookmarkBtn.setOnTouchListener(this);
		currentPlayingTimeText = (TextView) topPanel
				.findViewById(R.id.currentTime);
		totalDurationTimeText = (TextView) topPanel
				.findViewById(R.id.totalTime);
		videoFilename = (TextView) headerPanel.findViewById(R.id.videoName);
		if (videoName == null || videoName.equalsIgnoreCase("")) {
			videoFilename.setText("untitled video");
		} else {
			videoFilename.setText(videoName);
		}

		/*
		 * Init media player
		 */
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnInfoListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnSeekCompleteListener(this);
		mediaPlayer.setOnVideoSizeChangedListener(this);
		mediaPlayer.setOnBufferingUpdateListener(this);
		try {
			mediaPlayer.setDataSource(this, Uri.parse(videoFilepath));
		} catch (Exception e) {
			Log.d(LOG_TAG, e.toString());
			finish();
		}
		currentDisplay = getWindowManager().getDefaultDisplay();
		/*
		 * Animation Initialization
		 */
		fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d("Surface", "Surface Changed");
	}

	@Override
	protected void onResume() {
		super.onResume();
		initViews();
		setupList();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("Surface", "Surface Created");
		mediaPlayer.setDisplay(holder);
		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException ilse) {
			ilse.printStackTrace();
			Log.d(LOG_TAG, "IllegalStateException");
		} catch (IOException e) {
			e.printStackTrace();
			Log.d(LOG_TAG, "IOExceaption");
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		playTimeSaved = mediaPlayer.getCurrentPosition();
		mediaPlayer.stop();
		Log.d("Pause", "pause");
	}

	private void setupList() {
		TagDAO tagDAO = new TagDAOImpl(this);
		tagDAO.open();
		System.out.println("video id : " + videoId);
		tagList = tagDAO.getAllTags(videoId);
		// tagList = tagDAO.sampleTagList(videoId);

		System.out.println("tagList lenght " + tagList.size());
		listView = (ListView) tagFrame.findViewById(R.id.taglist);
		if (tagList.size() > 1) {
			Collections.sort(tagList, new TimeComparator());
		}
		listView.setAdapter(new TagListAdapter(this, tagList));
		tagDAO.close();
		listView.setOnItemClickListener(new TagListListener());

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("Surface", "Surface destroyed");
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onPrepared(MediaPlayer mp) {
		Log.v(LOG_TAG, "onPrepared Called");
		videoWidth = mp.getVideoWidth();
		videoHeight = mp.getVideoHeight();
		// TODO : Try to avoid deprecated methods. Search for alternative.
		if (videoWidth > currentDisplay.getWidth()
				|| videoHeight > currentDisplay.getHeight()) {
			float heightRatio = (float) videoHeight
					/ (float) currentDisplay.getHeight();
			float widthRatio = (float) videoWidth
					/ (float) currentDisplay.getWidth();

			if (heightRatio > 1 || widthRatio > 1) {
				if (heightRatio > widthRatio) {
					videoHeight = (int) Math.ceil((float) videoHeight
							/ (float) heightRatio);
					videoWidth = (int) Math.ceil((float) videoWidth
							/ (float) heightRatio);
				} else {
					videoHeight = (int) Math.ceil((float) videoHeight
							/ (float) widthRatio);
					videoWidth = (int) Math.ceil((float) videoWidth
							/ (float) widthRatio);
				}
			}
		}
		// TODO : Try to avoid deprecated methods. Search for alternative.
		surfaceView.setLayoutParams(new FrameLayout.LayoutParams(currentDisplay
				.getWidth(), currentDisplay.getHeight()));
		if (playTimeSaved > 0) {
			mp.seekTo(playTimeSaved);
		}
		mp.start();
		progressbar.setProgress(0);
		progressbar.setMax(100);
		updateProgressBar();
		mediaController.setVisibility(View.VISIBLE);
		setupSlider();
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		if (what == MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING) {
			Log.v(LOG_TAG, "Media Info, Media Info Bad Interleaving " + extra);
		} else if (what == MediaPlayer.MEDIA_INFO_NOT_SEEKABLE) {
			Log.v(LOG_TAG, "Media Info, Media Info Not Seekable " + extra);
		} else if (what == MediaPlayer.MEDIA_INFO_UNKNOWN) {
			Log.v(LOG_TAG, "Media Info, Media Info Unknown " + extra);
		} else if (what == MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING) {
			Log.v(LOG_TAG, "MediaInfo, Media Info Video Track Lagging " + extra);
			/*
			 * Android Version 2.0 and Higher } else if (whatInfo ==
			 * MediaPlayer.MEDIA_INFO_METADATA_UPDATE) {
			 * Log.v(LOGTAG,"MediaInfo, Media Info Metadata Update " + extra);
			 */
		}
		return false;
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.v(LOG_TAG, "onError Called");

		if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
			Log.v(LOG_TAG, "Media Error, Server Died " + extra);
		} else if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
			Log.v(LOG_TAG, "Media Error, Error Unknown " + extra);
		}

		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.d(LOG_TAG, "Song Play Completed");
		mHandler.removeCallbacks(mUpdateTimeTask);
		playpauseBtn.setImageResource(R.drawable.play);
		progressbar.setProgress(0);
		currentPlayingTimeText.setText("00:00");
		System.err.println("Bookmarl list"
				+ progressbar.getBookmarks().toString());
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// TODO : Check for view.getID().
		// TODO : use switch-case.
		if (view == playpauseBtn) {
			viewCall = true;
			Log.d("BTN", "PP : " + view.getId());
			playpauseAction();
		} else if (view == bookMarkBtn) {
			viewCall = true;
			Log.d("BTN", "BM: " + view.getId());
			saveBookmark();
		} else if (view == brwBookmarkBtn) {
			viewCall = true;
			Log.d("BTN", "B-BM: " + view.getId());
			showBookmarkList();
			if (mediaController.getVisibility() == View.VISIBLE) {
				hideMediaController();
			}
		} else if (view == backwardBtn) {
			viewCall = true;
			Log.d("BTN", "<<: " + view.getId());
			backwardAction();
		} else if (view == forwardBtn) {
			viewCall = true;
			Log.d("BTN", ">>: " + view.getId());
			forwardAction();
		} else if (view == surfaceView && !viewCall) {
			Log.d("BTN", "normal touch: " + view.getId());
			if (mediaController.getVisibility() == View.VISIBLE) {
				hideMediaController();
			} else if (mediaController.getVisibility() == View.INVISIBLE
					&& listView.getVisibility() == View.INVISIBLE) {
				showMediaController();
			} else if (listView.getVisibility() == View.VISIBLE) {
				hideBookmarkList();
			}
		} else if (view == mediaController) {
			viewCall = true;
			Log.d("Panel", "MediaPanel : " + view.getId());
		} else if (view == headerPanel) {
			viewCall = true;
			Log.d("Panel", "Headerpanel : " + view.getId());
		} else {
			Log.d("Else", "Big Else");
			viewCall = false;
		}
		return false;

	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		// TODO : make local primitive object as final, if these are not going
		// to change.
		int totalDuration = mediaPlayer.getDuration();
		int currentPosition = utils.progressToTimer(seekBar.getProgress(),
				totalDuration);

		// forward or backward to certain seconds
		mediaPlayer.seekTo(currentPosition);
		mediaPlayer.start();
		playpauseBtn.setImageResource(R.drawable.pause);
		updateProgressBar();
		/*
		 * Drawable tempDrawable = seekBar.getBackground(); tempDrawable.ge
		 */

	}

	/*
	 * User Defined Method for handling progress bar progress
	 */

	private void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 100);
	}

	/*
	 * Handlers and runnables for the MediaPlayer
	 */
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			long totalDuration = mediaPlayer.getDuration();
			long currentDuration = mediaPlayer.getCurrentPosition();

			// Log.e("TotalDuration", "" + totalDuration);
			// Log.e("currentDuration", "" + currentDuration);
			// TODO : Use String.valueOf();
			
			// totalDurationTimeText.setText(""
			// + utils.milliSecondsToTimer(totalDuration));

			totalDurationTimeText.setText(String.valueOf(utils
					.milliSecondsToTimer(totalDuration)));

			// Displaying time completed playing
			currentPlayingTimeText.setText(""
					+ utils.milliSecondsToTimer(currentDuration));
			// Updating progress bar
			int progress = (int) (utils.getProgressPercentage(currentDuration,
					totalDuration));
			// Log.d("Progress", ""+progress);
			progressbar.setProgress(progress);

			// Running this thread after 100 milliseconds
			mHandler.postDelayed(this, 100);
		}
	};

	private Runnable mediaControllerRunnable = new Runnable() {
		public void run() {
			if (mediaController.getVisibility() == View.VISIBLE) {
				Log.e("Just Removed", "Removing");
				mediaController.startAnimation(fadeOut);
				mediaController.setVisibility(View.INVISIBLE);
			}
		}
	};

	/*
	 * private void showAutoHideMediaPanel() {
	 * mcHandler.postDelayed(mediaControllerRunnable, 5000); }
	 */

	@Override
	public void onClick(View v) {
		//TODO : use String constants.
		String postiveButtonName = "";
		int layout_id = -1;
		OnClickListener positiveListener = null;
		String dialogDesc = null;
		String title = "";
		DialogCreatedListener dialogCreatedListener = null;
		if (v.getId() == R.id.edit_tag) {
			Log.d(LOG_TAG, "edit");
			title = "Update Caption";
			postiveButtonName = "Update";
			layout_id = R.layout.update_dialog;
			positiveListener = updatePositiveListener;
			dialogCreatedListener = dialogListener;
		} else if (v.getId() == R.id.delete_tag) {
			Log.d(LOG_TAG, "delete");
			title = "Delete Tag";
			postiveButtonName = "Remove";
			dialogDesc = "Are you sure want to remove bookmark.";
			layout_id = -1;
			positiveListener = deletePositiveListener;
		}
		currentTagPosition = Integer.parseInt(v.getTag().toString());
		Toast.makeText(
				this,
				postiveButtonName + " "
						+ (tagList.get(currentTagPosition)).getBookmarkTime(),
				Toast.LENGTH_SHORT).show();
		customDialog = new CustomDialog(this, title, null, positiveListener);
		customDialog.setDescription(dialogDesc);
		customDialog.setLayoutResource(layout_id);
		customDialog.setPositivebtnname(postiveButtonName);
		customDialog
				.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		customDialog.setDialogCreatedListener(dialogCreatedListener);
		customDialog.showDialog();

	}

	private DialogCreatedListener dialogListener = new DialogCreatedListener() {

		@Override
		public void onDialogCreated(View view) {
			EditText text = ((EditText) view
					.findViewById(R.id.txt_bookmark_caption));
			String txt = tagList.get(currentTagPosition).getCaption();
			Log.d(LOG_TAG, "Returned Text is " + txt);
			if (txt != null)
				text.setText(txt);
		}
	};

	private OnClickListener deletePositiveListener = new OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			deleteBookmark(tagList.get(currentTagPosition).getId());
			setupList();
			currentTagPosition = -1;
		}
	};
	private OnClickListener updatePositiveListener = new OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			View tempView = customDialog.getView();
			String caption = ((EditText) tempView
					.findViewById(R.id.txt_bookmark_caption)).getText()
					.toString();
			Log.d(LOG_TAG, "caption :: " + caption);
			updateBookmark(tagList.get(currentTagPosition).getId(), caption);
			setupList();
			currentTagPosition = -1;
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(mUpdateTimeTask);
		mcHandler.removeCallbacks(mediaControllerRunnable);
		mediaPlayer.release();
	}

	/*
	 * Orientation change
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {

	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		Log.e("Current buffer percent", "" + percent + "%");
		progressbar.setSecondaryProgress(percent);
	}

	private void setupSlider() {
		int totalDuration = mediaPlayer.getDuration();
		Log.e("Total mediDuration setupList", "sl" + totalDuration);
		Set<Integer> bookmarkList;
		if (tagList.size() > 0) {
			bookmarkList = new LinkedHashSet<Integer>();
			for (Tag t : tagList) {
				Integer currentProgress = utils.getProgressPercentage(
						t.getBookmarkTime(), totalDuration);
				// currentProgress /= 1000;
				Log.e("Listitem", "li" + currentProgress);
				bookmarkList.add(currentProgress);
			}
			Log.e("List item", "bl" + bookmarkList.toString());
			progressbar.setBookmarks(bookmarkList);
		}

	}

	private class TagListListener implements OnItemClickListener {

		private TagListListener() {
			// Toast.makeText(getApplicationContext(), "List Listener Setup",
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long id) {
			LinearLayout layout = (LinearLayout) view;
			TextView millisTextView = (TextView) layout
					.findViewById(R.id.hiddentTimeinmillis);
			// Toast.makeText(getApplicationContext(), "Millis :: " +
			// millisTextView.getText(), Toast.LENGTH_SHORT).show();
			mediaPlayer.seekTo(Integer.parseInt(millisTextView.getText()
					.toString()));
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
				updateProgressBar();
				playpauseBtn.setImageResource(R.drawable.pause);
			}

			listView.setVisibility(View.INVISIBLE);
			brwBookmarkBtn.setVisibility(View.VISIBLE);
		}

	}

	/*
	 * methods added later after resolving touch event for various components
	 */

	private void saveBookmark() {
		progressbar.setBookmark();
		Tag tag = new Tag();
		tag.setCaption("chapter");
		tag.setBookmarkTime(mediaPlayer.getCurrentPosition());
		tag.setVideo_id(videoId);
		tag.setCreationTime(new Date());
		tag.setFrequency(0);
		TagDAO tagDao = new TagDAOImpl(this);
		tagDao.open();
		tagDao.createTag(tag);
		tagDao.close();
		setupList();
	}

	private void updateBookmark(final int tagId, final String caption) {

		TagDAO tagDao = new TagDAOImpl(this);
		tagDao.open();
		tagDao.updateCaption(tagId, caption);
		tagDao.close();
	}

	private void deleteBookmark(final int tagId) {
		TagDAO tagDao = new TagDAOImpl(this);
		tagDao.open();
		tagDao.deleteTag(tagId);
		tagDao.close();
	}

	private void playpauseAction() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			playpauseBtn.setImageResource(R.drawable.play);
		} else if (!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
			updateProgressBar();
			playpauseBtn.setImageResource(R.drawable.pause);
		}
	}

	private void forwardAction() {
		int currentPosition = mediaPlayer.getCurrentPosition();
		if (currentPosition + seekForwardTime <= mediaPlayer.getDuration()) {
			// forward song
			mediaPlayer.seekTo(currentPosition + seekForwardTime);
		} else {
			// forward to end position
			mediaPlayer.seekTo(mediaPlayer.getDuration());
		}
	}

	private void backwardAction() {
		int currentPosition = mediaPlayer.getCurrentPosition();
		// check if seekBackward time is greater than 0 sec
		if (currentPosition - seekBackwardTime >= 0) {
			// forward song
			mediaPlayer.seekTo(currentPosition - seekBackwardTime);
		} else {
			// backward to starting position
			mediaPlayer.seekTo(0);
		}
	}

	private void showMediaController() {
		mediaController.startAnimation(fadeIn);
		headerPanel.startAnimation(fadeIn);
		mediaController.setVisibility(View.VISIBLE);
		headerPanel.setVisibility(View.VISIBLE);

	}

	private void hideMediaController() {
		mediaController.startAnimation(fadeOut);
		headerPanel.startAnimation(fadeOut);
		mediaController.setVisibility(View.INVISIBLE);
		headerPanel.setVisibility(View.INVISIBLE);
	}

	private void hideBookmarkList() {
		brwBookmarkBtn.setVisibility(View.VISIBLE);
		listView.setVisibility(View.INVISIBLE);
	}

	private void showBookmarkList() {
		brwBookmarkBtn.setVisibility(View.INVISIBLE);
		listView.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Log.d("Came", "Here");
			return true;
		} else {
			Log.d("Came", "Here....");
			return super.onKeyDown(keyCode, event);
		}

	}

	private int saveVideo(String path, String name) {
		VideoDAO videoDAO = new VideoDAOImpl(this);
		Video video = new Video();
		videoDAO.open();
		int id = videoDAO.isDuplicate(name, path);
		if (id == -1) {
			video.setFileName(name);
			video.setPath(path);
			id = videoDAO.createVideo(video);
			videoDAO.close();
			new UpdateThumbnailTask(this, id, new File(path)).execute();
			return id;

		} else {
			videoDAO.close();
			return id;

		}
	}
}