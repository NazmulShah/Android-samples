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
import com.example.tagmyvideo.db.dao.TagDAO;
import com.example.tagmyvideo.db.model.Tag;

/**
 * @author Nikhil Sharma & Prashant Prabhakar
 * 
 */
public class TagDAOImpl implements TagDAO {

	private final String TAG = TagDAOImpl.class.getName();

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;

	private String[] TagColumns = { DBConstants.COLUMN_TAG_ID,
			DBConstants.COLUMN_TAG_VIDEO_ID, DBConstants.COLUMN_TAG_CAPTION,
			DBConstants.COLUMN_TAG_CREATION_TIME,
			DBConstants.COLUMN_TAG_BOOKMARK_TIME,
			DBConstants.COLUMN_TAG_FREQUENCY };

	public TagDAOImpl(Context context) {
		dbHelper = MySQLiteHelper.getInstance(context);
	}

	@Override
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	@Override
	public void close() {
		dbHelper.close();
	}

	@Override
	public long createTag(Tag tag) {
		if (getTagByBookmarkTime(tag.getVideo_id(),
				tag.getBookmarkTime() / 1000) != null)
			return -1;
		ContentValues values = new ContentValues();
		values.put(DBConstants.COLUMN_TAG_CAPTION, tag.getCaption());
		values.put(DBConstants.COLUMN_TAG_BOOKMARK_TIME,
				tag.getBookmarkTime() / 1000);
		values.put(DBConstants.COLUMN_TAG_CREATION_TIME, tag.getCreationTime()
				.toString());
		values.put(DBConstants.COLUMN_TAG_FREQUENCY, tag.getFrequency());
		values.put(DBConstants.COLUMN_TAG_VIDEO_ID, tag.getVideo_id());
		long insertId = database.insert(DBConstants.TABLE_TAG, null, values);
		Log.d(TAG, "new tag inserted............." + insertId);
		return insertId;
	}

	@Override
	public int deleteTag(int tagId) {
		int result = database.delete(DBConstants.TABLE_TAG,
				DBConstants.COLUMN_TAG_ID + " = " + tagId, null);
		return result;
	}

	@Override
	public List<Tag> getAllTags(int videoId) {
		List<Tag> tagList = new ArrayList<Tag>();
		//TODO : check for NullPointerException
		Cursor cursor = database.query(DBConstants.TABLE_TAG, TagColumns,
				DBConstants.COLUMN_TAG_VIDEO_ID + " = " + videoId, null, null,
				null, null);
		Log.d(TAG, "No. of Tags = " + cursor.getCount());
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Tag tag = cursorToTag(cursor);
			tagList.add(tag);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return tagList;
	}

	@SuppressWarnings("deprecation")
	private Tag cursorToTag(Cursor cursor) {
		Tag tag = new Tag();

		tag.setId(Integer.parseInt(cursor.getString(0)));
		tag.setVideo_id(cursor.getInt(1));
		tag.setCaption(cursor.getString(2));
		tag.setCreationTime(new Date(cursor.getString(3)));
		tag.setBookmarkTime(cursor.getInt(4) * 1000);
		tag.setFrequency((cursor.getInt(5)));

		return tag;
	}

	@Override
	public int updateCaption(int tagId, String caption) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.COLUMN_TAG_CAPTION, caption);

		return database.update(DBConstants.TABLE_TAG, values,
				DBConstants.COLUMN_TAG_ID + " = " + tagId, null);
	}

	@Override
	public int updateFrequency(int tagId) {
		int frequency = 0;
		ContentValues values = new ContentValues();
		values.put(DBConstants.COLUMN_TAG_FREQUENCY, frequency + 1);

		return database.update(DBConstants.TABLE_TAG, values,
				DBConstants.COLUMN_TAG_ID + " = " + tagId, null);
	}

	@Override
	public Tag getTagByBookmarkTime(final int videoId, final int bookmarkTime) {
		Cursor cursor = database.query(DBConstants.TABLE_TAG, TagColumns,
				DBConstants.COLUMN_TAG_VIDEO_ID + " = " + videoId + " and "
						+ DBConstants.COLUMN_TAG_BOOKMARK_TIME + " = "
						+ bookmarkTime, null, null, null, null);
		cursor.moveToFirst();
		if (cursor.getCount() != 0) {
			Tag tag = cursorToTag(cursor);
			cursor.close();
			Log.d(TAG, "tag present.");
			return tag;
		}
		return null;

	}

}
