<<<<<<< HEAD
package com.example.tagmyvideo.ui.widget.custom_dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

public class CustomDialog {

	private Context context;
	View tempView = null;
	private AlertDialog.Builder builder;
	private DialogCreatedListener dialogCreatedListener;
	private AlertDialog tempDlg;
	private boolean cancelable;
	private Drawable icon;
	private int layoutResource;
	private String title;
	private String description;
	private String positivebtnname;
	private String negativebtnname;
	private OnClickListener positiveClickListener;
	private OnClickListener negativeClickListener;

	public CustomDialog(final Context context, final String title,
			final String description, OnClickListener positiveClickListener) {
		dialogCreatedListener = null;
		tempDlg = null;
		setCancelable(false);
		setIcon(null);
		setLayoutResource(-1);
		setPositivebtnname("Ok");
		setNegativebtnname("Cancel");
		this.setTitle(title);
		this.setDescription(description);
		this.context = context;
		builder = new AlertDialog.Builder(context);
		this.positiveClickListener = positiveClickListener;
	}

	/**
	 * @param dialogCreatedListener
	 *            the dialogCreatedListener to set
	 */
	public void setDialogCreatedListener(
			DialogCreatedListener dialogCreatedListener) {
		this.dialogCreatedListener = dialogCreatedListener;
	}

	public void showDialog() {
		builder.setCancelable(isCancelable());
		if (icon != null)
			builder.setIcon(icon);

		builder.setTitle(capitalize(title));
		if (layoutResource != -1) {
			LayoutInflater inflater = LayoutInflater.from(context);
			tempView = inflater.inflate(layoutResource, null);
			builder.setView(tempView);
		} else if (description != null) {
			builder.setMessage(description);
		}

		builder.setPositiveButton(positivebtnname, positiveClickListener);

		setDefaultNegativeListener();
		builder.setNegativeButton(negativebtnname, negativeClickListener);
		tempDlg = builder.create();
		tempDlg.show();
		if (dialogCreatedListener != null)
			dialogCreatedListener.onDialogCreated(tempView);
	}

	private static String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
	
	public View getView(){
		return tempView;
	}

	/**
	 * @return the cancelable
	 */
	public boolean isCancelable() {
		return cancelable;
	}

	/**
	 * @param cancelable
	 *            the cancelable to set
	 */
	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	/**
	 * @param layoutResource
	 *            the layoutResource to set
	 */
	public void setLayoutResource(int layoutResource) {
		this.layoutResource = layoutResource;
	}

	/**
	 * @param positivebtnname
	 *            the positivebtnname to set
	 */
	public void setPositivebtnname(String positivebtnname) {
		this.positivebtnname = positivebtnname;
	}

	/**
	 * @param negativebtnname
	 *            the negativebtnname to set
	 */
	public void setNegativebtnname(String negativebtnname) {
		this.negativebtnname = negativebtnname;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param positiveClickListener
	 *            the positiveClickListener to set
	 */
	public void setPositiveClickListener(OnClickListener positiveClickListener) {
		
		this.positiveClickListener = positiveClickListener;
	}

	/**
	 * @param negativeClickListener
	 *            the negativeClickListener to set
	 */
	public void setNegativeClickListener(OnClickListener negativeClickListener) {
		this.negativeClickListener = negativeClickListener;
	}

	private void setDefaultNegativeListener() {
		if (negativeClickListener == null)
			this.negativeClickListener = new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.cancel();
					dialog.dismiss();
				}

			};

	}

}
=======
package com.example.tagmyvideo.ui.widget.custom_dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

public class CustomDialog {

	private Context context;
	View tempView = null;
	private AlertDialog.Builder builder;
	private DialogCreatedListener dialogCreatedListener;
	private AlertDialog tempDlg;
	private boolean cancelable;
	private Drawable icon;
	private int layoutResource;
	private String title;
	private String description;
	private String positivebtnname;
	private String negativebtnname;
	private OnClickListener positiveClickListener;
	private OnClickListener negativeClickListener;

	public CustomDialog(final Context context, final String title,
			final String description, OnClickListener positiveClickListener) {
		dialogCreatedListener = null;
		tempDlg = null;
		setCancelable(false);
		setIcon(null);
		setLayoutResource(-1);
		setPositivebtnname("Ok");
		setNegativebtnname("Cancel");
		this.setTitle(title);
		this.setDescription(description);
		this.context = context;
		builder = new AlertDialog.Builder(context);
		this.positiveClickListener = positiveClickListener;
	}

	/**
	 * @param dialogCreatedListener
	 *            the dialogCreatedListener to set
	 */
	public void setDialogCreatedListener(
			DialogCreatedListener dialogCreatedListener) {
		this.dialogCreatedListener = dialogCreatedListener;
	}

	public void showDialog() {
		builder.setCancelable(isCancelable());
		if (icon != null)
			builder.setIcon(icon);

		builder.setTitle(capitalize(title));
		if (layoutResource != -1) {
			LayoutInflater inflater = LayoutInflater.from(context);
			tempView = inflater.inflate(layoutResource, null);
			builder.setView(tempView);
		} else if (description != null) {
			builder.setMessage(description);
		}

		builder.setPositiveButton(positivebtnname, positiveClickListener);

		setDefaultNegativeListener();
		builder.setNegativeButton(negativebtnname, negativeClickListener);
		tempDlg = builder.create();
		tempDlg.show();
		if (dialogCreatedListener != null)
			dialogCreatedListener.onDialogCreated(tempView);
	}

	private static String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
	
	public View getView(){
		return tempView;
	}

	/**
	 * @return the cancelable
	 */
	public boolean isCancelable() {
		return cancelable;
	}

	/**
	 * @param cancelable
	 *            the cancelable to set
	 */
	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	/**
	 * @param layoutResource
	 *            the layoutResource to set
	 */
	public void setLayoutResource(int layoutResource) {
		this.layoutResource = layoutResource;
	}

	/**
	 * @param positivebtnname
	 *            the positivebtnname to set
	 */
	public void setPositivebtnname(String positivebtnname) {
		this.positivebtnname = positivebtnname;
	}

	/**
	 * @param negativebtnname
	 *            the negativebtnname to set
	 */
	public void setNegativebtnname(String negativebtnname) {
		this.negativebtnname = negativebtnname;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param positiveClickListener
	 *            the positiveClickListener to set
	 */
	public void setPositiveClickListener(OnClickListener positiveClickListener) {
		
		this.positiveClickListener = positiveClickListener;
	}

	/**
	 * @param negativeClickListener
	 *            the negativeClickListener to set
	 */
	public void setNegativeClickListener(OnClickListener negativeClickListener) {
		this.negativeClickListener = negativeClickListener;
	}

	private void setDefaultNegativeListener() {
		if (negativeClickListener == null)
			this.negativeClickListener = new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.cancel();
					dialog.dismiss();
				}

			};

	}

}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
