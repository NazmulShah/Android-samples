/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.tagmyvideo.db.model.Video;

/**
 * @author Ashish R Agre
 * 
 */
public class FetchThumbnailTask extends AsyncTask<Void, Boolean, Boolean> {

	private Context context;
	private ProgressBar progressBar;
	private View view;
	private List<Video> videoRows;

	public FetchThumbnailTask(Context context, ProgressBar progressBar,
			View view) {
		this.context = context;
		this.progressBar = progressBar;
		this.view = view;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		String[] mediColumns = { MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
				MediaStore.Video.Media.MIME_TYPE };
		videoRows = new ArrayList<Video>();
		videoRows.clear();
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediColumns, null,
				null, null);
		if (cursor.moveToFirst()) {
			while (cursor.moveToNext()) {
				Video tempVideoView = new Video();
				String filePath = cursor.getString(cursor
						.getColumnIndex(MediaStore.Video.Media.DATA));
				processNameAndPath(filePath, tempVideoView);
				int id = cursor.getInt(cursor
						.getColumnIndex(MediaStore.Video.Media._ID));
				tempVideoView.setLocalVideoId(id);
				Log.e("VideoGallery", "Video ID" + id);

				ContentResolver crThumb = context.getContentResolver();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 3;
				Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(
						crThumb, id, MediaStore.Video.Thumbnails.MINI_KIND,
						options);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					curThumb.compress(Bitmap.CompressFormat.JPEG, 60, baos);
					byte[] data = baos.toByteArray();
					tempVideoView.setThumbData(data);
					videoRows.add(tempVideoView);
				} catch (Exception e) {
					if(e instanceof NullPointerException) {
						return true;
					}
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			progressBar.setVisibility(View.GONE);
			ThumbnailLoaded thumbs = (ThumbnailLoaded) context;
			for (Video v : videoRows) {
				Log.d("Video path ", "VP " + v.getPath());
				Log.d("Video name ", "VN " + v.getFileName());
			}
			Log.d("Video List Size ", "vS " + videoRows.size());
			thumbs.thumbnailLoaded(videoRows);
		}
	}

	private void processNameAndPath(String filePath, Video vObj) {
		FileNameUtility fileNameUtility;
		try {
			fileNameUtility = new FileNameUtility(filePath);
			vObj.setPath(fileNameUtility.getFilePath());
			vObj.setFileName(fileNameUtility.getFileName());
		} catch (IOException e) {
			vObj.setFileName("empty");
			vObj.setPath("empty");
		}
	}

	public interface ThumbnailLoaded {
		public void thumbnailLoaded(List<Video> thumbnailList);
	}
}
