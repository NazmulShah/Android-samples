/**
 * 
 */
package com.neevtech.imageprocessing.effects;

import java.util.Random;

import com.neevtech.imageprocessing.constants.Neevimagick;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * @author Ashish R Agre
 * 
 */
public class SnowEffect {

	public Bitmap showSnowEffect(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		Random robj = new Random();
		int R, G, B, index, thresHold = 50;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				index = y * width + x;
				R = Color.red(pixels[index]);
				G = Color.green(pixels[index]);
				B = Color.blue(pixels[index]);
				thresHold = robj.nextInt(Neevimagick.COLOR_MAX);
				// Log.e("Called", "again" + thresHold);
				if (R > thresHold && G > thresHold && B > thresHold) {
					pixels[index] = Color.rgb(Neevimagick.COLOR_MAX,
							Neevimagick.COLOR_MAX, Neevimagick.COLOR_MAX);
				}

			}
		}

		Bitmap bitOut = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		bitOut.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitOut;
	}

}
