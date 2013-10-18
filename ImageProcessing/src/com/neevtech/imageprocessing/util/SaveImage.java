package com.neevtech.imageprocessing.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Shruti
 */
public class SaveImage {

	public static void imageSave(String extStorageDirectory, String picname,
			Context context, ImageView image, int j) {

		File direct = new File(extStorageDirectory + "/ImageProcessing");
		if ((direct.exists()) == false) {
			boolean success = direct.mkdir();
			Log.e("Created folder" + success,
					"Path of it" + direct.getAbsolutePath());
		}
		SaveImage
		.savingOfImage(extStorageDirectory, picname, image, context, j);

	}

	public static void imageSave(String extStorageDirectory, String picname,
			Context context, Bitmap image, int j) {

		File direct = new File(extStorageDirectory + "/ImageProcessing");
		if ((direct.exists()) == false) {
			boolean success = direct.mkdir();
			Log.e("Created folder" + success,
					"Path of it" + direct.getAbsolutePath());
		}
		SaveImage.savingOfBitMap(extStorageDirectory, picname, image, context,
				j);

	}

	public static void savingOfImage(String extStorageDirectory,
			String picname, ImageView image, Context context, int j) {
		OutputStream outStream1 = null;
		File file1 = new File(extStorageDirectory + "/ImageProcessing/",
				picname + j++ + ".JPEG");
		try {
			outStream1 = new FileOutputStream(file1);
			image.buildDrawingCache();
			Bitmap bmap = image.getDrawingCache();
			bmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream1);
			outStream1.flush();
			outStream1.close();
			Toast.makeText(context, "Saved to SD Card", Toast.LENGTH_LONG)
			.show();
			Log.e("Path" + file1.getAbsolutePath(), "...");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	public static String savingOfBitMap(String extStorageDirectory,
			String picname, Bitmap image, Context context, int j) {
		File direct = new File(extStorageDirectory + "/ImageProcessing");
		if ((direct.exists()) == false) {
			boolean success = direct.mkdir();
			Log.e("Created folder" + success,
					"Path of it" + direct.getAbsolutePath());
		}
		OutputStream outStream2 = null;
		File file1 = new File(extStorageDirectory + "/ImageProcessing/",
				picname + j++ + ".JPEG");
		try {
			outStream2 = new FileOutputStream(file1);
			image.compress(Bitmap.CompressFormat.JPEG, 100, outStream2);
			outStream2.flush();
			outStream2.close();
			Toast.makeText(context, "Saved to SD Card", Toast.LENGTH_LONG)
			.show();
			Log.e("Path..." + file1.getAbsolutePath(), "...");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
		return file1.getAbsolutePath();
	}
}
