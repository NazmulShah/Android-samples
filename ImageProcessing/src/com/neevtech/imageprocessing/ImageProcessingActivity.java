package com.neevtech.imageprocessing;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.neevtech.imageprocessing.constants.Neevimagick;
import com.neevtech.imageprocessing.effects.EmbossEffect;
import com.neevtech.imageprocessing.effects.EngraveEffect;
import com.neevtech.imageprocessing.effects.FlipOrMirrorEffect;
import com.neevtech.imageprocessing.effects.InvertEffect;
import com.neevtech.imageprocessing.effects.MeanRemovalEffect;
import com.neevtech.imageprocessing.effects.SnowEffect;
import com.neevtech.imageprocessing.effects.filters.BlackFilter;
import com.neevtech.imageprocessing.effects.filters.DullEffect;
import com.neevtech.imageprocessing.ui.OverlayImages;
import com.neevtech.imageprocessing.util.MemCacheManager;
import com.neevtech.imageprocessing.util.SaveImage;
import com.neevtech.imageprocessing.util.ShareImage;

/**
 * @author Shruti
 * 
 */
public class ImageProcessingActivity extends BaseActivity implements
		OnClickListener, OnSeekBarChangeListener {

	private Bitmap selectedImage;
	private ScrollView adjustColors, adjustColorLevels;
	private HorizontalScrollView effectsLayout;
	private RelativeLayout levelsLayout1, levelsLayout2;
	private RelativeLayout imagesLayout;
	public static ImageView image;
	private ImageView apply;
	private Dialog frameDialog;
	protected RefreshHandler handler;
	private LinearLayout colorLayout, colorLevelsLayout, effectLayout,
			frameLayout, paintLayout, saveLayout, shareLayout, distortLayout,
			resetLayout;
	private LinearLayout adjustmentLayout1, adjustmentLayout2, firstRow;
	private LinearLayout hueAdjustmentLayout, saturationAdjustmentLayout,
			brightnessAdjustmentLayout;
	private LinearLayout redAdjustmentLayout, greenAdjustmentLayout,
			blueAdjustmentLayout;
	private SeekBar seekbarHue, seekbarBrightness, seekbarSaturation;
	private SeekBar seekbarRed, seekbarGreen, seekbarBlue;
	private ImageView invertedImage, dullImage, engraveImage, embossImage,
			snowImage, meanremovalImage, flipImage, blackImage;
	private Bitmap nextLevel;
	// private Bitmap canvasBitmap;
	private int currSeekProgressOfHue, currSeekProgressOfSat,
			currSeekProgressOfBrit;
	private int currSeekProgressOfRed, currSeekProgressOfGreen,
			currSeekProgressOfBlue;
	private ProgressDialog dialog;
	private Bitmap snowBitmap;
	public static int imageCount = 0;
	private String picname = "mypic";
	public String extStorageDirectory;
	private LinearLayout frame1Layout;
	private LinearLayout frame2Layout;
	private LinearLayout frame3Layout;
	private LinearLayout frame4Layout;
	private LinearLayout frameNoneLayout;
	public static boolean isSaved, isShared;
	private static final int PROGRESS_DIALOG = 0;
	private static final int SAVE_DIALOG = 1;
	private static final int SAVE_IMAGE = 2;
	private static final int SHARE_IMAGE = 3;
	private String shareImageFilePath;

	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.imageprocessingapppage);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("In onCreate", "ImageProc");
		inflateMenu = true;
		super.onCreate(savedInstanceState);
		initializeViews();
		setScrollViewVisibility();
		loadScroller();
		initializeLayouts();
		settingOnClickListeners();
	}

	private void initializeViews() {
		extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();
		Log.e("In initializeviews", "ImageProc");
		LayoutInflater inflater = LayoutInflater
				.from(ImageProcessingActivity.this);
		app = inflater.inflate(R.layout.imageprocessingapppage, null);
		// menu = inflater.inflate(R.layout.menulist, null);

		// Menu button initialization
		ViewGroup topBar = (ViewGroup) app.findViewById(R.id.topBar);
		menuButton = (ImageView) topBar.findViewById(R.id.menu);
		apply = (ImageView) topBar.findViewById(R.id.apply);

		selectedImage = MemCacheManager.getBitmapFromMemCache("myImage");
		imagesLayout = (RelativeLayout) app.findViewById(R.id.imagesLayout);
		// imagesLayout.setBackgroundResource(R.drawable.border1);
		image = (ImageView) imagesLayout.findViewById(R.id.originalImage);
		image.setImageBitmap(selectedImage);

		// The three scroll views
		adjustColors = (ScrollView) app.findViewById(R.id.adjustColors);
		adjustColorLevels = (ScrollView) app
				.findViewById(R.id.adjustColorLevels);
		effectsLayout = (HorizontalScrollView) app
				.findViewById(R.id.effectsLayout);

		// The inner relative layouts of the above scroll views
		levelsLayout1 = (RelativeLayout) adjustColors
				.findViewById(R.id.levelsLayout1);
		levelsLayout2 = (RelativeLayout) adjustColorLevels
				.findViewById(R.id.levelsLayout2);

		// Contents of adjust colors
		adjustmentLayout1 = (LinearLayout) levelsLayout1
				.findViewById(R.id.adjustmentLayout1);

		hueAdjustmentLayout = (LinearLayout) adjustmentLayout1
				.findViewById(R.id.hueadjustmentLayout);
		saturationAdjustmentLayout = (LinearLayout) adjustmentLayout1
				.findViewById(R.id.saturationadjustmentLayout);
		brightnessAdjustmentLayout = (LinearLayout) adjustmentLayout1
				.findViewById(R.id.brightnessadjustmentLayout);
		/*
		 * contrastAdjustmentLayout = (LinearLayout) adjustmentLayout1
		 * .findViewById(R.id.contrastadjustmentLayout);
		 */

		seekbarHue = (SeekBar) hueAdjustmentLayout
				.findViewById(R.id.seekbarOfHue);
		seekbarSaturation = (SeekBar) saturationAdjustmentLayout
				.findViewById(R.id.seekbarOfSaturation);
		seekbarBrightness = (SeekBar) brightnessAdjustmentLayout
				.findViewById(R.id.seekbarOfBrightness);
		/*
		 * seekbarContrast = (SeekBar) contrastAdjustmentLayout
		 * .findViewById(R.id.seekbarOfContrast);
		 */

		// Contents of adjust color levels
		adjustmentLayout2 = (LinearLayout) levelsLayout2
				.findViewById(R.id.adjustmentLayout2);

		redAdjustmentLayout = (LinearLayout) adjustmentLayout2
				.findViewById(R.id.redadjustmentLayout);
		greenAdjustmentLayout = (LinearLayout) adjustmentLayout2
				.findViewById(R.id.greenadjustmentLayout);
		blueAdjustmentLayout = (LinearLayout) adjustmentLayout2
				.findViewById(R.id.blueadjustmentLayout);

		seekbarRed = (SeekBar) redAdjustmentLayout
				.findViewById(R.id.seekbarOfRed);
		seekbarGreen = (SeekBar) greenAdjustmentLayout
				.findViewById(R.id.seekbarOfGreen);
		seekbarBlue = (SeekBar) blueAdjustmentLayout
				.findViewById(R.id.seekbarOfBlue);

		// Contents of effects
		firstRow = (LinearLayout) effectsLayout.findViewById(R.id.firstRow);

		LinearLayout firstVertRow = (LinearLayout) firstRow
				.findViewById(R.id.firstVertRow);
		invertedImage = (ImageView) firstVertRow.findViewById(R.id.invertImage);

		LinearLayout secondVertRow = (LinearLayout) firstRow
				.findViewById(R.id.secondVertRow);
		dullImage = (ImageView) secondVertRow.findViewById(R.id.dullImage);

		LinearLayout thirdVertRow = (LinearLayout) firstRow
				.findViewById(R.id.thirdVertRow);
		engraveImage = (ImageView) thirdVertRow.findViewById(R.id.engraveImage);

		LinearLayout fourthVertRow = (LinearLayout) firstRow
				.findViewById(R.id.fourthVertRow);
		embossImage = (ImageView) fourthVertRow.findViewById(R.id.embossImage);

		LinearLayout fifthVertRow = (LinearLayout) firstRow
				.findViewById(R.id.fifthVertRow);
		meanremovalImage = (ImageView) fifthVertRow
				.findViewById(R.id.meanremovalImage);

		LinearLayout sixthVertRow = (LinearLayout) firstRow
				.findViewById(R.id.sixthVertRow);
		flipImage = (ImageView) sixthVertRow.findViewById(R.id.flipImage);

		LinearLayout seventhVertRow = (LinearLayout) firstRow
				.findViewById(R.id.seventhVertRow);
		blackImage = (ImageView) seventhVertRow.findViewById(R.id.blackImage);

		LinearLayout eigthtVertRow = (LinearLayout) firstRow
				.findViewById(R.id.eighthVertRow);
		snowImage = (ImageView) eigthtVertRow.findViewById(R.id.snowImage);

		handler = new RefreshHandler();
		resetSeeks();
		nextLevel = selectedImage;
		isSaved = true;
		isShared = false;
	}

	// Setting invisible for all scrollviews at first show up
	private void setScrollViewVisibility() {
		adjustColors.setVisibility(View.INVISIBLE);
		adjustColorLevels.setVisibility(View.INVISIBLE);
		effectsLayout.setVisibility(View.INVISIBLE);
	}

	// Resetting the seeks
	private void resetSeeks() {
		// Adjust seekbars max and min values
		seekbarHue.setMax(360);
		seekbarHue.setProgress(180);
		seekbarHue.setKeyProgressIncrement(1);

		seekbarSaturation.setMax(200);
		seekbarSaturation.setProgress(100);
		seekbarSaturation.setKeyProgressIncrement(1);

		seekbarBrightness.setMax(200);
		seekbarBrightness.setProgress(100);
		seekbarBrightness.setKeyProgressIncrement(1);

		// Adjust seekbars of color levels max and min
		seekbarRed.setMax(200);
		seekbarRed.setProgress(100);
		seekbarRed.setKeyProgressIncrement(1);

		seekbarGreen.setMax(200);
		seekbarGreen.setProgress(100);
		seekbarGreen.setKeyProgressIncrement(1);

		seekbarBlue.setMax(200);
		seekbarBlue.setProgress(100);
		seekbarBlue.setKeyProgressIncrement(1);
	}

	// Initializing all menu options
	private void initializeLayouts() {
		paintLayout = (LinearLayout) menu.findViewById(R.id.paintLayout);
		saveLayout = (LinearLayout) menu.findViewById(R.id.saveLayout);
		shareLayout = (LinearLayout) menu.findViewById(R.id.shareLayout);
		frameLayout = (LinearLayout) menu.findViewById(R.id.frameLayout);
		effectLayout = (LinearLayout) menu.findViewById(R.id.effectLayout);
		distortLayout = (LinearLayout) menu.findViewById(R.id.distortLayout);
		colorLevelsLayout = (LinearLayout) menu
				.findViewById(R.id.colorLevelsLayout);
		resetLayout = (LinearLayout) menu.findViewById(R.id.resetLayout);
		colorLayout = (LinearLayout) menu.findViewById(R.id.colorLayout);
	}

	// Setting on click listeners for menu options
	private void settingOnClickListeners() {
		paintLayout.setOnClickListener(this);
		saveLayout.setOnClickListener(this);
		shareLayout.setOnClickListener(this);
		effectLayout.setOnClickListener(this);
		distortLayout.setOnClickListener(this);
		colorLevelsLayout.setOnClickListener(this);
		resetLayout.setOnClickListener(this);
		frameLayout.setOnClickListener(this);
		colorLayout.setOnClickListener(this);
		apply.setOnClickListener(this);
	}

	// on click actions of menu options
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.paintLayout:
			setScrollViewVisibility();
			hideMenuLayout();
			AsyncTaskForNextActivity paintTask = new AsyncTaskForNextActivity(
					"PaintActivity");
			paintTask.execute();
			break;

		case R.id.saveLayout:
			setScrollViewVisibility();
			hideMenuLayout();
			if (isSaved == false) {
				showDialog(SAVE_IMAGE);
				// Image proessing has occured
				/*
				 * CustomDialog.showDialog(ImageProcessingActivity.this,
				 * "Do you want to save the image?", true, "SaveImage", "Yes",
				 * "No"); Log.e("Status of menu in onclick of save",
				 * "boolean..." + menuOut);
				 */
			} else {
				showDialog(SAVE_DIALOG);
			}
			break;

		case R.id.shareLayout:
			setScrollViewVisibility();
			hideMenuLayout();
			/*
			 * if (isSaved == false) { showDialog(SHARE_IMAGE); } else {
			 * AsyncTaskForNextActivity taskForNext = new
			 * AsyncTaskForNextActivity( "ShareImageActivity");
			 * taskForNext.execute(); }
			 */
			firingShareActivity();
			break;

		case R.id.frameLayout:
			setScrollViewVisibility();
			hideMenuLayout();
			frameDialog = new Dialog(ImageProcessingActivity.this);
			frameDialog.setTitle("Frames");
			frameDialog.setContentView(R.layout.framedialog);

			frameNoneLayout = (LinearLayout) frameDialog
					.findViewById(R.id.frameNoneLayout);
			frameNoneLayout.setOnClickListener(this);

			frame1Layout = (LinearLayout) frameDialog
					.findViewById(R.id.frame1Layout);
			frame1Layout.setOnClickListener(this);

			frame2Layout = (LinearLayout) frameDialog
					.findViewById(R.id.frame2Layout);
			frame2Layout.setOnClickListener(this);

			frame3Layout = (LinearLayout) frameDialog
					.findViewById(R.id.frame3Layout);
			frame3Layout.setOnClickListener(this);

			frame4Layout = (LinearLayout) frameDialog
					.findViewById(R.id.frame4Layout);
			frame4Layout.setOnClickListener(this);

			frameDialog.show();
			break;

		// On click over specific frame

		case R.id.frameNoneLayout:
			frameDialog.dismiss();
			imagesLayout.setBackgroundDrawable(null);
			break;

		case R.id.frame1Layout:
			frameDialog.dismiss();
			imagesLayout.setBackgroundResource(R.drawable.border1);
			break;

		case R.id.frame2Layout:
			frameDialog.dismiss();
			imagesLayout.setBackgroundResource(R.drawable.border3);
			break;

		case R.id.frame3Layout:
			frameDialog.dismiss();
			imagesLayout.setBackgroundResource(R.drawable.border4);
			break;

		case R.id.frame4Layout:
			frameDialog.dismiss();
			imagesLayout.setBackgroundResource(R.drawable.border5);
			break;

		case R.id.colorLayout:
			setScrollViewVisibility();
			hideMenuLayout();
			adjustColors.setVisibility(View.VISIBLE);
			obtainSeeksProgressValue();
			setSeeksProgressValue();

			trackSeekbarChangesForColor();

			break;

		case R.id.colorLevelsLayout:
			setScrollViewVisibility();
			hideMenuLayout();
			adjustColorLevels.setVisibility(View.VISIBLE);

			obtainSeeksProgressValue();
			setSeeksProgressValue();
			trackSeekbarChangesForColorLevels();

			break;

		case R.id.resetLayout:
			setScrollViewVisibility();
			hideMenuLayout();
			image.setImageBitmap(selectedImage);
			isSaved = true;
			resetSeeks();
			break;

		case R.id.effectLayout:
			setScrollViewVisibility();
			hideMenuLayout();
			effectsLayout.setVisibility(View.VISIBLE);
			EffectsImagePreparation effectsImageTask = new EffectsImagePreparation(
					nextLevel);
			effectsImageTask.execute();

			break;

		case R.id.distortLayout:
			setScrollViewVisibility();
			hideMenuLayout();
			AsyncTaskForNextActivity distortTask = new AsyncTaskForNextActivity(
					"DistortionActivity");
			distortTask.execute();
			break;

		case R.id.apply:
			nextLevel = preChanges();
			image.setImageBitmap(nextLevel);
			break;
		}
	}

	private void trackSeekbarChangesForColorLevels() {
		Log.e("Setting on seekbar listener", "");
		seekbarRed.setOnSeekBarChangeListener(this);
		seekbarGreen.setOnSeekBarChangeListener(this);
		seekbarBlue.setOnSeekBarChangeListener(this);
	}

	private void trackSeekbarChangesForColor() {
		Log.e("Setting on seekbar listener", "fytgfhcgxdc");
		seekbarHue.setOnSeekBarChangeListener(this);
		seekbarBrightness.setOnSeekBarChangeListener(this);
		seekbarSaturation.setOnSeekBarChangeListener(this);
	}

	private Bitmap preChanges() {
		Bitmap currentBitmap = null;
		image.setDrawingCacheEnabled(true);
		currentBitmap = Bitmap.createBitmap(image.getDrawingCache());
		image.setDrawingCacheEnabled(false);
		return currentBitmap;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		Log.e("Seekbar progress",
				"printing in on start touch" + seekBar.getProgress());
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

		if ((seekBar.getId() == seekbarHue.getId())
				|| (seekBar.getId() == seekbarBrightness.getId())
				|| (seekBar.getId() == seekbarSaturation.getId())) {
			Log.e("All is right", "");
			SetColorAsyncTask setColorTask = new SetColorAsyncTask(nextLevel);
			setColorTask.execute();
		}

		else if ((seekBar.getId() == seekbarBlue.getId())
				|| (seekBar.getId() == seekbarRed.getId())
				|| (seekBar.getId() == seekbarGreen.getId())) {
			Log.e("All is right", "In else if");
			SetColorLevelsAsyncTask setColorLevelTask = new SetColorLevelsAsyncTask(
					nextLevel);
			setColorLevelTask.execute();
		}
	}

	private void obtainSeeksProgressValue() {
		currSeekProgressOfHue = seekbarHue.getProgress();
		currSeekProgressOfSat = seekbarSaturation.getProgress();
		currSeekProgressOfBrit = seekbarBrightness.getProgress();
		currSeekProgressOfRed = seekbarRed.getProgress();
		currSeekProgressOfGreen = seekbarGreen.getProgress();
		currSeekProgressOfBlue = seekbarBlue.getProgress();
	}

	private void setSeeksProgressValue() {
		seekbarHue.setProgress(currSeekProgressOfHue);
		seekbarSaturation.setProgress(currSeekProgressOfSat);
		seekbarBrightness.setProgress(currSeekProgressOfBrit);
		seekbarRed.setProgress(currSeekProgressOfRed);
		seekbarGreen.setProgress(currSeekProgressOfGreen);
		seekbarBlue.setProgress(currSeekProgressOfBlue);
	}

	// On click of share image
	protected void firingShareActivity() {
		shareImageFilePath = ShareImage.shareImage(preChanges(),
				extStorageDirectory, picname, this,
				++ImageProcessingActivity.imageCount);
		/*
		 * Intent shareIntent = new Intent(ImageProcessingActivity.this,
		 * ShareImageActivity.class);
		 * 
		 * Getting Byte[] from bitmap
		 * 
		 * Drawable tempDraw = image.getDrawable(); Bitmap tempBmp =
		 * ((BitmapDrawable) tempDraw).getBitmap(); ByteArrayOutputStream baos =
		 * new ByteArrayOutputStream();
		 * tempBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos); byte[] data
		 * = baos.toByteArray();
		 * 
		 * Adding byte[] data to intent
		 * 
		 * shareIntent.putExtra("imgByteData", data);
		 * startActivity(shareIntent); finish();
		 */

		/*
		 * Bitmap icon = preChanges(); String filePath =
		 * SaveImage.savingOfBitMap(extStorageDirectory, picname, preChanges(),
		 * this, ++ImageProcessingActivity.imageCount);
		 * Log.e("Temp saved file path", "path.." + filePath);
		 * 
		 * Intent share = new Intent(Intent.ACTION_SEND);
		 * share.setType("image/jpeg"); ByteArrayOutputStream bytes = new
		 * ByteArrayOutputStream(); icon.compress(Bitmap.CompressFormat.JPEG,
		 * 100, bytes); File f = new File(filePath); try { f.createNewFile();
		 * FileOutputStream fo = new FileOutputStream(f);
		 * fo.write(bytes.toByteArray()); } catch (IOException e) {
		 * e.printStackTrace(); } share.putExtra(Intent.EXTRA_STREAM,
		 * Uri.parse("file:///" + filePath));
		 * startActivity(Intent.createChooser(share, "Share image via"));
		 */
	}

	// Overlaying original image + drawing path layer
	protected Bitmap overlayingBitmaps() {
		Bitmap bmpUnderneath = selectedImage;
		Bitmap aboveOne = null;
		Bitmap finalOverlayed = null;
		int ht = bmpUnderneath.getHeight();
		int wt = bmpUnderneath.getWidth();
		bmpUnderneath = Bitmap.createScaledBitmap(bmpUnderneath, wt, ht, true)
				.copy(Bitmap.Config.ARGB_8888, true);
		aboveOne = Bitmap.createScaledBitmap(aboveOne, wt, ht, true).copy(
				Bitmap.Config.ARGB_8888, true);
		finalOverlayed = OverlayImages.overlayImages(bmpUnderneath, aboveOne);
		return finalOverlayed;
	}

	// For all kinds of dialogs
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG:
			dialog = new ProgressDialog(ImageProcessingActivity.this);
			dialog.setMessage("Processing image. Please wait");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		case SAVE_DIALOG:
			AlertDialog.Builder builder;
			builder = new AlertDialog.Builder(ImageProcessingActivity.this);
			builder.setCancelable(false);
			builder.setIcon(R.drawable.alert);
			builder.setTitle("Save");
			builder.setMessage("No changes to be saved");
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							removeDialog(SAVE_DIALOG);
						}
					});
			AlertDialog tempDlg = builder.create();
			tempDlg.show();
			return tempDlg;
		case SAVE_IMAGE:
			AlertDialog.Builder builderofImageDlg;
			builderofImageDlg = new AlertDialog.Builder(
					ImageProcessingActivity.this);
			builderofImageDlg.setCancelable(false);
			builderofImageDlg.setIcon(R.drawable.alert);
			builderofImageDlg.setTitle("Save");
			builderofImageDlg
					.setMessage("Do you want to save the changes of the image?");
			builderofImageDlg.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							Bitmap finalBitmap = overlayingBorderWithImage();
							if (finalBitmap == null) {
								finalBitmap = preChanges();
							}
							SaveImage.imageSave(extStorageDirectory, picname,
									ImageProcessingActivity.this, finalBitmap,
									++ImageProcessingActivity.imageCount);
							isSaved = true;
							removeDialog(SAVE_IMAGE);
						}
					});
			builderofImageDlg.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							removeDialog(SAVE_IMAGE);
							isSaved = false;
							AsyncTaskForNextActivity task = new AsyncTaskForNextActivity(
									"PhotoChooserActivity");
							task.execute();
						}
					});
			AlertDialog saveDlg = builderofImageDlg.create();
			saveDlg.show();
			return saveDlg;
		case SHARE_IMAGE:
			AlertDialog.Builder builderofShareDlg;
			builderofShareDlg = new AlertDialog.Builder(
					ImageProcessingActivity.this);
			builderofShareDlg.setCancelable(false);
			builderofShareDlg.setIcon(R.drawable.alert);
			builderofShareDlg.setTitle("Save");
			builderofShareDlg
					.setMessage("Do you want to save the changes of the image?");
			builderofShareDlg.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							removeDialog(SHARE_IMAGE);
							// Bitmap fiBitmap = overlayingBorderWithImage();
							SaveImage.imageSave(extStorageDirectory, picname,
									ImageProcessingActivity.this, preChanges(),
									++ImageProcessingActivity.imageCount);
							AsyncTaskForNextActivity task = new AsyncTaskForNextActivity(
									"ShareImageActivity");
							task.execute();
						}
					});
			builderofShareDlg.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							removeDialog(SHARE_IMAGE);
							AsyncTaskForNextActivity task = new AsyncTaskForNextActivity(
									"ShareImageActivity");
							task.execute();
						}
					});
			AlertDialog shareDlg = builderofShareDlg.create();
			shareDlg.show();
			return shareDlg;
		}
		return null;

	}

	// Function which overlays the image with its border
	protected Bitmap overlayingBorderWithImage() {
		Bitmap selImage = preChanges();
		Drawable temp = imagesLayout.getBackground();
		Bitmap finalOverlayed = null;
		if (temp == null) {
			Log.e("No border", "...");
		} else {
			Bitmap borImage = ((BitmapDrawable) temp).getBitmap();
			int wd = selImage.getWidth();
			int ht = selImage.getHeight();
			selImage = Bitmap.createScaledBitmap(selImage, (wd - 62),
					(ht - 28), true).copy(Bitmap.Config.ARGB_8888, true);
			borImage = Bitmap.createScaledBitmap(borImage, wd, ht, true).copy(
					Bitmap.Config.ARGB_8888, true);
			finalOverlayed = OverlayImages.overlayBorderAndImage(borImage, selImage);
		}
		return finalOverlayed;
	}

	// On back pressed
	public void onBackPressed() {
		Log.e("On back pressed", "In image processing activity");
		if (menuOut == true) {
			hideMenuLayout();
		} else if (isSaved == false) {
			showDialog(SAVE_IMAGE);
		} else {
			AsyncTaskForNextActivity task = new AsyncTaskForNextActivity(
					"PhotoChooserActivity");
			task.execute();
		}

	}

	// Async task for adjusting hue saturation and brightness
	class SetColorAsyncTask extends AsyncTask<Void, Void, GroupBitmap> {
		Bitmap temp1;

		public SetColorAsyncTask(Bitmap bmp) {
			this.temp1 = bmp;
		}

		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected GroupBitmap doInBackground(Void... params) {
			GroupBitmap groupBMResult = updateHSV(temp1);
			return groupBMResult;
		}

		protected void onPostExecute(GroupBitmap result) {
			if (dialog != null)
				removeDialog(PROGRESS_DIALOG);
			image.setImageBitmap(result.bitmapDest);
			isSaved = false;
		}

	}

	// Async task for adjusting R, G, B values of an image
	class SetColorLevelsAsyncTask extends
			AsyncTask<Void, Void, GroupBitmapForColorLevel> {
		Bitmap temp1;

		public SetColorLevelsAsyncTask(Bitmap bmp) {
			this.temp1 = bmp;
		}

		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected GroupBitmapForColorLevel doInBackground(Void... params) {
			GroupBitmapForColorLevel groupBMResult = updateRGB(temp1);
			return groupBMResult;
		}

		protected void onPostExecute(GroupBitmapForColorLevel result) {
			if (dialog != null)
				removeDialog(PROGRESS_DIALOG);
			image.setImageBitmap(result.bitmapFinal);
			isSaved = false;
		}

	}

	// Async task for adjusting R, G, B values of an image
	class EffectsImagePreparation extends AsyncTask<Void, Void, Bitmap[]> {
		Bitmap temp1;
		Bitmap[] effectsBitmaps = new Bitmap[7];

		public EffectsImagePreparation(Bitmap bmp) {
			this.temp1 = bmp;
		}

		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected Bitmap[] doInBackground(Void... params) {

			effectsBitmaps[0] = InvertEffect.invert(temp1);
			effectsBitmaps[1] = DullEffect.doColorFilter(temp1);

			EngraveEffect engraveEffect = new EngraveEffect();
			effectsBitmaps[2] = engraveEffect.engrave(temp1);

			EmbossEffect embossEffect = new EmbossEffect();
			effectsBitmaps[3] = embossEffect.emboss(temp1);
			effectsBitmaps[4] = MeanRemovalEffect.applyMeanRemoval(temp1);
			effectsBitmaps[5] = FlipOrMirrorEffect.flip(temp1,
					Neevimagick.FLIP_HORIZONTAL);
			effectsBitmaps[6] = BlackFilter.applyBlackFilter(temp1);

			return effectsBitmaps;
		}

		protected void onPostExecute(Bitmap[] result) {
			if (dialog != null)
				removeDialog(PROGRESS_DIALOG);
			handler.sleep(Neevimagick.SNOW_EFFECT_TIME);
			setEffectsImages(result);
			setOnClicklistenersOverEffectsSampleImages(result);
			isSaved = false;
		}

	}

	public class AsyncTaskForNextActivity extends AsyncTask<String, Void, Void> {
		String nextClassName;

		public AsyncTaskForNextActivity(String nextClassName) {
			this.nextClassName = nextClassName;
		}

		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected Void doInBackground(String... params) {
			if (nextClassName.equals("PaintActivity")) {
				MemCacheManager.addBitmapToMemoryCache("changedImage",
						preChanges());
				Intent i = new Intent(ImageProcessingActivity.this,
						PaintActivity.class);
				startActivity(i);
				finish();
			} else if (nextClassName.equals("ShareImageActivity")) {
				firingShareActivity();
			} else if (nextClassName.equals("PhotoChooserActivity")) {
				Intent i = new Intent(ImageProcessingActivity.this,
						PhotoChooserActivity.class);
				startActivity(i);
				finish();
			} else if (nextClassName.equals("DistortionActivity")) {
				MemCacheManager.addBitmapToMemoryCache("changedImage",
						preChanges());
				Intent i = new Intent(ImageProcessingActivity.this,
						DistortionActivity.class);
				startActivity(i);
				finish();
			}

			return null;
		}

		protected void onPostExecute(Bitmap result) {
			if (dialog != null)
				removeDialog(PROGRESS_DIALOG);
		}

	}

	// Class which applyies the hue, saturation and brightness changes at a time
	// on an image
	class GroupBitmap {
		Bitmap bitmapDest;
	};

	// Update HSV according to SeekBar setting
	private GroupBitmap updateHSV(Bitmap src) {

		int progressHue = seekbarHue.getProgress() - 180;
		int progressSat = seekbarSaturation.getProgress() - 100;
		int progressBrit = seekbarBrightness.getProgress() - 100;

		float settingHue = (float) progressHue * 360 / 180;
		float settingSat = (float) progressSat / 100;
		float settingBrit = (float) progressBrit / 100;
		GroupBitmap convertedGroupBitmap = new GroupBitmap();

		int w = src.getWidth();
		int h = src.getHeight();

		int[] mapSrcColor = new int[w * h];
		int[] mapDestColor = new int[w * h];

		int[] mapHue = new int[w * h];
		int[] mapSat = new int[w * h];
		int[] mapBrit = new int[w * h];

		float[] pixelHSV = new float[4];

		src.getPixels(mapSrcColor, 0, w, 0, 0, w, h);

		int index = 0;
		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {

				// Convert from Color to HSV
				Color.colorToHSV(mapSrcColor[index], pixelHSV);

				// Adjust HSV
				pixelHSV[0] = pixelHSV[0] + settingHue;
				if (pixelHSV[0] < 0) {
					pixelHSV[0] = 0;
				} else if (pixelHSV[0] > 360) {
					pixelHSV[0] = 360;
				}

				pixelHSV[1] = pixelHSV[1] + settingSat;
				if (pixelHSV[1] < 0) {
					pixelHSV[1] = 0;
				} else if (pixelHSV[1] > 1) {
					pixelHSV[1] = 1;
				}

				pixelHSV[2] = pixelHSV[2] + settingBrit;
				if (pixelHSV[2] < 0) {
					pixelHSV[2] = 0;
				} else if (pixelHSV[2] > 1) {
					pixelHSV[2] = 1;
				}
				/*
				 * Represent Hue, Saturation and Value in separated color of R,
				 * G, B.
				 */
				mapHue[index] = Color
						.rgb((int) (pixelHSV[0] * 255 / 360), 0, 0);
				mapSat[index] = Color.rgb(0, (int) (pixelHSV[1] * 255), 0);
				mapBrit[index] = Color.rgb(0, 0, (int) (pixelHSV[2] * 255));

				// Convert back from HSV to Color
				mapDestColor[index] = Color.HSVToColor(pixelHSV);

				index++;
			}
		}

		Config destConfig = src.getConfig();
		/*
		 * If the bitmap's internal config is in one of the public formats,
		 * return that config, otherwise return null.
		 */

		if (destConfig == null) {
			destConfig = Config.RGB_565;
		}

		convertedGroupBitmap.bitmapDest = Bitmap.createBitmap(mapDestColor, w,
				h, destConfig);

		return convertedGroupBitmap;
	}

	public void setOnClicklistenersOverEffectsSampleImages(final Bitmap[] result) {
		invertedImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				image.setImageBitmap(result[0]);
			}
		});

		dullImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				image.setImageBitmap(result[1]);
			}
		});

		engraveImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				image.setImageBitmap(result[2]);
			}
		});

		embossImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				image.setImageBitmap(result[3]);
			}
		});

		meanremovalImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				image.setImageBitmap(result[4]);
			}
		});

		flipImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				image.setImageBitmap(result[5]);
			}
		});

		blackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				image.setImageBitmap(result[6]);
			}
		});

		snowImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				image.setImageBitmap(snowBitmap);
			}
		});

	}

	public void setEffectsImages(Bitmap[] result) {
		invertedImage.setImageBitmap(result[0]);
		dullImage.setImageBitmap(result[1]);
		engraveImage.setImageBitmap(result[2]);
		embossImage.setImageBitmap(result[3]);
		meanremovalImage.setImageBitmap(result[4]);
		flipImage.setImageBitmap(result[5]);
		blackImage.setImageBitmap(result[6]);
	}

	class GroupBitmapForColorLevel {
		Bitmap bitmapFinal;
	};

	// Update HSV according to SeekBar setting
	private GroupBitmapForColorLevel updateRGB(Bitmap src) {

		int progressRed = seekbarRed.getProgress() - 100;
		int progressGreen = seekbarGreen.getProgress() - 100;
		int progressBlue = seekbarBlue.getProgress() - 100;

		GroupBitmapForColorLevel convertedGroupBitmap = new GroupBitmapForColorLevel();

		int w = src.getWidth();
		int h = src.getHeight();
		int pixel;
		int A, R, G, B;
		Bitmap bmOut = Bitmap.createBitmap(w, h, src.getConfig());

		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				pixel = src.getPixel(x, y);

				A = Color.alpha(pixel);

				R = Color.red(pixel);

				G = Color.green(pixel);

				B = Color.blue(pixel);

				R += progressRed;

				if (R > 255) {
					R = 255;
				}

				else if (R < 0) {
					R = 0;
				}

				G += progressGreen;

				if (G > 255) {
					G = 255;
				}

				else if (G < 0) {
					G = 0;
				}

				B += progressBlue;

				if (B > 255) {
					B = 255;
				}

				else if (B < 0) {
					B = 0;
				}
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		Config destConfig = src.getConfig();
		/*
		 * If the bitmap's internal config is in one of the public formats,
		 * return that config, otherwise return null.
		 */

		if (destConfig == null) {
			destConfig = Config.RGB_565;
		}

		convertedGroupBitmap.bitmapFinal = bmOut;

		return convertedGroupBitmap;
	}

	// Handler for snow effect
	@SuppressLint("HandlerLeak")
	private class RefreshHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// Log.e("Calling", "asdad");
			ImageProcessingActivity.this.updateImage();
		}

		public void sleep(long millsec) {
			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), millsec);
		}
	}

	// Updating snow image
	private void updateImage() {
		snowImage.setImageBitmap(nextLevel);
		// Drawable tempDraw = image.getDrawable();
		snowBitmap = nextLevel; /* ((BitmapDrawable) tempDraw).getBitmap(); */
		SnowEffect snowEffect = new SnowEffect();
		snowBitmap = snowEffect.showSnowEffect(snowBitmap);
		snowImage.setImageBitmap(snowBitmap);
		handler.sleep(Neevimagick.SNOW_EFFECT_TIME);
	}

	// On destroy of this activity
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
		if (ImageProcessingActivity.isShared == true) {
			File file = new File(shareImageFilePath);
			boolean deleted = file.delete();
			Log.e("File deleted", "Success or failure  " + deleted);
		}
		super.onRestart();
	}

}
