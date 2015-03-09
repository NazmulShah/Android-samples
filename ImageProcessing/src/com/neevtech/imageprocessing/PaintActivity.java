package com.neevtech.imageprocessing;

import java.io.File;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.CornerPathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.neevtech.imageprocessing.paintutils.Brush;
import com.neevtech.imageprocessing.paintutils.ColorPickerDialog;
import com.neevtech.imageprocessing.paintutils.DrawingPath;
import com.neevtech.imageprocessing.paintutils.DrawingSurface;
import com.neevtech.imageprocessing.paintutils.PenBrush;
import com.neevtech.imageprocessing.ui.OverlayImages;
import com.neevtech.imageprocessing.util.MemCacheManager;
import com.neevtech.imageprocessing.util.SaveImage;
import com.neevtech.imageprocessing.util.ShareImage;

/**
 * @author Shruti
 * 
 */
public class PaintActivity extends BaseActivity implements OnClickListener,
		OnTouchListener, ColorPickerDialog.OnColorChangedListener {
	private DrawingSurface drawingSurface;
	private boolean isDrawMode;
	private Paint currentPaint;
	private int strokewidth, lastColor, tempColor;
	private boolean isEmboss, isSpray, isInterlace, isRainbow, isParallel,
			isHollow;
	public static boolean isSaved, isShared;
	private String picname = "mypic";
	private String extStorageDirectory;
	private Brush currentBrush;
	private DrawingPath currentDrawingPath;
	private LinearLayout brushLayout, colorLayout, undoLayout, redoLayout,
			saveLayout, brushWidthLayout;
	private Dialog listDialog, brushDialog;
	private ListView list;
	private MaskFilter emboss, blur;
	private ProgressDialog dialog;
	private SeekBar brushWidth;
	private Button brushSet, brushCancel;
	private final int SAVE_IMAGE = 2;
	private final int PROGRESS_DIALOG = 3;
	private LinearLayout shareLayout;
	private static final int SAVE_DIALOG = 4;
	private String shareImageFilePath;
	private boolean isEraseMode;
	private LinearLayout eraseLayout, linear1;
	private Bitmap selectedImage;

	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.paintapppage);
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		Log.e("In onCreate", "Paint activity");
		inflateMenu = true;
		super.onCreate(savedInstanceState);
		initializeViews();
		loadScroller();
		initializeLayouts();
		settingOnClickListeners();
	}

	private void initializeViews() {
		LayoutInflater inflater = LayoutInflater.from(PaintActivity.this);
		app = inflater.inflate(R.layout.paintapppage, null);

		// Menu button initialization
		LinearLayout topBar = (LinearLayout) app.findViewById(R.id.topBar);
		linear1 = (LinearLayout) app.findViewById(R.id.linear1);
		selectedImage = MemCacheManager.getBitmapFromMemCache("changedImage");
		Drawable d = new BitmapDrawable(getResources(), selectedImage);
		linear1.setBackgroundDrawable(d);

		menuButton = (ImageView) topBar.findViewById(R.id.paintmenu);
		menuButton.bringToFront();

		// Drawing surface initialisation
		drawingSurface = (DrawingSurface) linear1
				.findViewById(R.id.drawingSurface);

		drawingSurface.bringToFront();
		drawingSurface.setZOrderOnTop(true); // necessary
		drawingSurface.getHolder().setFormat(PixelFormat.TRANSPARENT);

		strokewidth = 20;
		isDrawMode = true;
		isSaved = true;
		isEmboss = false;
		isSpray = false;
		isInterlace = false;
		isRainbow = false;
		isParallel = false;
		isHollow = false;
		isShared = false;
		isEraseMode = false;

		extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();

		setCurrentPaint();

		// Drawing surface utils
		currentBrush = new PenBrush();
		drawingSurface.setOnTouchListener(PaintActivity.this);
		drawingSurface.previewPath = new DrawingPath();
		drawingSurface.previewPath.path = new Path();
		drawingSurface.previewPath.paint = getPreviewPaint();

	}

	// Menu options layouts
	private void initializeLayouts() {
		brushLayout = (LinearLayout) menu.findViewById(R.id.brushLayout);
		colorLayout = (LinearLayout) menu.findViewById(R.id.colorLayout);
		undoLayout = (LinearLayout) menu.findViewById(R.id.undoLayout);
		redoLayout = (LinearLayout) menu.findViewById(R.id.redoLayout);
		saveLayout = (LinearLayout) menu.findViewById(R.id.saveLayout);
		brushWidthLayout = (LinearLayout) menu
				.findViewById(R.id.brushWidthLayout);
		shareLayout = (LinearLayout) menu.findViewById(R.id.shareLayout);
		eraseLayout = (LinearLayout) menu.findViewById(R.id.eraseLayout);
	}

	// On touch method called on touch of drawing surface
	@Override
	public boolean onTouch(View v, MotionEvent motionEvent) {
		if (isDrawMode == true) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				drawingSurface.isDrawing = true;
				currentDrawingPath = new DrawingPath();
				currentDrawingPath.paint = currentPaint;
				currentDrawingPath.path = new Path();
				currentBrush.mouseDown(currentDrawingPath.path,
						motionEvent.getX(), motionEvent.getY());
				currentBrush.mouseDown(drawingSurface.previewPath.path,
						motionEvent.getX(), motionEvent.getY());

			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				drawingSurface.isDrawing = true;
				currentBrush.mouseMove(currentDrawingPath.path,
						motionEvent.getX(), motionEvent.getY());
				currentBrush.mouseMove(drawingSurface.previewPath.path,
						motionEvent.getX(), motionEvent.getY());

			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				currentBrush.mouseUp(drawingSurface.previewPath.path,
						motionEvent.getX(), motionEvent.getY());
				drawingSurface.previewPath.path = new Path();
				drawingSurface.addDrawingPath(currentDrawingPath);
				isSaved = false;
				Log.e("Drawing path", "Added new drawing path");
				currentBrush.mouseUp(currentDrawingPath.path,
						motionEvent.getX(), motionEvent.getY());
				Log.e("Drawing path", "Before check of isNeonMode"
						+ currentDrawingPath);
			}
		} else if (isEraseMode == true) {
			Log.e("Inside on touch of erase mode", "..................");
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				drawingSurface.isDrawing = true;
				currentDrawingPath = new DrawingPath();
				currentDrawingPath.paint = setCurrentPaintForErasing();
				currentDrawingPath.path = new Path();
				currentBrush.mouseDown(currentDrawingPath.path,
						motionEvent.getX(), motionEvent.getY());
				currentBrush.mouseDown(drawingSurface.previewPath.path,
						motionEvent.getX(), motionEvent.getY());

			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				drawingSurface.isDrawing = true;
				currentBrush.mouseMove(currentDrawingPath.path,
						motionEvent.getX(), motionEvent.getY());
				currentBrush.mouseMove(drawingSurface.previewPath.path,
						motionEvent.getX(), motionEvent.getY());

			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				currentBrush.mouseUp(drawingSurface.previewPath.path,
						motionEvent.getX(), motionEvent.getY());
				drawingSurface.previewPath.path = new Path();
				setCurrentPaintForErasing();
				drawingSurface.addDrawingPath(currentDrawingPath);
				isSaved = false;
				Log.e("Drawing path", "Added new drawing path in erase");
				currentBrush.mouseUp(currentDrawingPath.path,
						motionEvent.getX(), motionEvent.getY());
			}
		}
		return true;
	}

	// Setting paint attributes for erase
	private Paint setCurrentPaintForErasing() {
		currentPaint = new Paint();
		currentPaint.setAntiAlias(false);
		currentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		currentPaint.setStyle(Paint.Style.STROKE);
		currentPaint.setStrokeJoin(Paint.Join.ROUND);
		currentPaint.setStrokeCap(Paint.Cap.ROUND);
		currentPaint.setStrokeWidth(strokewidth);
		return currentPaint;
	}

	// Setting paint attributes
	private void setCurrentPaint() {
		currentPaint = new Paint();
		currentPaint.setDither(true);
		currentPaint.setColor(0xFFFFFF00);
		currentPaint.setStyle(Paint.Style.STROKE);
		currentPaint.setStrokeJoin(Paint.Join.ROUND);
		currentPaint.setStrokeCap(Paint.Cap.ROUND);
		currentPaint.setStrokeWidth(strokewidth);
	}

	// Setting paint attributes for brush
	private void setCurrentPaintForBrush() {
		currentPaint = new Paint();
		currentPaint.setDither(true);
		currentPaint.setColor(lastColor);
		currentPaint.setStyle(Paint.Style.STROKE);
		currentPaint.setStrokeJoin(Paint.Join.ROUND);
		currentPaint.setStrokeCap(Paint.Cap.ROUND);
		currentPaint.setStrokeWidth(strokewidth);
	}

	// Paint attributes for preview path
	private Paint getPreviewPaint() {
		final Paint previewPaint = new Paint();
		previewPaint.setColor(0xFFC1C1C1);
		previewPaint.setStyle(Paint.Style.STROKE);
		previewPaint.setStrokeJoin(Paint.Join.ROUND);
		previewPaint.setStrokeCap(Paint.Cap.ROUND);
		previewPaint.setStrokeWidth(strokewidth);
		return previewPaint;
	}

	@Override
	public void colorChanged(int color) {
		currentPaint.setColor(color);
	}

	// Setting on click listeners for menu options
	private void settingOnClickListeners() {
		if (brushLayout == null) {
			Log.e("Layout got is null", "" + brushLayout);
		}
		brushLayout.setOnClickListener(this);
		colorLayout.setOnClickListener(this);
		undoLayout.setOnClickListener(this);
		redoLayout.setOnClickListener(this);
		saveLayout.setOnClickListener(this);
		brushWidthLayout.setOnClickListener(this);
		shareLayout.setOnClickListener(this);
		eraseLayout.setOnClickListener(this);
	}

	// On click of menu options
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.undoLayout:
			drawingSurface.undo();
			break;

		case R.id.redoLayout:
			drawingSurface.redo();
			break;

		case R.id.brushLayout:
			if(isEraseMode == true){
				Log.e("Last was eraser", "...");
				currentPaint.setColor(tempColor);
			}
			isDrawMode = true;
			isEraseMode = false;
			
			listDialog = new Dialog(PaintActivity.this);
			listDialog.setTitle("Brushes");
			listDialog.setContentView(R.layout.list);
			list = (ListView) listDialog.findViewById(R.id.listView);
			String[] brushes = getResources().getStringArray(R.array.Brushes);
			list.setAdapter(new ArrayAdapter<String>(PaintActivity.this,
					R.layout.simplerow, R.id.rowTextView, brushes));
			list.setOnItemClickListener(new MyOnItemSelectedListenerForBrushes());
			listDialog.show();
			hideMenuLayout();
			break;

		case R.id.colorLayout:
			if(isEraseMode == true){
				currentPaint.setColor(tempColor);
				Log.e("Last was eraser", "...");
			}
			isDrawMode = true;
			isEraseMode = false;
			new ColorPickerDialog(this, this, currentPaint.getColor()).show();
			currentPaint = new Paint();
			currentPaint.setAntiAlias(true);
			currentPaint.setDither(true);
			currentPaint.setColor(0xFFFF0000);
			currentPaint.setStyle(Paint.Style.STROKE);
			currentPaint.setStrokeJoin(Paint.Join.ROUND);
			currentPaint.setStrokeCap(Paint.Cap.ROUND);
			currentPaint.setStrokeWidth(strokewidth);
			hideMenuLayout();
			setBrushAttributes();
			break;

		case R.id.saveLayout:
			if (isSaved == false) {
				showDialog(SAVE_IMAGE);
			} else {
				showDialog(SAVE_DIALOG);
			}
			hideMenuLayout();
			break;

		case R.id.brushSet:
			if(isEraseMode == true){
				lastColor = tempColor;
				Log.e("Last was eraser", "...");
			}
			isDrawMode = true;
			isEraseMode = false;
			brushDialog.dismiss();
			currentPaint = new Paint();
			currentPaint.setDither(true);
			currentPaint.setStyle(Paint.Style.STROKE);
			currentPaint.setStrokeJoin(Paint.Join.ROUND);
			currentPaint.setColor(lastColor);
			currentPaint.setStrokeCap(Paint.Cap.ROUND);
			currentPaint.setStrokeWidth(strokewidth);
			setBrushWidthAttributes();
			break;

		case R.id.brushCancel:
			if(isEraseMode == true){
				currentPaint.setColor(tempColor);
				Log.e("Last was eraser", "...");
			}
			isDrawMode = true;
			isEraseMode = false;
			strokewidth = 20;
			brushDialog.dismiss();
			break;

		case R.id.brushWidthLayout:
			isDrawMode = true;
			isEraseMode = false;
			hideMenuLayout();
			brushDialog = new Dialog(PaintActivity.this);
			brushDialog.setTitle("Set stroke width");
			brushDialog.setContentView(R.layout.brushwidth);
			brushSet = (Button) brushDialog.findViewById(R.id.brushSet);
			brushCancel = (Button) brushDialog.findViewById(R.id.brushCancel);
			brushDialog.show();
			brushWidth = (SeekBar) brushDialog.findViewById(R.id.brushControl);
			brushWidth.setMax(50);
			brushWidth.setKeyProgressIncrement(10);
			brushWidth.setProgress(strokewidth);
			brushWidth
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						public void onStopTrackingTouch(SeekBar seekBar) {
							strokewidth = seekBar.getProgress();
						}

						public void onStartTrackingTouch(SeekBar seekBar) {

						}

						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
						}

					});

			brushSet.setOnClickListener(PaintActivity.this);
			brushCancel.setOnClickListener(PaintActivity.this);
			break;

		case R.id.shareLayout:
			hideMenuLayout();
			shareImageFilePath = ShareImage.shareImage(
					drawingSurface.getBitmap(), extStorageDirectory, picname,
					this, ++ImageProcessingActivity.imageCount);
			break;

		case R.id.eraseLayout:
			hideMenuLayout();
			Log.e("Erase Clicked", "...");
			tempColor = currentPaint.getColor();
			isDrawMode = false;
			isEraseMode = true;
			Log.e("Status of erase mode", "" + isEraseMode);
			break;
		}
	}

	// Consists dialogs shown in this activity
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG:
			dialog = new ProgressDialog(PaintActivity.this);
			dialog.setMessage("Processing image. Please wait");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;

		case SAVE_DIALOG:
			AlertDialog.Builder builderForSave;
			builderForSave = new AlertDialog.Builder(PaintActivity.this);
			builderForSave.setCancelable(true);
			builderForSave.setIcon(R.drawable.alert);
			builderForSave.setTitle("Save");
			builderForSave.setMessage("No changes to be saved");
			builderForSave.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							removeDialog(SAVE_DIALOG);
						}
					});
			AlertDialog tempDlg1 = builderForSave.create();
			tempDlg1.show();
			return tempDlg1;

		case SAVE_IMAGE:
			AlertDialog.Builder builderofImageDlg;
			builderofImageDlg = new AlertDialog.Builder(PaintActivity.this);
			builderofImageDlg.setCancelable(true);
			builderofImageDlg.setIcon(R.drawable.alert);
			builderofImageDlg.setTitle("Save");
			builderofImageDlg
					.setMessage("Do you want to save the changes of the image?");
			builderofImageDlg.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							isSaved = true;
							Bitmap finalOverlayed = overlayingBitmaps();
							SaveImage.imageSave(extStorageDirectory, picname,
									PaintActivity.this,
									finalOverlayed,
									++ImageProcessingActivity.imageCount);
							
							removeDialog(SAVE_IMAGE);
						}
					});
			builderofImageDlg.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							removeDialog(SAVE_IMAGE);
							isSaved = false;
							AsyncTaskForNextActivity task = new AsyncTaskForNextActivity();
							task.execute();
						}
					});
			AlertDialog saveDlg = builderofImageDlg.create();
			saveDlg.show();
			return saveDlg;
		}
		return null;
	}
	
	// Overlaying original image + drawing path layer
	protected Bitmap overlayingBitmaps() {
		Bitmap bmpUnderneath = MemCacheManager.getBitmapFromMemCache("changedImage");
		Bitmap aboveOne = drawingSurface.getBitmap();
		Bitmap finalOverlayed = null;
		int ht = bmpUnderneath.getHeight();
		int wt = bmpUnderneath.getWidth();
		bmpUnderneath = Bitmap.createScaledBitmap(bmpUnderneath, wt, ht, true).copy(
				Bitmap.Config.ARGB_8888, true);
		aboveOne = Bitmap.createScaledBitmap(aboveOne, wt, ht, true).copy(
				Bitmap.Config.ARGB_8888, true);
		finalOverlayed = OverlayImages.overlayImages(bmpUnderneath, aboveOne);
		return finalOverlayed;
	}

	// Async task for next activity
	private class AsyncTaskForNextActivity extends
			AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected String doInBackground(String... params) {
			Intent i = new Intent(PaintActivity.this,
					ImageProcessingActivity.class);
			startActivity(i);
			finish();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog != null)
				removeDialog(PROGRESS_DIALOG);
		}

	}

	// Setting the paint attributes after selecting brush width
	private void setBrushWidthAttributes() {
		if (isEmboss) {
			lastColor = currentPaint.getColor();
			emboss = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6,
					3.5f);
			currentPaint.setMaskFilter(emboss);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		} else if (isSpray) {
			lastColor = currentPaint.getColor();
			blur = new BlurMaskFilter(30, BlurMaskFilter.Blur.NORMAL);
			currentPaint.setMaskFilter(blur);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		} else if (isInterlace) {
			lastColor = currentPaint.getColor();
			LinearGradient gradient = new LinearGradient(30, 0, 50, 0,
					new int[] { 0x00000000, lastColor, 0x00000000 }, null,
					Shader.TileMode.REPEAT);
			currentPaint.setShader(gradient);
			PathEffect cornerEffect = new CornerPathEffect(10);
			currentPaint.setPathEffect(cornerEffect);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		} else if (isRainbow) {
			lastColor = currentPaint.getColor();
			LinearGradient gradient = new LinearGradient(-100, -70, 100, 20,
					new int[] { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF,
							0xFF00FF00, 0xFFFFFF00, 0xFF000000, 0xFFFFFFFF,
							0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF,
							0xFF00FF00, 0xFFFFFF00, 0xFFFF0000, 0xFFFF0000,
							0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
							0xFFFFFF00, 0xFFFF0000, 0xFF000000, 0xFFFFFFFF },
					null, Shader.TileMode.MIRROR);
			currentPaint.setShader(gradient);
			PathEffect cornerEffect = new CornerPathEffect(10);
			currentPaint.setPathEffect(cornerEffect);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		} else if (isParallel) {
			lastColor = currentPaint.getColor();
			PathEffect effect = new PathDashPathEffect(makePathDash(), 12, 50,
					PathDashPathEffect.Style.MORPH);
			currentPaint.setPathEffect(effect);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		} else if (isHollow) {
			lastColor = currentPaint.getColor();
			blur = new BlurMaskFilter(10, BlurMaskFilter.Blur.OUTER);
			currentPaint.setMaskFilter(blur);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		}
	}

	// Creating path for a parallel brush
	private static Path makePathDash() {
		Path dashPath = new Path();
		dashPath.moveTo(-20, 4);
		dashPath.lineTo(20, 4);
		dashPath.lineTo(20, 3);
		dashPath.lineTo(-20, 3);
		dashPath.close();
		dashPath.moveTo(-20, -4);
		dashPath.lineTo(20, -4);
		dashPath.lineTo(20, -3);
		dashPath.lineTo(-20, -3);
		return dashPath;
	}

	// Listener class for brushes
	public class MyOnItemSelectedListenerForBrushes implements
			OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int pos,
				long id) {

			System.out.println("we are in item selected listener of filter");
			String brush = parent.getItemAtPosition(pos).toString().trim();
			Log.d("Brush selected is", ".." + brush);

			if (brush.equals("Emboss")) {
				isEmboss = true;
				isSpray = false;
				isInterlace = false;
				isRainbow = false;
				isParallel = false;
				isHollow = false;
			}

			else if (brush.equals("Spray")) {
				isEmboss = false;
				isSpray = true;
				isInterlace = false;
				isRainbow = false;
				isParallel = false;
				isHollow = false;
			}

			else if (brush.equals("Interlace")) {
				isEmboss = false;
				isSpray = false;
				isInterlace = true;
				isRainbow = false;
				isParallel = false;
				isHollow = false;
			}

			else if (brush.equals("Rainbow")) {
				isEmboss = false;
				isSpray = false;
				isInterlace = false;
				isRainbow = true;
				isParallel = false;
				isHollow = false;
			}

			else if (brush.equals("Parallel")) {
				isEmboss = false;
				isSpray = false;
				isInterlace = false;
				isRainbow = false;
				isParallel = true;
				isHollow = false;
			}

			else if (brush.equals("Hollow")) {
				isEmboss = false;
				isSpray = false;
				isInterlace = false;
				isRainbow = false;
				isParallel = false;
				isHollow = true;
			}
			setBrushAttributes();

			listDialog.dismiss();
		}
	}

	// Setting a new drawing path when a new brush type is selected
	private void setBrushAttributes() {
		if (isEmboss) {
			lastColor = currentPaint.getColor();
			setCurrentPaintForBrush();
			emboss = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6,
					3.5f);
			currentPaint.setMaskFilter(emboss);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		} else if (isSpray) {
			lastColor = currentPaint.getColor();
			setCurrentPaintForBrush();
			blur = new BlurMaskFilter(30, BlurMaskFilter.Blur.NORMAL);
			currentPaint.setMaskFilter(blur);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		} else if (isInterlace) {
			lastColor = currentPaint.getColor();
			setCurrentPaintForBrush();
			LinearGradient gradient = new LinearGradient(30, 0, 50, 0,
					new int[] { 0x00000000, lastColor, 0x00000000 }, null,
					Shader.TileMode.REPEAT);
			currentPaint.setShader(gradient);
			PathEffect cornerEffect = new CornerPathEffect(10);
			currentPaint.setPathEffect(cornerEffect);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		} else if (isRainbow) {
			lastColor = currentPaint.getColor();
			setCurrentPaintForBrush();
			LinearGradient gradient = new LinearGradient(-100, -70, 100, 20,
					new int[] { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF,
							0xFF00FF00, 0xFFFFFF00, 0xFF000000, 0xFFFFFFFF,
							0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF,
							0xFF00FF00, 0xFFFFFF00, 0xFFFF0000, 0xFFFF0000,
							0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
							0xFFFFFF00, 0xFFFF0000, 0xFF000000, 0xFFFFFFFF },
					null, Shader.TileMode.MIRROR);
			currentPaint.setShader(gradient);
			PathEffect cornerEffect = new CornerPathEffect(10);
			currentPaint.setPathEffect(cornerEffect);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		} else if (isParallel) {
			lastColor = currentPaint.getColor();
			setCurrentPaintForBrush();
			PathEffect effect = new PathDashPathEffect(makePathDash(), 12, 50,
					PathDashPathEffect.Style.MORPH);
			currentPaint.setPathEffect(effect);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		} else if (isHollow) {
			lastColor = currentPaint.getColor();
			setCurrentPaintForBrush();
			blur = new BlurMaskFilter(10, BlurMaskFilter.Blur.OUTER);
			currentPaint.setMaskFilter(blur);
			drawingSurface.previewPath.path = new Path();
			drawingSurface.previewPath.paint = getPreviewPaint();
		}
	}

	// On back pressed in paint module
	public void onBackPressed() {
		if (menuOut == true) {
			hideMenuLayout();
		} else if (isSaved == false) {
			showDialog(SAVE_IMAGE);
		} else {
			AsyncTaskForNextActivity task = new AsyncTaskForNextActivity();
			task.execute();
		}
	}

	// On destroy of the paint activity
	protected void onDestroy() {
		if (scrollView != null) {
			scrollView.removeAllViews();
			scrollView = null;
		}
		super.onDestroy();
	}

	// On restart method of the activity
	protected void onRestart() {
		Log.e("On restart of", "Image processing");
		if (PaintActivity.isShared == true) {
			File file = new File(shareImageFilePath);
			boolean deleted = file.delete();
			Log.e("File deleted", "Success or failure  " + deleted);
		}
		super.onRestart();
	}

}
