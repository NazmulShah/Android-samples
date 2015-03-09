/**
 * 
 */
package com.neevtech.crud_demo.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.neevtech.crud_demo.model.User;

/**
 * @author prashant
 * 
 */
public class UserDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_PHONE_NO,
			MySQLiteHelper.COLUMN_ADDRESS };

	public UserDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void createUser(User user) {

		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_NAME, user.getName());
		values.put(MySQLiteHelper.COLUMN_PHONE_NO, user.getPhoneNo());
		values.put(MySQLiteHelper.COLUMN_ADDRESS, user.getAddress());

		long insertId = database.insert(MySQLiteHelper.TABLE_USERS, null,
				values);

		System.out.println("contact created with Id:" + insertId);

	}

	public void deleteUser(int id) {

		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_USERS, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public void updateUser(User user) {

		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_ID, user.getId());
		values.put(MySQLiteHelper.COLUMN_NAME, user.getName());
		values.put(MySQLiteHelper.COLUMN_PHONE_NO, user.getPhoneNo());
		values.put(MySQLiteHelper.COLUMN_ADDRESS, user.getAddress());

		database.update(MySQLiteHelper.TABLE_USERS, values,
				MySQLiteHelper.COLUMN_ID + " = " + user.getId(), null);
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = cursorToUser(cursor);
			users.add(user);
			cursor.moveToNext();
		}
		cursor.close();
		return users;
	}

	private User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getInt(0));
		user.setName(cursor.getString(1));
		user.setPhoneNo(cursor.getString(2));
		user.setAddress(cursor.getString(3));
		return user;
	}

	public User findUserById(int id) {
		Cursor cursor = database.rawQuery("select * from "
				+ MySQLiteHelper.TABLE_USERS + " where "
				+ MySQLiteHelper.COLUMN_ID + "= " + id, null);
		Log.i("Cursor Count",
				"The current Cursor Count is " + cursor.getCount());
		cursor.moveToFirst();
		User user = cursorToUser(cursor);

		Log.i("ContactDataSource", user.toString());
		return user;
	}

}
