package com.example.tagmyvideo;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tagmyvideo.db.dao.VideoDAO;
import com.example.tagmyvideo.db.dao.impl.VideoDAOImpl;
import com.example.tagmyvideo.db.model.Video;
import com.example.tagmyvideo.recorder.CamActivity;
import com.example.tagmyvideo.utils.UpdateThumbnailTask;

public class DashBoardActivity extends Activity implements OnClickListener {

	private View relative_layout;
	private Animation rotate, fadeIn;
	private ImageView book, setting, playlist, record, center;
	private View LocalView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dash_board);

		initilizations();

	}

	private void initilizations() {
		rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_center);

		fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

		rotate.setInterpolator(new AccelerateInterpolator(1.0f));
		relative_layout = (View) findViewById(R.id.ParentDashBoardLayout);

		book = (ImageView) findViewById(R.id.bookImage);
		setting = (ImageView) findViewById(R.id.settingImage);
		record = (ImageView) findViewById(R.id.recordImage);
		playlist = (ImageView) findViewById(R.id.playlistImage);
		center = (ImageView) findViewById(R.id.centerImage);

		book.setOnClickListener(this);
		setting.setOnClickListener(this);
		record.setOnClickListener(this);
		playlist.setOnClickListener(this);
		center.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		LocalView = v;
		relative_layout.startAnimation(rotate);

		/*
		 * try { Thread.sleep(2000); } catch (InterruptedException e) {
		 * System.out.println("error in thread.Sleep in OnCLICK method" ); }
		 */

		if (v == book) {
			center.setImageResource(R.drawable.book);
		} else if (v == setting) {
			center.setImageResource(R.drawable.setting);
		} else if (v == record) {
			center.setImageResource(R.drawable.record);
		} else if (v == playlist) {
			center.setImageResource(R.drawable.playlist);
		}

		center.startAnimation(fadeIn);

		fadeIn.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (LocalView == book) {
					Intent browseIntent = new Intent(DashBoardActivity.this,
							BrowseTaggedVideoActivity.class);
					startActivity(browseIntent);
				} else if (LocalView == setting) {
				} else if (LocalView == record) {
					Intent captureIntent = new Intent(DashBoardActivity.this,
							CamActivity.class);
					captureIntent.putExtra("return-data", true);
					startActivityForResult(captureIntent, 1);
				} else if (LocalView == playlist) {

					/*
					 * System.out.println("Inside playlist"); Intent
					 * browseIntent = new Intent(Intent.ACTION_GET_CONTENT,
					 * null);
					 * 
					 * System.out.println("Inside playlist browseIntent loadesd")
					 * ; browseIntent.setType("video/*");
					 * browseIntent.putExtra("return-data", true);
					 * startActivityForResult(browseIntent, 1);
					 */

					/*
					 * Intent browseIntent = new Intent(DashBoardActivity.this,
					 * BrowseVideoActivity.class); startActivity(browseIntent);
					 */
					Intent browseIntent = new Intent(DashBoardActivity.this,
							GalleryViewActivity.class);
					startActivity(browseIntent);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if (data != null && data.getData() != null) {
				Uri uri = data.getData();
				Log.d("Data.getData", "Data.getData" + data.getData());
				Cursor cursor = getContentResolver().query(
						uri,
						new String[] { MediaStore.Video.VideoColumns.DATA,
								MediaStore.Video.VideoColumns.DISPLAY_NAME,
								MediaStore.Video.VideoColumns._ID,
								MediaStore.Video.VideoColumns.TITLE,
								MediaStore.Video.VideoColumns.MIME_TYPE },
						null, null, null);
				cursor.moveToFirst();
				int idDB = cursor.getInt(cursor
						.getColumnIndex(MediaStore.Video.Media._ID));
				Log.d("DashboardActivity", "Video ID from database" + idDB);
				Log.d("VideoGallery", "" + uri.getPath());

				/*
				 * ContentResolver crThumb = getContentResolver();
				 * BitmapFactory.Options options = new BitmapFactory.Options();
				 * options.inSampleSize = 1; Bitmap curThumb =
				 * MediaStore.Video.Thumbnails.getThumbnail( crThumb, idDB,
				 * MediaStore.Video.Thumbnails.MINI_KIND, options);
				 * 
				 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
				 * curThumb.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				 * byte[] imgData = baos.toByteArray();
				 * 
				 * Log.e("VideoGallery", "BMP Data " + imgData.length);
				 */

				File videoPath = new File(cursor.getString(0));
				/*
				 * String videoHeight = cursor.getString(1); String videoWidth =
				 * cursor.getString(2);
				 */
				String path = videoPath.getAbsolutePath();
				Toast.makeText(this, "Video Path " + path, Toast.LENGTH_SHORT)
						.show();
				//TODO : Remove Sys.Out
				System.out.println("video Path : "
						+ videoPath.getAbsolutePath());
				System.out.println("video Name : " + cursor.getString(1));
				String onlyPath = path.substring(0, (path.length() - cursor
						.getString(1).length()));
				System.out.println("only path " + onlyPath);
				int videoId = saveVideo(onlyPath, cursor.getString(1));
				UpdateThumbnailTask updateThumbnail = new UpdateThumbnailTask(
						this, videoId, idDB);
				updateThumbnail.execute();
				System.out.println("id of the video : " + videoId);

				Intent playVideoActivity = new Intent(this,
						VideoPlayerActivity.class);
				playVideoActivity.putExtra("videoPath",
						videoPath.getAbsolutePath());
				playVideoActivity.putExtra("video_id", videoId);
				playVideoActivity.putExtra("video_name", cursor.getString(1));
				cursor.close();
				startActivity(playVideoActivity);
			}
			break;

		default:
			break;
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
			return id;

		} else {
			videoDAO.close();
			return id;

		}
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

}
