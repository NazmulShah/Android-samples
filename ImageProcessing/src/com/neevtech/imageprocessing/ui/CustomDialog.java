<<<<<<< HEAD
package com.neevtech.imageprocessing.ui;

import java.util.List;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.neevtech.imageprocessing.PhotoChooserActivity;
import com.neevtech.imageprocessing.R;

/**
 * @author Shruti
 * 
 */
public class CustomDialog {
	public static void showDialog(final Context context, final String title,
			final boolean cancelable, final String operation,
			final String positivebtnname, final String negativebtnname) {
		AlertDialog.Builder builder;
		builder = new AlertDialog.Builder(context);
		builder.setCancelable(cancelable);
		builder.setIcon(context.getResources().getDrawable(R.drawable.alert));

		builder.setTitle(capitalize(title));

		builder.setPositiveButton(positivebtnname, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				/*
				 * if (operation.equals("SaveImage")) { //This is not called
				 * SaveImage.imageSave(
				 * ImageProcessingActivity.extStorageDirectory,
				 * ImageProcessingActivity.picname, context,
				 * ImageProcessingActivity.image,
				 * ++ImageProcessingActivity.imageCount); Log.e("Context name",
				 * "..." + context); String part = "" + context; String[] parts
				 * = part.split("@"); if (parts[0]
				 * .equals("com.neevtech.imageprocessing.ImageProcesingActivity"
				 * )) { ImageProcessingActivity.isSaved = true; } else if
				 * (parts[0] .equals("com.neevtech.imageprocessing.DummyPaint"))
				 * { PaintActivity.isSaved = true; } dialog.cancel();
				 * dialog.dismiss(); } else
				 */if (operation.equals("Quit")) {
					dialog.cancel();
					dialog.dismiss();
					List<RunningAppProcessInfo> procInfos = PhotoChooserActivity.am
							.getRunningAppProcesses();
					for (int i = 0; i < procInfos.size(); i++) {
						System.out.println("These are the running processes..."
								+ procInfos.get(i).processName);
						if (procInfos.get(i).processName
								.equals("com.neevtech.imageprocessing")) {
							int myId = procInfos.get(i).pid;
							// android.os.Process.killProcess(android.os.Process.myPid());
							System.out
									.println("The pid of Image processing app.."
											+ myId);
							android.os.Process.killProcess(myId);
							System.out
									.println("Image processing app killed successfully...");
						}
					}
				}
				/*
				 * else if (operation.equals("TweetError")) { dialog.cancel();
				 * dialog.dismiss(); }
				 */

			}
		});

		builder.setNegativeButton(negativebtnname, new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				dialog.dismiss();
			}
		});
		AlertDialog tempDlg = builder.create();
		tempDlg.show();
	}

	private static String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
}
=======
package com.neevtech.imageprocessing.ui;

import java.util.List;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.neevtech.imageprocessing.PhotoChooserActivity;
import com.neevtech.imageprocessing.R;

/**
 * @author Shruti
 * 
 */
public class CustomDialog {
	public static void showDialog(final Context context, final String title,
			final boolean cancelable, final String operation,
			final String positivebtnname, final String negativebtnname) {
		AlertDialog.Builder builder;
		builder = new AlertDialog.Builder(context);
		builder.setCancelable(cancelable);
		builder.setIcon(context.getResources().getDrawable(R.drawable.alert));

		builder.setTitle(capitalize(title));

		builder.setPositiveButton(positivebtnname, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				/*
				 * if (operation.equals("SaveImage")) { //This is not called
				 * SaveImage.imageSave(
				 * ImageProcessingActivity.extStorageDirectory,
				 * ImageProcessingActivity.picname, context,
				 * ImageProcessingActivity.image,
				 * ++ImageProcessingActivity.imageCount); Log.e("Context name",
				 * "..." + context); String part = "" + context; String[] parts
				 * = part.split("@"); if (parts[0]
				 * .equals("com.neevtech.imageprocessing.ImageProcesingActivity"
				 * )) { ImageProcessingActivity.isSaved = true; } else if
				 * (parts[0] .equals("com.neevtech.imageprocessing.DummyPaint"))
				 * { PaintActivity.isSaved = true; } dialog.cancel();
				 * dialog.dismiss(); } else
				 */if (operation.equals("Quit")) {
					dialog.cancel();
					dialog.dismiss();
					List<RunningAppProcessInfo> procInfos = PhotoChooserActivity.am
							.getRunningAppProcesses();
					for (int i = 0; i < procInfos.size(); i++) {
						System.out.println("These are the running processes..."
								+ procInfos.get(i).processName);
						if (procInfos.get(i).processName
								.equals("com.neevtech.imageprocessing")) {
							int myId = procInfos.get(i).pid;
							// android.os.Process.killProcess(android.os.Process.myPid());
							System.out
									.println("The pid of Image processing app.."
											+ myId);
							android.os.Process.killProcess(myId);
							System.out
									.println("Image processing app killed successfully...");
						}
					}
				}
				/*
				 * else if (operation.equals("TweetError")) { dialog.cancel();
				 * dialog.dismiss(); }
				 */

			}
		});

		builder.setNegativeButton(negativebtnname, new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				dialog.dismiss();
			}
		});
		AlertDialog tempDlg = builder.create();
		tempDlg.show();
	}

	private static String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
