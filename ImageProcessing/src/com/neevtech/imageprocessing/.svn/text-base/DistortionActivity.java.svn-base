package com.neevtech.imageprocessing;

import java.io.File;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.neevtech.imageprocessing.util.FlipFilter;
import com.neevtech.imageprocessing.util.MemCacheManager;
import com.neevtech.imageprocessing.util.SaveImage;
import com.neevtech.imageprocessing.util.SetOffsetPixels;
import com.neevtech.imageprocessing.util.ShareImage;
import com.neevtech.imageprocessing.util.SphereFilter;
import com.neevtech.imageprocessing.util.SwirlFilter;

/**
 * @author Shruti
 * 
 */
public class DistortionActivity extends BaseActivity implements OnClickListener {

	private String extStorageDirectory;
	private RelativeLayout areaofchange, menuarea;
	private Bitmap selectedImage;
	private ImageView distortImage;
	private boolean isFlip, isSwirl, isBulge, isWarp, isRadioRtlOpen, prevWarp,
			prevBulge;
	private RelativeLayout flipradiobtns;
	private LinearLayout flipLayout;
	private LinearLayout warpLayout;
	private LinearLayout bulgeLayout;
	private LinearLayout swirlLayout;
	private LinearLayout saveLayout;
	private LinearLayout resetLayout, shareLayout;
	private Bitmap myImageBitmap;
	private SampleView newView;
	private BulgeView newBulgeView;
	private Bitmap extraone;
	private Bitmap warpView;
	private Bitmap bulgeView;
	private boolean isHorz;
	private boolean isVert;
	private static boolean isSaved;
	public static boolean isShared;
	private static final int SAVE_IMAGE = 1;
	private static final int NO_CHANGE = 2;
	private static final int SAVE_IMAGE_OF_WARP = 3;
	private static final int SAVE_IMAGE_OF_BULGE = 4;
	private final static int PROGRESS_DIALOG = 0;
	public ProgressDialog dialog;
	private String picname = "mypic";
	private String shareImageFilePath;
	private View temp;

	// On orientation change handling
	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.distortapppage);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("In onCreate", "ImageProc");
		inflateMenu = true;
		super.onCreate(savedInstanceState);
		initializeViews();
		loadScroller();
		initializeLayouts();
		settingVisibility();
		settingOnClickListeners();
	}

	// Initialisation of all views in the app and menu page of distort
	private void initializeViews() {
		extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();
		Log.e("In initializeviews", "Distort");
		LayoutInflater inflater = LayoutInflater.from(DistortionActivity.this);
		app = inflater.inflate(R.layout.distortapppage, null);

		// Menu button initialization
		areaofchange = (RelativeLayout) app.findViewById(R.id.areaofchange);
		LinearLayout topBar = (LinearLayout) areaofchange
				.findViewById(R.id.topBar);
		menuarea = (RelativeLayout) topBar.findViewById(R.id.menuarea);
		menuButton = (ImageView) menuarea.findViewById(R.id.distortmenu);

		selectedImage = MemCacheManager.getBitmapFromMemCache("changedImage");

		isFlip = false;
		isSwirl = false;
		isBulge = false;
		isWarp = false;
		isRadioRtlOpen = false;
		prevWarp = false;
		prevBulge = false;
		isSaved = true;
		distortImage = (ImageView) app.findViewById(R.id.distortimage);
		distortImage.setImageBitmap(selectedImage);

		myImageBitmap = selectedImage;
	}

	// Setting visibility of the radio buttons to invisible
	private void settingVisibility() {
		flipradiobtns.setVisibility(View.INVISIBLE);
	}

	// Initialising the the menu options of distort
	private void initializeLayouts() {
		flipradiobtns = (RelativeLayout) app.findViewById(R.id.flipRadioBtns);
		flipLayout = (LinearLayout) menu.findViewById(R.id.flipLayout);
		warpLayout = (LinearLayout) menu.findViewById(R.id.warpLayout);
		bulgeLayout = (LinearLayout) menu.findViewById(R.id.bulgeLayout);
		swirlLayout = (LinearLayout) menu.findViewById(R.id.swirlLayout);
		saveLayout = (LinearLayout) menu.findViewById(R.id.saveLayout);
		resetLayout = (LinearLayout) menu.findViewById(R.id.resetLayout);
		shareLayout = (LinearLayout) menu.findViewById(R.id.shareLayout);
		distortImage = (ImageView) app.findViewById(R.id.distortimage);
		distortImage.setImageBitmap(selectedImage);
	}

	// Setting on click listeners over the menu options of distort
	private void settingOnClickListeners() {
		flipLayout.setOnClickListener(this);
		warpLayout.setOnClickListener(this);
		bulgeLayout.setOnClickListener(this);
		swirlLayout.setOnClickListener(this);
		saveLayout.setOnClickListener(this);
		resetLayout.setOnClickListener(this);
		shareLayout.setOnClickListener(this);
	}

	// On click actions over click of menu options of distort
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.warpLayout:
			isFlip = false;
			isRadioRtlOpen = false;
			isWarp = true;
			isSwirl = false;
			isBulge = false;
			prevWarp = true;
			if (prevBulge) {
				areaofchange.removeViewAt(0);
				prevBulge = false;
			}

			newView = new SampleView(DistortionActivity.this, selectedImage,
					areaofchange);
			areaofchange.addView(newView, 0);
			menuarea.setVisibility(View.VISIBLE);
			menuButton.bringToFront();
			settingVisibility();
			hideMenuLayout();
			break;

		case R.id.flipLayout:
			isFlip = true;
			isSwirl = false;
			isBulge = false;
			isWarp = false;
			flipradiobtns.setVisibility(View.VISIBLE);
			isRadioRtlOpen = true;
			if (prevWarp == true || prevBulge == true) {
				areaofchange.removeViewAt(0);
				prevWarp = false;
				prevBulge = false;
			} else {
				menuButton.bringToFront();
			}
			extraone = Bitmap.createScaledBitmap(myImageBitmap,
					areaofchange.getWidth(), areaofchange.getHeight(), true)
					.copy(Bitmap.Config.ARGB_8888, true);
			hideMenuLayout();
			break;

		case R.id.swirlLayout:
			isRadioRtlOpen = false;
			settingVisibility();
			isSwirl = true;
			isWarp = false;
			isFlip = false;
			isBulge = false;
			if (prevWarp == true || prevBulge == true) {
				areaofchange.removeViewAt(0);
				prevWarp = false;
				prevBulge = false;
			} else {
				menuButton.bringToFront();
			}
			extraone = Bitmap.createScaledBitmap(myImageBitmap, 100, 100, true)
					.copy(Bitmap.Config.ARGB_8888, true);
			AsyncTaskForLoader swirltask = new AsyncTaskForLoader(extraone,
					false, false);
			swirltask.execute();
			hideMenuLayout();
			break;

		case R.id.bulgeLayout:
			isRadioRtlOpen = false;
			isSwirl = false;
			isWarp = false;
			isBulge = true;
			isFlip = false;
			prevBulge = true;
			if (prevWarp) {
				areaofchange.removeViewAt(0);
			}
			newBulgeView = new BulgeView(DistortionActivity.this,
					selectedImage, areaofchange);
			areaofchange.addView(newBulgeView, 0);
			menuarea.setVisibility(View.VISIBLE);
			menuButton.bringToFront();
			settingVisibility();
			hideMenuLayout();
			break;

		case R.id.saveLayout:
			hideMenuLayout();
			if (isRadioRtlOpen == true) {
				isRadioRtlOpen = false;
				flipradiobtns.setVisibility(View.INVISIBLE);
			}
			if (isSaved == false) {
				if (isFlip == false && isSwirl == false) {
					if (isWarp) {
						warpView = newView.getBitmap();
						showDialog(SAVE_IMAGE_OF_WARP);
					} else if (isBulge) {
						bulgeView = newBulgeView.getBitmap();
						showDialog(SAVE_IMAGE_OF_BULGE);
					}
				} else {
					showDialog(SAVE_IMAGE);
				}
			} else {
				showDialog(NO_CHANGE);
			}

			break;

		case R.id.resetLayout:
			Intent i = new Intent(this, DistortionActivity.class);
			startActivity(i);
			finish();
			break;

		case R.id.shareLayout:
			shareImageFilePath = ShareImage.shareImage(postChanges(),
					extStorageDirectory, picname, this,
					++ImageProcessingActivity.imageCount);
			break;
		}
	}

	// Getting currentBitmap
	private Bitmap postChanges() {
		Bitmap bmp = null;
		if (isBulge == true || isWarp == true) {
			temp = areaofchange.getChildAt(0);
			temp.setDrawingCacheEnabled(true);
			bmp = Bitmap.createBitmap(temp.getDrawingCache());
			temp.setDrawingCacheEnabled(false);
		}
		else if(isFlip == true || isSwirl == true){
			distortImage.setDrawingCacheEnabled(true);
			bmp = Bitmap.createBitmap(distortImage.getDrawingCache());
			distortImage.setDrawingCacheEnabled(false);
		}
		return bmp;
	}

	// Handling on Back key press
	public void onBackPressed() {
		if (isRadioRtlOpen == true) {
			isRadioRtlOpen = false;
			flipradiobtns.setVisibility(View.INVISIBLE);
		} else if (isSaved == false) {
			if (isFlip == false && isSwirl == false) {
				if (isWarp) {
					warpView = newView.getBitmap();
					showDialog(SAVE_IMAGE_OF_WARP);
				} else if (isBulge) {
					bulgeView = newBulgeView.getBitmap();
					showDialog(SAVE_IMAGE_OF_BULGE);
				}
			} else {
				showDialog(SAVE_IMAGE);
			}
		} else {
			AsyncTaskForNextActivity task = new AsyncTaskForNextActivity();
			task.execute();
		}

	}

	// Dialogs of distort activity
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG:
			dialog = new ProgressDialog(DistortionActivity.this);
			dialog.setMessage("Processing image. Please wait");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;

		case SAVE_IMAGE:
			AlertDialog.Builder builderofImageDlg;
			builderofImageDlg = new AlertDialog.Builder(DistortionActivity.this);
			builderofImageDlg.setCancelable(true);
			builderofImageDlg.setIcon(R.drawable.alert);
			builderofImageDlg.setTitle("Save");
			builderofImageDlg
					.setMessage("Do you want to save the changes of the image?");
			builderofImageDlg.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							DistortionActivity.isSaved = true;
							SaveImage.imageSave(extStorageDirectory, picname,
									DistortionActivity.this, distortImage,
									++ImageProcessingActivity.imageCount);
							removeDialog(SAVE_IMAGE);
						}
					});
			builderofImageDlg.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							removeDialog(SAVE_IMAGE);
							isSaved = false;
							removeDialog(SAVE_IMAGE);
							AsyncTaskForNextActivity task = new AsyncTaskForNextActivity();
							task.execute();
						}
					});
			AlertDialog saveDlg = builderofImageDlg.create();
			saveDlg.show();
			return saveDlg;

		case NO_CHANGE:
			AlertDialog.Builder builderofImageDlg1;
			builderofImageDlg1 = new AlertDialog.Builder(
					DistortionActivity.this);
			builderofImageDlg1.setCancelable(true);
			builderofImageDlg1.setIcon(R.drawable.alert);
			builderofImageDlg1.setTitle("Save");
			builderofImageDlg1.setMessage("No changes to be saved");
			builderofImageDlg1.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							DistortionActivity.isSaved = true;
							removeDialog(NO_CHANGE);
						}
					});
			AlertDialog nochange = builderofImageDlg1.create();
			nochange.show();
			return nochange;

		case SAVE_IMAGE_OF_WARP:
			AlertDialog.Builder builderofImageDlg2;
			builderofImageDlg2 = new AlertDialog.Builder(
					DistortionActivity.this);
			builderofImageDlg2.setCancelable(true);
			builderofImageDlg2.setIcon(R.drawable.alert);
			builderofImageDlg2.setTitle("Save");
			builderofImageDlg2
					.setMessage("Do you want to save the changes of the image?");
			builderofImageDlg2.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							DistortionActivity.isSaved = true;
							SaveImage.imageSave(extStorageDirectory, picname,
									DistortionActivity.this, warpView,
									++ImageProcessingActivity.imageCount);
							removeDialog(SAVE_IMAGE_OF_WARP);
						}
					});
			builderofImageDlg2.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							removeDialog(SAVE_IMAGE_OF_WARP);
							isSaved = false;
							AsyncTaskForNextActivity task = new AsyncTaskForNextActivity();
							task.execute();
						}
					});
			AlertDialog saveDlgforWarp = builderofImageDlg2.create();
			saveDlgforWarp.show();
			return saveDlgforWarp;

		case SAVE_IMAGE_OF_BULGE:
			AlertDialog.Builder builderofImageDlg3;
			builderofImageDlg3 = new AlertDialog.Builder(
					DistortionActivity.this);
			builderofImageDlg3.setCancelable(true);
			builderofImageDlg3.setIcon(R.drawable.alert);
			builderofImageDlg3.setTitle("Save");
			builderofImageDlg3
					.setMessage("Do you want to save the changes of the image?");
			builderofImageDlg3.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							DistortionActivity.isSaved = true;
							SaveImage.imageSave(extStorageDirectory, picname,
									DistortionActivity.this, bulgeView,
									++ImageProcessingActivity.imageCount);
							removeDialog(SAVE_IMAGE_OF_BULGE);
						}
					});
			builderofImageDlg3.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							removeDialog(SAVE_IMAGE_OF_BULGE);
							isSaved = false;
							AsyncTaskForNextActivity task = new AsyncTaskForNextActivity();
							task.execute();
						}
					});
			AlertDialog saveDlgforBulge = builderofImageDlg3.create();
			saveDlgforBulge.show();
			return saveDlgforBulge;

		}

		return null;
	}

	// Actions over flip radio buttons click
	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.horizontal:
			if (checked) {
				isHorz = true;
				isVert = false;
				extraone = Bitmap.createScaledBitmap(myImageBitmap, 100, 100,
						true).copy(Bitmap.Config.ARGB_8888, true);
				AsyncTaskForLoader task = new AsyncTaskForLoader(extraone,
						isHorz, isVert);
				task.execute();
			}
			break;
		case R.id.vertical:
			if (checked) {
				isHorz = false;
				isVert = true;
				extraone = Bitmap.createScaledBitmap(myImageBitmap, 100, 100,
						true).copy(Bitmap.Config.ARGB_8888, true);
				AsyncTaskForLoader task = new AsyncTaskForLoader(extraone,
						isHorz, isVert);
				task.execute();
			}

			break;
		}
	}

	// View class of time warp option
	private static class SampleView extends View {
		private static final int WIDTH = 30;
		private static final int HEIGHT = 30;
		private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);

		private Bitmap mBitmap;
		private final float[] mVerts = new float[COUNT * 2];
		private final float[] mOrig = new float[COUNT * 2];

		private final Matrix mMatrix = new Matrix();
		private final Matrix mInverse = new Matrix();

		private static void setXY(float[] array, int index, float x, float y) {
			array[index * 2 + 0] = x;
			array[index * 2 + 1] = y;
		}

		public SampleView(Context context, Bitmap bmp,
				RelativeLayout areaofchange) {
			super(context);
			setFocusable(true);
			setDrawingCacheEnabled(true);
			mBitmap = bmp;
			mBitmap = Bitmap.createScaledBitmap(mBitmap,
					areaofchange.getWidth(), areaofchange.getHeight(), true)
					.copy(Bitmap.Config.ARGB_8888, true);
			float w = mBitmap.getWidth();
			float h = mBitmap.getHeight();
			// construct our mesh
			int index = 0;
			for (int y = 0; y <= HEIGHT; y++) {
				float fy = h * y / HEIGHT;
				for (int x = 0; x <= WIDTH; x++) {
					float fx = w * x / WIDTH;
					setXY(mVerts, index, fx, fy);
					setXY(mOrig, index, fx, fy);
					index += 1;
				}
			}

			mMatrix.setTranslate(10, 10);
			mMatrix.invert(mInverse);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(0xFFCCCCCC);
			canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, mVerts, 0, null, 0,
					null);
		}

		public Bitmap getBitmap() {
			return getDrawingCache();
		}

		private void warp(float cx, float cy) {
			final float K = 30000;
			float[] src = mOrig;
			float[] dst = mVerts;
			for (int i = 0; i < COUNT * 2; i += 2) {
				float x = src[i + 0];
				float y = src[i + 1];
				float dx = cx - x;
				float dy = cy - y;
				float dd = dx * dx + dy * dy;
				float d = (float) Math.sqrt(dd);
				float pull = K / (dd + 0.000001f);

				pull /= (d + 0.000001f);
				if (pull >= 1) {
					dst[i + 0] = cx;
					dst[i + 1] = cy;
				} else {
					dst[i + 0] = x + dx * pull;
					dst[i + 1] = y + dy * pull;
				}
			}
		}

		private int mLastWarpX = -9999; // don't match a touch coordinate
		private int mLastWarpY;

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float[] pt = { event.getX(), event.getY() };
			mInverse.mapPoints(pt);
			isSaved = false;
			int x = (int) pt[0];
			int y = (int) pt[1];
			if (mLastWarpX != x || mLastWarpY != y) {
				mLastWarpX = x;
				mLastWarpY = y;
				warp(pt[0], pt[1]);
				invalidate();
			}
			return true;
		}

	}

	// View class of bulge option
	private static class BulgeView extends View {
		private static final int WIDTH = 30;
		private static final int HEIGHT = 30;
		private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);

		private Bitmap mBitmap;
		private final float[] mVerts = new float[COUNT * 2];
		private final float[] mOrig = new float[COUNT * 2];

		private final Matrix mMatrix = new Matrix();
		private final Matrix mInverse = new Matrix();

		private static void setXY(float[] array, int index, float x, float y) {
			array[index * 2 + 0] = x;
			array[index * 2 + 1] = y;
		}

		public BulgeView(Context context, Bitmap bmp,
				RelativeLayout areaofchange) {
			super(context);
			setFocusable(true);
			setDrawingCacheEnabled(true);
			mBitmap = bmp;
			mBitmap = Bitmap.createScaledBitmap(mBitmap,
					areaofchange.getWidth(), areaofchange.getHeight(), true)
					.copy(Bitmap.Config.ARGB_8888, true);
			float w = mBitmap.getWidth();
			float h = mBitmap.getHeight();
			// construct our mesh
			int index = 0;
			for (int y = 0; y <= HEIGHT; y++) {
				float fy = h * y / HEIGHT;
				for (int x = 0; x <= WIDTH; x++) {
					float fx = w * x / WIDTH;
					setXY(mVerts, index, fx, fy);
					setXY(mOrig, index, fx, fy);
					index += 1;
				}
			}

			mMatrix.setTranslate(10, 10);
			mMatrix.invert(mInverse);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(0xFFCCCCCC);
			// canvas.concat(mMatrix);
			canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, mVerts, 0, null, 0,
					null);
		}

		public Bitmap getBitmap() {
			return getDrawingCache();
		}

		private void warp(float cx, float cy) {
			final float K = -1000;
			float[] src = mOrig;
			float[] dst = mVerts;
			for (int i = 0; i < COUNT * 2; i += 2) {
				float x = src[i + 0];
				float y = src[i + 1];
				float dx = cx - x;
				float dy = cy - y;
				float dd = dx * dx + dy * dy;
				float d = (float) Math.sqrt(dd);
				float pull = K / (dd + 0.000001f);

				pull /= (d + 0.000001f);
				if (pull >= 1) {
					dst[i + 0] = cx;
					dst[i + 1] = cy;
				} else {
					dst[i + 0] = x + dx * pull;
					dst[i + 1] = y + dy * pull;
				}
			}
		}

		private int mLastWarpX = -9999; // don't match a touch coordinate
		private int mLastWarpY;

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float[] pt = { event.getX(), event.getY() };
			mInverse.mapPoints(pt);
			isSaved = false;
			int x = (int) pt[0];
			int y = (int) pt[1];
			if (mLastWarpX != x || mLastWarpY != y) {
				mLastWarpX = x;
				mLastWarpY = y;
				warp(pt[0], pt[1]);
				invalidate();
			}
			return true;
		}

	}

	// Async task class for showing loader while processing image
	public class AsyncTaskForLoader extends AsyncTask<String, Void, Bitmap> {
		Bitmap changedImage;
		Bitmap finalResult = null;
		boolean isHorz = false;
		boolean isVert = false;

		public AsyncTaskForLoader(Bitmap chBitmap, boolean isHorz,
				boolean isVert) {
			this.changedImage = chBitmap;
			this.isHorz = isHorz;
			this.isVert = isVert;
		}

		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			if (isFlip) {
				Point[][] pts1 = FlipFilter.flipFilter(changedImage, isHorz,
						isVert);
				finalResult = SetOffsetPixels.offsetFilterAbs(changedImage,
						pts1);
				// finalResult = FlipOrMirrorEffect.flip(changedImage, type);
			} else if (isSwirl) {
				Point[][] pts2 = SwirlFilter.swirlFilter(changedImage);
				finalResult = SetOffsetPixels.offsetFilterAbs(changedImage,
						pts2);
			} else if (isBulge) {
				Point[][] pts3 = SphereFilter.sphereFilter(changedImage);
				finalResult = SetOffsetPixels.offsetFilterAbs(changedImage,
						pts3);
			}
			isSaved = false;
			return finalResult;
		}

		protected void onPostExecute(Bitmap result) {
			if (dialog != null)
				removeDialog(PROGRESS_DIALOG);
			distortImage.setImageBitmap(result);
			menuButton.bringToFront();
		}

	}

	// Async task fired on click of next activity
	public class AsyncTaskForNextActivity extends AsyncTask<String, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected Void doInBackground(String... params) {
			Intent i = new Intent(DistortionActivity.this,
					ImageProcessingActivity.class);
			startActivity(i);
			finish();
			return null;
		}

		protected void onPostExecute(Bitmap result) {
			if (dialog != null)
				removeDialog(PROGRESS_DIALOG);
		}

	}

	// On restart method of the activity
	protected void onRestart() {
		Log.e("On restart of", "Image processing");
		if (DistortionActivity.isShared == true) {
			File file = new File(shareImageFilePath);
			boolean deleted = file.delete();
			Log.e("File deleted", "Success or failure  " + deleted);
		}
		super.onRestart();
	}

}
