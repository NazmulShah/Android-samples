<<<<<<< HEAD
package com.neevtech.imageprocessing;

import android.app.Application;
import android.os.StrictMode;

public class ImageApplication extends Application {
	@Override
	public void onCreate() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectAll() // or
				// .detectAll()
				// for
				// all
				// detectable
				// problems
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
				.penaltyLog().penaltyDeath().build());
		super.onCreate();
	}
}
=======
package com.neevtech.imageprocessing;

import android.app.Application;
import android.os.StrictMode;

public class ImageApplication extends Application {
	@Override
	public void onCreate() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectAll() // or
				// .detectAll()
				// for
				// all
				// detectable
				// problems
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
				.penaltyLog().penaltyDeath().build());
		super.onCreate();
	}
}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
