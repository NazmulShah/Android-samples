<<<<<<< HEAD
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.io.File;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.tagmyvideo.db.dao.VideoDAO;
import com.example.tagmyvideo.db.dao.impl.VideoDAOImpl;

/**
 * @author Ashish R Agre
 * 
 */
public class UpdateThumbnailTask extends AsyncTask<Void, String, Boolean> {
	private Context context;
	private Integer videoId;
	private Integer mediaStoreId;
	private byte[] imgData;
	private File file;
	private VideoDAO vDAO;

	public UpdateThumbnailTask(Context context, Integer videoId,
			int mediaStoreId) {
		this.context = context;
		this.videoId = videoId;
		this.mediaStoreId = mediaStoreId;
		vDAO = new VideoDAOImpl(context);
	}
	
	public UpdateThumbnailTask(Context context, Integer videoId, File file) {
		this.context = context;
		this.videoId = videoId;
		this.mediaStoreId = -1;
		this.file = file;
		/*if (vDAO == null) {
			vDAO = new VideoDAOImpl(context);
			Log.d("Video Dao Secind Timwe null", "VideoDAO NULL");
		}*/
		vDAO = new VideoDAOImpl(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected Boolean doInBackground(Void... params) {
		ExtractThumbnail eThumbnail = new ExtractThumbnail(context);
		if (mediaStoreId == -1) {
			mediaStoreId = eThumbnail.getMedistoreThumbId(file);
			Log.d("MediaStoreId Async", "MID" + mediaStoreId);
		}
		imgData = eThumbnail.getImageBlob(mediaStoreId);
		if (imgData != null) {
			vDAO.open();
			int result = vDAO.updateVideoThumbnail(videoId, this.imgData);
			vDAO.close();
			Log.d("ImgDataNull Async", "Result ->" + result);
			if (result != -1) {
				return true;
			}
		} else {
			Log.d("ImgDataNull Async", "imgdata null");
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {

		if (!result) {
			Toast.makeText(context, "Unable to fetch thumbnail",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, "Thumbnail updated" + imgData.length,
					Toast.LENGTH_SHORT).show();
		}
	}

}
=======
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.io.File;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.tagmyvideo.db.dao.VideoDAO;
import com.example.tagmyvideo.db.dao.impl.VideoDAOImpl;

/**
 * @author Ashish R Agre
 * 
 */
public class UpdateThumbnailTask extends AsyncTask<Void, String, Boolean> {
	private Context context;
	private Integer videoId;
	private Integer mediaStoreId;
	private byte[] imgData;
	private File file;
	private VideoDAO vDAO;

	public UpdateThumbnailTask(Context context, Integer videoId,
			int mediaStoreId) {
		this.context = context;
		this.videoId = videoId;
		this.mediaStoreId = mediaStoreId;
		vDAO = new VideoDAOImpl(context);
	}
	
	public UpdateThumbnailTask(Context context, Integer videoId, File file) {
		this.context = context;
		this.videoId = videoId;
		this.mediaStoreId = -1;
		this.file = file;
		/*if (vDAO == null) {
			vDAO = new VideoDAOImpl(context);
			Log.d("Video Dao Secind Timwe null", "VideoDAO NULL");
		}*/
		vDAO = new VideoDAOImpl(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected Boolean doInBackground(Void... params) {
		ExtractThumbnail eThumbnail = new ExtractThumbnail(context);
		if (mediaStoreId == -1) {
			mediaStoreId = eThumbnail.getMedistoreThumbId(file);
			Log.d("MediaStoreId Async", "MID" + mediaStoreId);
		}
		imgData = eThumbnail.getImageBlob(mediaStoreId);
		if (imgData != null) {
			vDAO.open();
			int result = vDAO.updateVideoThumbnail(videoId, this.imgData);
			vDAO.close();
			Log.d("ImgDataNull Async", "Result ->" + result);
			if (result != -1) {
				return true;
			}
		} else {
			Log.d("ImgDataNull Async", "imgdata null");
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {

		if (!result) {
			Toast.makeText(context, "Unable to fetch thumbnail",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, "Thumbnail updated" + imgData.length,
					Toast.LENGTH_SHORT).show();
		}
	}

}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
