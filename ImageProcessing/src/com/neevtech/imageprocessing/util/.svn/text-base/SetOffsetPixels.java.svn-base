package com.neevtech.imageprocessing.util;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

/**
 * @author Shruti
 */

public class SetOffsetPixels {
	public static Bitmap offsetFilterAbs(Bitmap b, Point[][] offset) {
		int nWidth = b.getWidth();
		int nHeight = b.getHeight();

		int xOffset, yOffset;

		for (int y = 0; y < nHeight; ++y) {
			for (int x = 0; x < nWidth; ++x) {
				xOffset = offset[x][y].x;
				yOffset = offset[x][y].y;

				if (yOffset >= 0 && yOffset < nHeight && xOffset >= 0
						&& xOffset < nWidth) {
					Log.e("Setting pixel value", "xOffset" + xOffset
							+ "yOffset" + yOffset);
					b.setPixel(x, y, b.getPixel(xOffset, yOffset));
				}
			}
		}

		return b;
	}
}
