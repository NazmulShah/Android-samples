package com.neevtech.imageprocessing.paintutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.neevtech.imageprocessing.R;

/**
 * @author Shruti
 * 
 */
public class DrawingSurface extends SurfaceView implements
		SurfaceHolder.Callback {

	public DrawThread thread;
	private Bitmap mBitmap;
	public boolean isDrawing = true;
	/* public boolean isWriting = false; */
	private Canvas canvas;
	public Canvas c;
	private Context context;
	public DrawingPath previewPath;
	private CommandManager commandManager;

	public DrawingSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		/*
		 * this.setBackgroundColor(Color.TRANSPARENT);
		 * this.setZOrderOnTop(true); //necessary
		 * getHolder().setFormat(PixelFormat.TRANSPARENT);
		 */
		this.context = context;
		getHolder().addCallback(this);
		commandManager = new CommandManager(context);
		thread = new DrawThread(getHolder());
	}

	private Handler previewDoneHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			isDrawing = false;
		}
	};

	public void addDrawingPath(DrawingPath drawingPath) {
		commandManager.addCommand(drawingPath);
	}

	public void deleteDrawingPath(DrawingPath drawingPath) {
		commandManager.addCommand(drawingPath);
	}

	public void drawText(StringPath text) {
		commandManager.addCommand(text);
	}

	public boolean hasMoreRedo() {
		return commandManager.hasMoreRedo();
	}

	public void redo() {
		isDrawing = true;
		commandManager.redo();

	}

	public void undo() {
		isDrawing = true;
		// isWriting = false;
		commandManager.undo();
	}

	public void undo1() {
		// isWriting = true;
		isDrawing = false;
		commandManager.undo1();
	}

	public boolean hasMoreUndo() {
		return commandManager.hasMoreUndo();
	}

	public boolean hasMoreUndo1() {
		return commandManager.hasMoreUndo1();
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		;
	}

	public void surfaceCreated(SurfaceHolder holder) {

		thread = new DrawThread(getHolder());
		thread.setRunning(true);
		isDrawing = true;
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("test-------2", "test---------2");
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
	}

	public int[] ds(int height, int width) {
		System.out.println("Inside ds method");
		int[] arr = new int[2];
		arr[0] = height;
		arr[1] = width;
		return arr;
	}

	/*
	 * public void drawPath(DrawingPath nextDrawingPath, Paint currentPaint) {
	 * nextDrawingPath.draw(c); }
	 */

	public class DrawThread extends Thread {

		private SurfaceHolder mSurfaceHolder;
		private volatile boolean _run;
		private Bitmap bmp;

		// private Paint paint;

		public DrawThread(SurfaceHolder surfaceHolder) {
			mSurfaceHolder = surfaceHolder;
			bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.transparent);

			if (bmp == null) {
				bmp = BitmapFactory.decodeResource(getResources(),
						R.drawable.transparent);
			}
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			int width = display.getWidth();
			int height = display.getHeight();
			bmp = Bitmap.createScaledBitmap(bmp, width, height, true);

		}

		public void setRunning(boolean run) {
			_run = run;
		}

		@Override
		public void run() {
			while (_run) {
				canvas = null;
				if (isDrawing == true) {
					try {
						/*
						 * if (mBitmap != null) { mBitmap.recycle(); }
						 */
						mBitmap =  Bitmap.createScaledBitmap(bmp,
									  bmp.getWidth(), bmp.getHeight(),
									  true).copy( Bitmap.Config.ARGB_8888,
									  true)
									 ;
						canvas = mSurfaceHolder.lockCanvas(null);

						c = new Canvas(mBitmap);
						// canvas.setBitmap(mBitmap);

						synchronized (mSurfaceHolder) {
							canvas.save();
							canvas.drawColor(0, PorterDuff.Mode.CLEAR);

							commandManager.executeAll(c, previewDoneHandler);
							previewPath.draw(c);
							canvas.drawBitmap(mBitmap, 0, 0, null);
							canvas.restore();
						}
						// }

						// canvas = new Canvas(mBitmap);
						// Log.e("DrawingSurface", "Initialised");
						// c.drawColor(0, PorterDuff.Mode.CLEAR);
						// canvas.drawColor(0, PorterDuff.Mode.CLEAR);
						// canvas.drawColor(0, PorterDuff.Mode.CLEAR);

						/*
						 * if (PaintActivity.neonMode == true) { int size =
						 * CommandManager.currentStack.size(); if (size != 0) {
						 * DrawingPath myPath = CommandManager.currentStack
						 * .get(size - 1); myPath.draw(c); } }
						 */

						// Log.e("Drawing bitmap", "Drawn");
					} finally {
						if (canvas != null) {
							mSurfaceHolder.unlockCanvasAndPost(canvas);
						}
					}

				}

			}
			Log.d("test-------", "test---------");
		}
	}
}
