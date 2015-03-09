/**
 * 
 */
package com.example.tagmyvideo.db;

import static com.example.tagmyvideo.constants.DBConstants.COLUMN_TAG_BOOKMARK_TIME;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_TAG_CAPTION;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_TAG_CREATION_TIME;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_TAG_FREQUENCY;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_TAG_ID;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_TAG_VIDEO_ID;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_VIDEO_FILE_NAME;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_VIDEO_FREQUENCY;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_VIDEO_ID;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_VIDEO_IS_BOOKMARKED;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_VIDEO_IS_SYNC;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_VIDEO_LENGTH;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_VIDEO_PATH;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_VIDEO_SYNC_TIME;
import static com.example.tagmyvideo.constants.DBConstants.COLUMN_VIDEO_THUMB;
import static com.example.tagmyvideo.constants.DBConstants.TABLE_TAG;
import static com.example.tagmyvideo.constants.DBConstants.TABLE_VIDEO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Nikhil & Prashant Prabhakar
 * 
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
	private static MySQLiteHelper sqLiteHelper;
	private final String TAG = MySQLiteHelper.class.getName();
	private static final String DATABASE_NAME = "tagsmyvideo.db";
	private static final int DATABASE_VERSION = 1;

	// video table create statement string.
	private final String VIDEO_DATABASE_CREATE = "" + "create table "
			+ TABLE_VIDEO + "(" + COLUMN_VIDEO_ID
			+ " integer primary key autoincrement, " + COLUMN_VIDEO_FILE_NAME
			+ " text not null, " + COLUMN_VIDEO_LENGTH + " integer, "
			+ COLUMN_VIDEO_IS_BOOKMARKED + " integer, " + COLUMN_VIDEO_IS_SYNC
			+ " integer, " + COLUMN_VIDEO_SYNC_TIME + " datetime, "
			+ COLUMN_VIDEO_FREQUENCY + " integer, " + COLUMN_VIDEO_PATH
			+ " text not null, " + COLUMN_VIDEO_THUMB + " integer );";

	// tag table create statement string
	private final String TAG_DATABASE_CREATE = "" + "create table " + TABLE_TAG
			+ "(" + COLUMN_TAG_ID + " integer primary key autoincrement, "
			+ COLUMN_TAG_CAPTION + " text not null, " + COLUMN_TAG_VIDEO_ID
			+ " integer not null, " + COLUMN_TAG_CREATION_TIME + " datetime,"
			+ COLUMN_TAG_BOOKMARK_TIME + " integer not null ,"
			+ COLUMN_TAG_FREQUENCY + " integer, " + "FOREIGN KEY ("
			+ COLUMN_TAG_VIDEO_ID + ") REFERENCES " + TABLE_VIDEO + "("
			+ COLUMN_VIDEO_ID + ") ON DELETE CASCADE);";

	private MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		Log.d(TAG, "Creation of tables...." + VIDEO_DATABASE_CREATE);
		database.execSQL(VIDEO_DATABASE_CREATE);

		Log.d(TAG, "Creation of tables...." + TAG_DATABASE_CREATE);
		database.execSQL(TAG_DATABASE_CREATE);
		Log.d(TAG, "Tables created....");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
		onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	public static MySQLiteHelper getInstance(Context context) {
		if (sqLiteHelper == null) {
			sqLiteHelper = new MySQLiteHelper(context);
		}
		return sqLiteHelper;
	}

}