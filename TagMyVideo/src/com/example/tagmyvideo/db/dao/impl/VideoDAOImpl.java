/**
 * 
 */
package com.example.tagmyvideo.db.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tagmyvideo.constants.DBConstants;
import com.example.tagmyvideo.db.MySQLiteHelper;
import com.example.tagmyvideo.db.dao.VideoDAO;
import com.example.tagmyvideo.db.model.Video;

/**
 * @author Nikhil Sharma & Prashant Prabhakar
 * 
 */
public class VideoDAOImpl implements VideoDAO {

	private final String TAG = VideoDAOImpl.class.getName();

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;

	private String[] VideoColumns = { DBConstants.COLUMN_VIDEO_ID,
			DBConstants.COLUMN_VIDEO_FILE_NAME,
			DBConstants.COLUMN_VIDEO_LENGTH,
			DBConstants.COLUMN_VIDEO_IS_BOOKMARKED,
			DBConstants.COLUMN_VIDEO_IS_SYNC,
			DBConstants.COLUMN_VIDEO_SYNC_TIME,
			DBConstants.COLUMN_VIDEO_FREQUENCY, DBConstants.COLUMN_VIDEO_PATH,
			DBConstants.COLUMN_VIDEO_THUMB };

	public VideoDAOImpl(Context context) {
		dbHelper = MySQLiteHelper.getInstance(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Integer createVideo(Video newVideo) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.COLUMN_VIDEO_FILE_NAME, newVideo.getFileName());
		values.put(DBConstants.COLUMN_VIDEO_LENGTH, newVideo.getLength());
		values.put(DBConstants.COLUMN_VIDEO_IS_BOOKMARKED,
				newVideo.getIsBookmarked());
		values.put(DBConstants.COLUMN_VIDEO_IS_SYNC, newVideo.getIsSync());
		values.put(DBConstants.COLUMN_VIDEO_FREQUENCY, 0);
		values.put(DBConstants.COLUMN_VIDEO_PATH, newVideo.getPath());
		values.put(DBConstants.COLUMN_VIDEO_THUMB, newVideo.getThumbData());
		int insertId = (int) database.insert(DBConstants.TABLE_VIDEO, null,
				values);
		Log.d(TAG, "new video inserted............." + insertId);
		return insertId;
	}

	public int deleteVideo(int videoId) {
		int result = database.delete(DBConstants.TABLE_VIDEO,
				DBConstants.COLUMN_VIDEO_ID + " = " + videoId, null);
		return result;
	}

	public List<Video> getAllVideos() {
		List<Video> videoList = new ArrayList<Video>();

		Cursor cursor = database.query(DBConstants.TABLE_VIDEO, VideoColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Video video = cursorToVideo(cursor);
			videoList.add(video);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		//TODO : make sure cursor is not null
		cursor.close();
		return videoList;
	}

	public Video getVideoById(int id) {
		Cursor cursor = database.query(DBConstants.TABLE_VIDEO, VideoColumns,
				DBConstants.COLUMN_VIDEO_ID + " = " + id, null, null, null,
				null);
		cursor.moveToFirst();
		Video video = cursorToVideo(cursor);
		//TODO : make sure cursor is not null
		cursor.close();
		return video;
	}

	public Video getVideoByName(String name) {
		Cursor cursor = database.query(DBConstants.TABLE_VIDEO, VideoColumns,
				DBConstants.COLUMN_VIDEO_FILE_NAME + " = " + name, null, null,
				null, null);
		cursor.moveToFirst();
		Video video = cursorToVideo(cursor);
		cursor.close();
		return video;
	}

	@Override
	public Integer isDuplicate(String name, String path) {
		Cursor cursor = database.query(DBConstants.TABLE_VIDEO,
				new String[] { DBConstants.COLUMN_VIDEO_ID },
				DBConstants.COLUMN_VIDEO_FILE_NAME + " = '" + name + "' ",
				null, null, null, null);
		cursor.moveToFirst();
		if (cursor.getCount() == 0) {
			Log.d(TAG, "isDuplicate ::: False....");
			cursor.close();
			return -1;
		} else {
			int id = Integer.parseInt(cursor.getString(0));
			Log.d(TAG, "isDuplicate ::: True....");
			Log.d(TAG, "with video id =  " + id);
			cursor.close();
			return id;
		}
	}

	@SuppressWarnings("deprecation")
	private Video cursorToVideo(Cursor cursor) {
		Video video = new Video();
		video.setId(Integer.parseInt(cursor.getString(0)));
		video.setFileName(cursor.getString(1));
		video.setLength(cursor.getLong(2));
		video.setIsBookmarked(Boolean.valueOf(cursor.getString(3)));
		video.setIsSync(cursor.getInt(4));
		if (video.getIsSync() > 0)
			video.setSync_time(new Date(cursor.getString(5)));
		video.setFrequency(cursor.getInt(6));
		video.setPath((cursor.getString(7)));
		video.setThumbData((cursor.getBlob(8)));
		return video;
	}

	@Override
	public int updateLength(int videoId, long length) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.COLUMN_VIDEO_LENGTH, length);
		return database.update(DBConstants.TABLE_VIDEO, values,
				DBConstants.COLUMN_VIDEO_ID + " = " + videoId, null);
	}

	@Override
	public int updateIsBookmarked(final int videoId, boolean status) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.COLUMN_VIDEO_IS_BOOKMARKED, status);

		return database.update(DBConstants.TABLE_VIDEO, values,
				DBConstants.COLUMN_VIDEO_ID + " =" + videoId, null);
	}

	@Override
	public int updateSyncData(int videoId, Date syncTime) {

		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
	}

	@Override
	public int updateVideoThumbnail(int videoId, byte[] videoThumbnail) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.COLUMN_VIDEO_THUMB, videoThumbnail);

		return database.update(DBConstants.TABLE_VIDEO, values,
				DBConstants.COLUMN_VIDEO_ID + " =" + videoId, null);
	}

}
