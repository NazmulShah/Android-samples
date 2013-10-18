package com.neevtech.imageprocessing.util;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * @author Shruti
 */

public class FlipFilter {
	public static Point[][] flipFilter(Bitmap b, boolean isVert, boolean isHorz) {
		int nWidth = b.getWidth();
		int nHeight = b.getHeight();
		Point[][] pt = new Point[nWidth][nHeight];
		for (int x = 0; x < nWidth; ++x) {
			for (int y = 0; y < nHeight; ++y) {
				pt[x][y] = new Point();
			}
		}

		for (int x = 0; x < nWidth; ++x) {
			for (int y = 0; y < nHeight; ++y) {
				pt[x][y].x = (isHorz) ? nWidth - (x + 1) : x;
				pt[x][y].y = (isVert) ? nHeight - (y + 1) : y;
			}
		}

		return pt;
	}
}
