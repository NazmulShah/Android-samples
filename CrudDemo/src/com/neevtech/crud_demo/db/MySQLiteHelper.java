/**
 * 
 */
package com.neevtech.crud_demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author prashant
 * 
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_USERS = "users";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PHONE_NO = "phone_no";
	public static final String COLUMN_ADDRESS = "address";

	private static final String DATABASE_NAME = "contacts.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	// Open Database by saving to s3db then use software:
	// http://sqliteadmin.orbmu2k.de/
	private static final String DATABASE_CREATE = "create table " + TABLE_USERS
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_NAME + " text not null," + COLUMN_PHONE_NO
			+ " text not null," + COLUMN_ADDRESS + " text not null" + ");";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		onCreate(db);
	}
}