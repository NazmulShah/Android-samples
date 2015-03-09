package com.neevtech.imageprocessing.paintutils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class DrawingPath implements ICanvasCommand {
	public Path path;
	public Paint paint;
	public String text;
	public float x,y;

	public void draw(Canvas canvas) {
		canvas.drawPath(path, paint);
	}

	public void drawText(Canvas canvas) {
		canvas.drawText(text, x, y, paint);
	}

	public void undo() {
		// Todo this would be changed later
	}

	public void reset(DrawingPath currentDrawingPath) {
		currentDrawingPath.reset(currentDrawingPath);
	}
}
