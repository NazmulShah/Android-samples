package com.neevtech.imageprocessing.effects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author Shruti
 */
public class GrayScaleEffect {
	public static void grayscaleEffect(Bitmap myImage, ColorMatrix colorMatrix,
			ColorMatrixColorFilter cmFilter, Paint cmPaint, Canvas cv,
			ImageView image, Bitmap canvasBitmap) {
		Log.d("Gray scale effect clicked", "..");
		colorMatrix.setSaturation(0);
		cmFilter = new ColorMatrixColorFilter(colorMatrix);
		cmPaint.setColorFilter(cmFilter);
		cv.drawBitmap(myImage, 0, 0, cmPaint);
		image.setImageBitmap(canvasBitmap);

	}
}
