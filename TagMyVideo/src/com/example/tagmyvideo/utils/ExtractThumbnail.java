<<<<<<< HEAD
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * @author Ashish R Agre
 * 
 */
public class ExtractThumbnail {
	private Context context;
	private byte[] imgData;

	public ExtractThumbnail(Context context) {
		this.context = context;
	}

	public byte[] getImageBlob(long id) {
		ContentResolver crThumb = context.getContentResolver();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, id,
				MediaStore.Video.Thumbnails.MINI_KIND, options);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		curThumb.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] imgblob = baos.toByteArray();
		this.imgData = imgblob;
		if (imgData.length > 0) {
			return this.imgData;
		} else {
			return null;
		}
	}

	public Bitmap getBitmap() {
		Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
		return bmp;
	}

	public int getMedistoreThumbId(File file) {
		Log.e("getMedistoreThumbId", "" + file.getAbsolutePath());
		// Uri uri = Uri.parse(file.getAbsolutePath());
		Uri uri = getImageContentUri(context, file);
		// Uri tempUri = Uri.parse("content://media/external/videos/media");
		Cursor cursor = null;
		if (uri == null) {
			cursor = context.getContentResolver().query(
					MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
					new String[] { MediaStore.Video.Media.DATA,
							MediaStore.Video.Media.DISPLAY_NAME,
							MediaStore.Video.Media._ID,
							MediaStore.Video.Media.TITLE,
							MediaStore.Video.Media.MIME_TYPE }, null, null,
					null);
		} else {
			cursor = context.getContentResolver().query(
					uri,
					new String[] { MediaStore.Video.Media.DATA,
							MediaStore.Video.Media.DISPLAY_NAME,
							MediaStore.Video.Media._ID,
							MediaStore.Video.Media.TITLE,
							MediaStore.Video.Media.MIME_TYPE }, null, null,
					null);
		}

		cursor.moveToFirst();
		int idDB = cursor.getInt(cursor
				.getColumnIndex(MediaStore.Video.Media._ID));
		cursor.close();
		return idDB;
	}

	/*
	 * private int getVideoIdFromFilePath(String filePath, ContentResolver
	 * contentResolver) {
	 * 
	 * long videoId = -1; Log.e("getVideoIdFromFilePath", "Loading file " +
	 * filePath);
	 * 
	 * // This returns us content://media/external/videos/media (or something //
	 * like that) // I pass in "external" because that's the MediaStore's name
	 * for the // external // storage on my device (the other possibility is
	 * "internal") Uri videosUri =
	 * MediaStore.Video.Media.getContentUri("external");
	 * 
	 * Log.e("getVideoIdFromFilePath", "videosUri = " + videosUri.toString());
	 * 
	 * String[] projection = { MediaStore.Video.Media._ID };
	 * 
	 * // TODO This will break if we have no matching item in the MediaStore.
	 * Cursor cursor = contentResolver.query(videosUri, projection,
	 * MediaStore.Video.VideoColumns.DATA + " LIKE ?", new String[] { filePath
	 * }, null); cursor.moveToFirst();
	 * 
	 * int columnIndex = cursor.getColumnIndex(projection[0]); videoId =
	 * cursor.getLong(columnIndex);
	 * 
	 * Log.e("getVideoIdFromFilePath", "Video ID is " + videoId);
	 * cursor.close(); return columnIndex; }
	 */

	public static Uri getImageContentUri(Context context, File imageFile) {
		String filePath = imageFile.getAbsolutePath();
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Video.VideoColumns._ID },
				MediaStore.Video.VideoColumns.DATA + "=? ",
				new String[] { filePath }, null);
		if (cursor != null && cursor.moveToFirst()) {
			int id = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Video.VideoColumns._ID));
			Uri baseUri = Uri.parse("content://media/external/video/media");
			cursor.close();
			return Uri.withAppendedPath(baseUri, "" + id);
		} else {
			if (imageFile.exists()) {
				ContentValues values = new ContentValues();
				values.put(MediaStore.Video.VideoColumns.DATA, filePath);
				//TODO: cursor could be null
				cursor.close();
				return context.getContentResolver().insert(
						MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
			} else {
				if (cursor != null)
					cursor.close();
				return null;
			}
		}
	}
}
=======
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * @author Ashish R Agre
 * 
 */
public class ExtractThumbnail {
	private Context context;
	private byte[] imgData;

	public ExtractThumbnail(Context context) {
		this.context = context;
	}

	public byte[] getImageBlob(long id) {
		ContentResolver crThumb = context.getContentResolver();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, id,
				MediaStore.Video.Thumbnails.MINI_KIND, options);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		curThumb.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] imgblob = baos.toByteArray();
		this.imgData = imgblob;
		if (imgData.length > 0) {
			return this.imgData;
		} else {
			return null;
		}
	}

	public Bitmap getBitmap() {
		Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
		return bmp;
	}

	public int getMedistoreThumbId(File file) {
		Log.e("getMedistoreThumbId", "" + file.getAbsolutePath());
		// Uri uri = Uri.parse(file.getAbsolutePath());
		Uri uri = getImageContentUri(context, file);
		// Uri tempUri = Uri.parse("content://media/external/videos/media");
		Cursor cursor = null;
		if (uri == null) {
			cursor = context.getContentResolver().query(
					MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
					new String[] { MediaStore.Video.Media.DATA,
							MediaStore.Video.Media.DISPLAY_NAME,
							MediaStore.Video.Media._ID,
							MediaStore.Video.Media.TITLE,
							MediaStore.Video.Media.MIME_TYPE }, null, null,
					null);
		} else {
			cursor = context.getContentResolver().query(
					uri,
					new String[] { MediaStore.Video.Media.DATA,
							MediaStore.Video.Media.DISPLAY_NAME,
							MediaStore.Video.Media._ID,
							MediaStore.Video.Media.TITLE,
							MediaStore.Video.Media.MIME_TYPE }, null, null,
					null);
		}

		cursor.moveToFirst();
		int idDB = cursor.getInt(cursor
				.getColumnIndex(MediaStore.Video.Media._ID));
		cursor.close();
		return idDB;
	}

	/*
	 * private int getVideoIdFromFilePath(String filePath, ContentResolver
	 * contentResolver) {
	 * 
	 * long videoId = -1; Log.e("getVideoIdFromFilePath", "Loading file " +
	 * filePath);
	 * 
	 * // This returns us content://media/external/videos/media (or something //
	 * like that) // I pass in "external" because that's the MediaStore's name
	 * for the // external // storage on my device (the other possibility is
	 * "internal") Uri videosUri =
	 * MediaStore.Video.Media.getContentUri("external");
	 * 
	 * Log.e("getVideoIdFromFilePath", "videosUri = " + videosUri.toString());
	 * 
	 * String[] projection = { MediaStore.Video.Media._ID };
	 * 
	 * // TODO This will break if we have no matching item in the MediaStore.
	 * Cursor cursor = contentResolver.query(videosUri, projection,
	 * MediaStore.Video.VideoColumns.DATA + " LIKE ?", new String[] { filePath
	 * }, null); cursor.moveToFirst();
	 * 
	 * int columnIndex = cursor.getColumnIndex(projection[0]); videoId =
	 * cursor.getLong(columnIndex);
	 * 
	 * Log.e("getVideoIdFromFilePath", "Video ID is " + videoId);
	 * cursor.close(); return columnIndex; }
	 */

	public static Uri getImageContentUri(Context context, File imageFile) {
		String filePath = imageFile.getAbsolutePath();
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Video.VideoColumns._ID },
				MediaStore.Video.VideoColumns.DATA + "=? ",
				new String[] { filePath }, null);
		if (cursor != null && cursor.moveToFirst()) {
			int id = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Video.VideoColumns._ID));
			Uri baseUri = Uri.parse("content://media/external/video/media");
			cursor.close();
			return Uri.withAppendedPath(baseUri, "" + id);
		} else {
			if (imageFile.exists()) {
				ContentValues values = new ContentValues();
				values.put(MediaStore.Video.VideoColumns.DATA, filePath);
				//TODO: cursor could be null
				cursor.close();
				return context.getContentResolver().insert(
						MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
			} else {
				if (cursor != null)
					cursor.close();
				return null;
			}
		}
	}
}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
