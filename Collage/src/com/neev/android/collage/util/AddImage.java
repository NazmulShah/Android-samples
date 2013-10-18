package com.neev.android.collage.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

public class AddImage extends Activity{
	ImageView newImageView;
	private static Bitmap newImage;
	public AddImage(Uri _uri, Context context) {
		
		// User had pick an image.
					Cursor cursor = getContentResolver().query(_uri,
									new String[] { 
							android.provider.MediaStore.Images.ImageColumns.DATA },
									null, null, null);
					cursor.moveToFirst();

					// Link to the image
					final String imageFilePath = cursor.getString(0);
					Log.e("imageFilePath", imageFilePath);
					File photos = new File(imageFilePath);
					// newImage = BitmapFactory.decodeFile(imageFilePath);
					newImage = decodeFile(photos);
					newImage = Bitmap.createScaledBitmap(newImage, 150, 150,
							true);
					// ImageView imageView = (ImageView)
					// findViewById(R.id.anImage);
					// saturationImage.setImageBitmap(b);
					
					cursor.close();
					System.out.println("start creation of new image");
					
					
					
					
					newImageView = new ImageView(context);
					newImageView.setImageBitmap(newImage);
					newImageView.setMaxHeight(100);
					newImageView.setMaxWidth(100);
					newImageView.setMinimumHeight(50);
					newImageView.setMinimumWidth(50);
//					newImageView.setLayoutParams(LayoutParams.WRAP_CONTENT);
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

}
