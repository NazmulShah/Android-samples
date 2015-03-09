package com.neevtech.imageprocessing.effects;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * @author Shruti
 */
public class InvertEffect {
	public static Bitmap invert(Bitmap src) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// color information
		int A, R, G, B;
		int pixel;

		// scan through all pixels
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				// get color on each channel
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);
				// set new pixel color to output image
				bmOut.setPixel(x, y,
						Color.argb(255 - A, 255 - R, 255 - G, 255 - B));
			}
		}

		// return final image
		return bmOut;
	}

}
