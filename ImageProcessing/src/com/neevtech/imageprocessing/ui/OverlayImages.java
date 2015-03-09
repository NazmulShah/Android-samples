package com.neevtech.imageprocessing.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class OverlayImages {
	public static Bitmap overlayImages(Bitmap bmp1, Bitmap bmp2) {
		Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(),
				bmp1.getHeight(), bmp1.getConfig());
		Canvas canvas = new Canvas(bmOverlay);
		canvas.drawBitmap(bmp1, new Matrix(), null);
		canvas.drawBitmap(bmp2, new Matrix(), null);
		return bmOverlay;
	}

	public static Bitmap overlayBorderAndImage(Bitmap bmp1, Bitmap bmp2) {
		Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(),
				bmp1.getHeight(), bmp1.getConfig());
		Canvas canvas = new Canvas(bmOverlay);
		Matrix small = new Matrix();
		small.postTranslate(31, 14);
		canvas.drawBitmap(bmp1, new Matrix(), null);
		canvas.drawBitmap(bmp2, small, null);
		return bmOverlay;
	}
}
