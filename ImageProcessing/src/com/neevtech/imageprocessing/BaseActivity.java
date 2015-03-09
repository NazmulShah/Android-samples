package com.neevtech.imageprocessing;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.neevtech.imageprocessing.MyHorizontalScrollView.SizeCallback;

/**
 * @author Shruti
 *
 */
public class BaseActivity extends Activity {

	protected static View menu = null;
	protected ImageView menuButton;
	// protected ImageView createTournamentButton = null;
	protected View app = null;
	protected MyHorizontalScrollView scrollView = null;
	protected boolean inflateMenu = false;
	protected static boolean menuOut = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (inflateMenu) {
			menu = null;
			if (this.getClass() == ImageProcessingActivity.class) {
				setContentView(R.layout.imageprocessingmain);
				Log.e("Set content view as", "paintmain");
				scrollView = (MyHorizontalScrollView) findViewById(R.id.scrollView);
			} else if (this.getClass() == PaintActivity.class) {
				setContentView(R.layout.paintmain);
				Log.e("Set content view as", "paintmain");
				scrollView = (MyHorizontalScrollView) findViewById(R.id.paintscrollView);
			} else if(this.getClass() == DistortionActivity.class) {
				setContentView(R.layout.distortmain);
				Log.e("Set content view as", "distortmain");
				scrollView = (MyHorizontalScrollView) findViewById(R.id.distortscrollView);
			}
			if (menu == null) {
				LayoutInflater inflater = LayoutInflater.from(this);
				Log.e("Menu is null here", "  ");
				if (this.getClass() == ImageProcessingActivity.class) {
					menu = inflater.inflate(R.layout.menulist, null);
					Log.e("Inflating", "imageproc menulist");
				} else if (this.getClass() == PaintActivity.class) {
					menu = inflater.inflate(R.layout.paintmenulist, null);
					Log.e("Inflating", "paint menulist");
				} else if (this.getClass() == DistortionActivity.class) {
					menu = inflater.inflate(R.layout.distortmenulist, null);
					Log.e("Inflating", "distort menulist");
				} 
			} else {
				if (menu != null && menu.getParent() != null) {
					Log.e("Menu not null", "  ");
					ViewGroup viewgp = (ViewGroup) menu.getParent();
					viewgp.removeView(menu);
				}
				menu.getBackground().setCallback(null);
			}
		}
	}

	protected void loadScroller() {
		Log.e("Menu button is", "" + menuButton);
		if (menuButton != null) {
			menuButton.setOnClickListener(new ClickListenerForScrolling(
					scrollView, menu));
			// sliderMenuButton.setOnTouchListener(new
			// TouchListenerForScrolling(
			// scrollView, menu));
			final View[] children = new View[] { menu, app };

			// Scroll to app (view[1]) when layout finished.
			int scrollToViewIdx = 1;
			scrollView.initViews(children, scrollToViewIdx,
					new SizeCallbackForMenu(menuButton));
		}
	}

	protected void unLoadScroller() {
		if (menuButton != null) {
			menuButton.setOnClickListener(null);
			scrollView.removeAllViews();
		}

	}

	static class SizeCallbackForMenu implements SizeCallback {
		int btnWidth;
		View btnSlide;

		public SizeCallbackForMenu(View btnSlide) {
			super();
			this.btnSlide = btnSlide;
		}

		@Override
		public void onGlobalLayout() {
			btnWidth = btnSlide.getMeasuredWidth();
		}

		@Override
		public void getViewSize(int idx, int w, int h, int[] dims) {
			dims[0] = w;
			dims[1] = h;
			final int menuIdx = 0;
			if (idx == menuIdx) {
				dims[0] = w - btnWidth - 20;
			}
		}
	}

	public void hideMenuLayout() { // Ensure menu is visible
		menu.setVisibility(View.VISIBLE);
		Log.e("Status of menu", "Is open or not" + menuOut);
		if (menuOut == true) {
			// Scroll to 0 to reveal menu Log.e("Menu is open", "  " + menuOut);
			scrollView.smoothScrollTo(scrollView.getMeasuredWidth(), 0);
			// menuOut = false;
			Log.e("Changed the status of menu", "menuOut" + menuOut);
		} //
		menuOut = !menuOut;
	}

	@Override
	protected void onDestroy() {
		menuButton = null;
		app = null;
		if (scrollView != null) {
			scrollView.removeAllViews();
			scrollView = null;
		}
		super.onDestroy();
	}

	static class ClickListenerForScrolling implements OnClickListener {
		HorizontalScrollView scrollView;
		View menu;

		/**
		 * Menu must NOT be out/shown to start with.
		 */

		public ClickListenerForScrolling(HorizontalScrollView scrollView,
				View menu) {
			// super();
			this.scrollView = scrollView;
			this.menu = menu;
			// this.menuOutInside = menuOut;
			Log.e("Status of menu in constructor", "menu " + menuOut);
		}

		@Override
		public void onClick(View v) {
			boolean menuOutInside = menuOut;
			Log.e("Status of menu in on Click of", "menu " + menuOutInside);

			int menuWidth = menu.getMeasuredWidth();
			if (menuOutInside == false) {
				// Opening menu
				int left = 0;
				scrollView.smoothScrollTo(left, 0);
			} else {
				// Closing menu
				int left = menuWidth;
				scrollView.smoothScrollTo(left, 0);
				// Log.e("Menu is closed", "So opening");
			}
			menuOutInside = !menuOutInside;
			menuOut = menuOutInside;
			Log.e("Status of menu in onclick after close or open", "boolean..."
					+ menuOutInside);
		}
	}

}
