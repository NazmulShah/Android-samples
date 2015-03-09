package com.neevtech.imageprocessing.effects.filters;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.neevtech.imageprocessing.constants.*;

/**
 * @author Shruti
 */
public class BlackFilter {
	// For applying black filter effect for the image
	public static Bitmap applyBlackFilter(Bitmap source) {
		// get image size
		int width = source.getWidth();
		int height = source.getHeight();
		int[] pixels = new int[width * height];
		// get pixel array from source
		source.getPixels(pixels, 0, width, 0, 0, width, height);
		// random object
		Random random = new Random();

		int R, G, B, index = 0, thresHold = 0;
		// iteration through pixels
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// get current index in 2D-matrix
				index = y * width + x;
				// get color
				R = Color.red(pixels[index]);
				G = Color.green(pixels[index]);
				B = Color.blue(pixels[index]);
				// generate threshold
				thresHold = random.nextInt(Neevimagick.COLOR_MAX);
				if (R < thresHold && G < thresHold && B < thresHold) {
					pixels[index] = Color.rgb(Neevimagick.COLOR_MIN,
							Neevimagick.COLOR_MIN, Neevimagick.COLOR_MIN);
				}
			}
		}
		// output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
		return bmOut;
	}
}
