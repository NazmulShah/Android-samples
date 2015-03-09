package com.neevtech.imageprocessing;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.neevtech.imageprocessing.ui.CustomDialog;
import com.neevtech.imageprocessing.ui.ImageAdapter;
import com.neevtech.imageprocessing.util.MemCacheManager;

/**
 * @author Shruti
 * 
 */
public class PhotoChooserActivity extends Activity implements OnClickListener {
	private static final int PROGRESS_DIALOG = 0;
	Button ourphoto, pickimage, captureimage;
	private Bitmap image;
	private ProgressDialog dialog;
	private Uri picUri;
	public static ActivityManager am;
	private int width, height;

	// private static LruCache<String, Bitmap> mMemoryCache;

	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.first);
		}
	}

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first);
		ourphoto = (Button) findViewById(R.id.ourphoto);
		pickimage = (Button) findViewById(R.id.pickimage);
		captureimage = (Button) findViewById(R.id.captureimage);
		ourphoto.setOnClickListener(PhotoChooserActivity.this);
		pickimage.setOnClickListener(PhotoChooserActivity.this);
		captureimage.setOnClickListener(PhotoChooserActivity.this);
		am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		MemCacheManager.initializeCache();
	}

	// On click
	public void onClick(View v) {
		if (v == ourphoto) {
			final Dialog dialog = new Dialog(PhotoChooserActivity.this,
					R.style.FullscreenTheme);
			dialog.setContentView(R.layout.tryourphoto);
			dialog.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_DIM_BEHIND);

			// dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			GridView gridView = (GridView) dialog.findViewById(R.id.grid_view);
			gridView.setAdapter(new ImageAdapter(this, width, height));
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					Log.d("Inside on item click", "Of grid view");
					ImageAdapter imageAdapter = new ImageAdapter(
							PhotoChooserActivity.this, width, height);
					image = BitmapFactory.decodeResource(getResources(),
							imageAdapter.mThumbIds[position]);
					MemCacheManager.addBitmapToMemoryCache("myImage", image);
					dialog.dismiss();
					AsyncTaskForNextActivity task = new AsyncTaskForNextActivity();
					task.execute();
					finish();
				}
			});
		}

		else if (v == pickimage) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			intent.putExtra("return-data", true);
			startActivityForResult(intent, 1);
		}

		else if (v == captureimage) {
			try {
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);
			} catch (ActivityNotFoundException anfe) {
				// display an error message
				String errorMessage = "Whoops - your device doesn't support capturing images!";
				Toast toast = Toast.makeText(this, errorMessage,
						Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if (requestCode == 1 && data != null && data.getData() != null) {
				Uri _uri = data.getData();

				if (_uri != null) {
					// User had pick an image.
					Cursor cursor = getContentResolver()
							.query(_uri,
									new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
									null, null, null);
					cursor.moveToFirst();

					// Link to the image
					final String imageFilePath = cursor.getString(0);
					Log.e("imageFilePath", imageFilePath);
					File photos = new File(imageFilePath);
					// Bitmap gallimage = decodeFile(photos);
					image = decodeFile(photos);
					MemCacheManager.addBitmapToMemoryCache("myImage", image);
					AsyncTaskForNextActivity task = new AsyncTaskForNextActivity();
					task.execute();
					cursor.close();
				}
			}
		case 0:
			if (requestCode == 0 && data != null && data.getData() != null) {
				picUri = data.getData();
				Log.d("Pic uri", "" + picUri);
				image = (Bitmap) data.getExtras().get("data");
				MemCacheManager.addBitmapToMemoryCache("myImage", image);
				AsyncTaskForNextActivity task = new AsyncTaskForNextActivity();
				task.execute();
			}
			// super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale++;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	public void onBackPressed() {
		CustomDialog.showDialog(PhotoChooserActivity.this,
				"Do you want to quit the application", true, "Quit", "Yes",
				"No");
	}

	public class AsyncTaskForNextActivity extends
			AsyncTask<String, Void, String> {

		@SuppressWarnings("deprecation")
		protected void onPreExecute() {
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected String doInBackground(String... params) {
			Intent i = new Intent(PhotoChooserActivity.this,
					ImageProcessingActivity.class);
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.PNG, 80, bs);
			i.putExtra("byteArray", bs.toByteArray());
			startActivity(i);
			finish();
			return null;
		}

		@SuppressWarnings("deprecation")
		protected void onPostExecute(String result) {
			if (dialog != null)
				removeDialog(PROGRESS_DIALOG);
		}

	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG:
			dialog = new ProgressDialog(PhotoChooserActivity.this);
			dialog.setMessage("Processing image. Please wait");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		return null;
	}

	/*
	 * public static void addBitmapToMemoryCache(String key, Bitmap bitmap) { if
	 * (getBitmapFromMemCache(key) == null) { mMemoryCache.put(key, bitmap);
	 * Log.e("Added", "Your bitmap"); } else { mMemoryCache.remove(key);
	 * mMemoryCache.put(key, bitmap); } }
	 * 
	 * public static Bitmap getBitmapFromMemCache(String key) { // Bitmap bmp =
	 * null; if (mMemoryCache.get(key) == null) { Log.e("No bitmap", "Found");
	 * // bmp = BitmapFactory.decodeResource(getResources(), //
	 * R.drawable.flowers1); } return mMemoryCache.get(key); }
	 */

}
