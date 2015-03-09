<<<<<<< HEAD
package com.neevtech.imageprocessing;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * @author Shruti
 *
 */
public class SplashActivity extends Activity {
	ImageView splash;
	private final int SPLASH_DISPLAY_LENGHT = 3000;

	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.splash);
		}
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int den = metrics.densityDpi;

		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		final int width = display.getWidth();
		final int height = display.getHeight();
		splash = (ImageView) findViewById(R.id.splashimage);
		Log.d("The device density and screen sizes are", den + " and " + width
				+ " " + height);
		if (den == 160) {
			System.out.println(" DEN " + den + " WIDTH N HEIGHT " + width
					+ height);
			if (width == 320 && height == 480) {
				splash.setImageResource(R.drawable.neevlogo);
			}
			if (width == 480 && height == 800) {
				splash.setImageResource(R.drawable.neevlogo);
			}
			if (width == 480 && height == 854) {
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

				splash.setImageResource(R.drawable.neevlogo);
			}
			if (width == 600 && height == 1024) {
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

				splash.setImageResource(R.drawable.neevlogo);
			}
			if ((width == 800 && height == 1280)) {
				System.out.println(" DEN " + den + " WIDTH N HEIGHT " + width
						+ height);
				splash.setImageResource(R.drawable.neevlogo);
			}
			if ((width == 1024 && height == 768)) {

				splash.setImageResource(R.drawable.neevlogo);
			}
			if ((width == 1280 && height == 768)) {

				splash.setImageResource(R.drawable.neevlogo);
			}
		}
		if (den == 240) {

			if (width == 480 && height == 800) {
				splash.setImageResource(R.drawable.neevlogo);
			}
			if (width == 480 && height == 854) {
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

				splash.setImageResource(R.drawable.neevlogo);
			}
			if (width == 600 && height == 1024) {
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

				splash.setImageResource(R.drawable.neevlogo);
			}

		}
		Animation rAnimation = AnimationUtils.loadAnimation(this,
				R.anim.custom_anim);
		splash.startAnimation(rAnimation);
		/*
		 * TextView logoTitle = (TextView) findViewById(R.id.splashText);
		 * Animation lAnimation = AnimationUtils.loadAnimation(this,
		 * R.anim.fade_in); logoTitle.startAnimation(lAnimation);
		 */

		new Handler().postDelayed(new Runnable() {
			// @Override
			public void run() {
				/* Create an Intent that will start the Contacts-Activity. */

				Intent mainIntent = new Intent(SplashActivity.this,
						PhotoChooserActivity.class); // for showing default
														// message
				mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				SplashActivity.this.startActivity(mainIntent);

				SplashActivity.this.finish();

			}
		}, SPLASH_DISPLAY_LENGHT);
	}

}
=======
package com.neevtech.imageprocessing;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * @author Shruti
 *
 */
public class SplashActivity extends Activity {
	ImageView splash;
	private final int SPLASH_DISPLAY_LENGHT = 3000;

	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.splash);
		}
	}

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int den = metrics.densityDpi;

		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		final int width = display.getWidth();
		final int height = display.getHeight();
		splash = (ImageView) findViewById(R.id.splashimage);
		Log.d("The device density and screen sizes are", den + " and " + width
				+ " " + height);
		if (den == 160) {
			System.out.println(" DEN " + den + " WIDTH N HEIGHT " + width
					+ height);
			if (width == 320 && height == 480) {
				splash.setImageResource(R.drawable.neevlogo);
			}
			if (width == 480 && height == 800) {
				splash.setImageResource(R.drawable.neevlogo);
			}
			if (width == 480 && height == 854) {
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

				splash.setImageResource(R.drawable.neevlogo);
			}
			if (width == 600 && height == 1024) {
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

				splash.setImageResource(R.drawable.neevlogo);
			}
			if ((width == 800 && height == 1280)) {
				System.out.println(" DEN " + den + " WIDTH N HEIGHT " + width
						+ height);
				splash.setImageResource(R.drawable.neevlogo);
			}
			if ((width == 1024 && height == 768)) {

				splash.setImageResource(R.drawable.neevlogo);
			}
			if ((width == 1280 && height == 768)) {

				splash.setImageResource(R.drawable.neevlogo);
			}
		}
		if (den == 240) {

			if (width == 480 && height == 800) {
				splash.setImageResource(R.drawable.neevlogo);
			}
			if (width == 480 && height == 854) {
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

				splash.setImageResource(R.drawable.neevlogo);
			}
			if (width == 600 && height == 1024) {
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

				splash.setImageResource(R.drawable.neevlogo);
			}

		}
		Animation rAnimation = AnimationUtils.loadAnimation(this,
				R.anim.custom_anim);
		splash.startAnimation(rAnimation);
		/*
		 * TextView logoTitle = (TextView) findViewById(R.id.splashText);
		 * Animation lAnimation = AnimationUtils.loadAnimation(this,
		 * R.anim.fade_in); logoTitle.startAnimation(lAnimation);
		 */

		new Handler().postDelayed(new Runnable() {
			// @Override
			public void run() {
				/* Create an Intent that will start the Contacts-Activity. */

				Intent mainIntent = new Intent(SplashActivity.this,
						PhotoChooserActivity.class); // for showing default
														// message
				mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				SplashActivity.this.startActivity(mainIntent);

				SplashActivity.this.finish();

			}
		}, SPLASH_DISPLAY_LENGHT);
	}

}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
