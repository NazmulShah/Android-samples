/**
 * 
 */
package com.neevtech.imageprocessing.effects.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * @author Shruti
 * 
 */
public class HueFilter {
	// Hue adjusting for image
	public static Bitmap adjustedHue(Bitmap o, int deg) {
		Bitmap srca = o;
		Bitmap bitmap = srca.copy(Bitmap.Config.ARGB_8888, true);
		for (int x = 0; x < bitmap.getWidth(); x++)
			for (int y = 0; y < bitmap.getHeight(); y++) {
				int newPixel = hueChange(bitmap.getPixel(x, y), deg);
				bitmap.setPixel(x, y, newPixel);
			}

		return bitmap;
	}

	public static int hueChange(int startpixel, int deg) {
		float[] hsv = new float[3]; // array to store HSV values
		Color.colorToHSV(startpixel, hsv); // get original HSV values of pixel
		hsv[0] = hsv[0] + deg; // add the shift to the HUE of HSV array
		hsv[0] = hsv[0] % 360; // confines hue to values:[0,360]
		return Color.HSVToColor(Color.alpha(startpixel), hsv);
	}
}
