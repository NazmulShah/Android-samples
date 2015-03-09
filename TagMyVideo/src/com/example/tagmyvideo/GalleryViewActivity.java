package com.example.tagmyvideo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tagmyvideo.db.dao.VideoDAO;
import com.example.tagmyvideo.db.dao.impl.VideoDAOImpl;
import com.example.tagmyvideo.db.model.Video;
import com.example.tagmyvideo.utils.FetchThumbnailTask;
import com.example.tagmyvideo.utils.FetchThumbnailTask.ThumbnailLoaded;
import com.example.tagmyvideo.utils.UpdateThumbnailTask;
import com.example.tagmyvideo.utils.VideoGalleryGridAdapter;

public class GalleryViewActivity extends Activity implements ThumbnailLoaded {
	private List<Video> thumbnails;
	private ProgressBar progressBar;
	private ImageView refreshImage;
	private GridView gridView;
	private VideoGalleryGridAdapter gvadapter;
	private boolean dataReceived = false;
	private boolean configChanged = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_gallery_grid_view);
		initComponents();
		// thumbnails = getMedia();

	}

	/*
	 * GrvidView Adapter
	 */

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("Muila re baba", "onResume");
		if (!configChanged) {
			fetchMedia();
			setAdapter();
		} else {
			dataReceived = true;
			setAdapter();
			progressBar.setVisibility(View.GONE);
		}
		/*
		 * super.onResume(); fetchMedia(); setAdapter();
		 */
	}

	private void initComponents() {
		gridView = (GridView) findViewById(R.id.gridView);
		progressBar = (ProgressBar) findViewById(R.id.mediaFetchProgress);
		refreshImage = (ImageView) findViewById(R.id.mediaGalleryRefresh);
		refreshImage.setOnTouchListener(refreshListener);
	}

	private void setAdapter() {
		if (dataReceived) {
			Log.d("Setting Adapter", "Setting Adapter");
			gvadapter = new VideoGalleryGridAdapter(this,
					R.layout.video_gallery_grid_view_data, thumbnails);
			gridView.setAdapter(gvadapter);
			gridView.setOnItemClickListener(itemClickListener);
			dataReceived = false;
		}
	}

	private void fetchMedia() {
		FetchThumbnailTask task = new FetchThumbnailTask(this, progressBar,
				null);
		task.execute();
	}

	@Override
	public void thumbnailLoaded(List<Video> thumbnailList) {
		Log.d("Callback", "Recieved the data");
		this.thumbnails = thumbnailList;
		dataReceived = true;
		setAdapter();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//TODO : Use String constants.
		outState.putParcelableArrayList("vList", (ArrayList<Video>) thumbnails);
		outState.putBoolean("configChange", true);
		Log.d("kardiya save", "hai re");
		super.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		thumbnails = savedInstanceState.getParcelableArrayList("vList");
		configChanged = savedInstanceState.getBoolean("configChange");
		if (thumbnails != null) {
			Log.d("Muila re baba", "Onrestore :: " + thumbnails.size()
					+ " & configareChangedOrNot :: " + configChanged);
		}
		super.onRestoreInstanceState(savedInstanceState);
	}

	/*private void clearThumnailsList() {
		thumbnails.clear();
	}*/

	private OnTouchListener refreshListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			progressBar.setVisibility(View.VISIBLE);
			//clearThumnailsList();
			fetchMedia();
			return false;
		}
	};

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long id) {
			Video v = thumbnails.get(position);
			Toast.makeText(
					GalleryViewActivity.this,
					"Video path " + v.getPath() + v.getFileName() + " :::: "
							+ v.getLocalVideoId(), Toast.LENGTH_SHORT).show();
			int videoId = saveVideo(v.getPath(), v.getFileName());
			if (v.getLocalVideoId() > -1) {
				UpdateThumbnailTask updateThumbnail = new UpdateThumbnailTask(
						GalleryViewActivity.this, videoId, v.getLocalVideoId());
				updateThumbnail.execute();
			}
			//TODO : Use String Constants.
			Intent playVideoActivity = new Intent(GalleryViewActivity.this,
					VideoPlayerActivity.class);
			playVideoActivity.putExtra("videoPath",
					v.getPath() + v.getFileName());
			playVideoActivity.putExtra("video_id", videoId);
			playVideoActivity.putExtra("video_name", v.getFileName());
			startActivity(playVideoActivity);
		}
	};

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
