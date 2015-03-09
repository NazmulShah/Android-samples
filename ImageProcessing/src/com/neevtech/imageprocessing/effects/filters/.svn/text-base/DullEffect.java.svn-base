package com.neevtech.imageprocessing.effects.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * @author Shruti
 */
public class DullEffect {
	// For giving dull effect to the image
	public static Bitmap doColorFilter(Bitmap src) {

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

				// apply filtering on each channel R, G, B

				A = Color.alpha(pixel);

				R = (int) ((Color.red(pixel)) / 2);

				G = (int) ((Color.green(pixel)) / 2);

				B = (int) ((Color.blue(pixel)) / 2);

				// set new color pixel to output bitmap

				bmOut.setPixel(x, y, Color.argb(A, R, G, B));

			}

		}

		// return final image

		return bmOut;

	}
}
