/**
 * 
 */
package com.neevtech.imageprocessing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Ashish R Agre
 * 
 */
public class ShareImageActivity extends Activity {
	protected byte[] imageData;
	protected ImageView imgView;
	protected String shareDescriptionText;

	private ImageView twitterShare, facebookShare;
	private EditText shareImageDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.share_image_layout);
		imgView = (ImageView) findViewById(R.id.finalShareImg);
		/*
		 * Getting the current updated image the setting it to the image view
		 * before presenting user to share it on twitter or on facebook
		 */
		Intent receivedIntent = getIntent();
		byte[] data = receivedIntent.getByteArrayExtra("imgByteData");
		this.imageData = data;
		Log.e("ShareImageactivity", "Byte data length " + data.length);
		Bitmap imageToBeShared = BitmapFactory.decodeByteArray(data, 0,
				data.length);
		imgView.setImageBitmap(imageToBeShared);
		/*
		 * Initializing of twitter and facebook reference imageview to provide
		 * sharing functionality to the user.
		 */

		twitterShare = (ImageView) findViewById(R.id.shareImageTwitter);
		facebookShare = (ImageView) findViewById(R.id.shareImageFacebook);
		/*
		 * Description Edittext
		 */
		shareImageDescription = (EditText) findViewById(R.id.shareImageDescription);

		/*
		 * Listener for twitter share imageview
		 */
		twitterShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(ShareImageActivity.this, "Twitter Share Image",
						Toast.LENGTH_SHORT).show();
				String dummyText = shareImageDescription.getText().toString();
				shareDescriptionText = "Description";
				Log.e("Dummy Text ", "" + dummyText.length());
				if (dummyText != null && dummyText != "" && dummyText.length() != 0) {
					shareDescriptionText = dummyText;
				}
				Log.e("Dummy Text ", "" + dummyText);
				Log.e("Description Text ", "" + shareDescriptionText);

				Intent shareIntent = new Intent(ShareImageActivity.this,
						TwitterShareActivity.class);
				shareIntent.putExtra("message", shareDescriptionText);
				shareIntent.putExtra("imageByteArray", imageData);
				startActivity(shareIntent);
				finish();
			}
		});
		/*
		 * Listener for facebook share imageview
		 */
		facebookShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String dummyText = shareImageDescription.getText().toString();
				shareDescriptionText = "Description";
				Log.e("Dummy Text ", "" + dummyText.length());
				if (dummyText != null && dummyText != "" && dummyText.length() != 0) {
					shareDescriptionText = dummyText;
				}
				Log.e("Dummy Text ", "" + dummyText);
				Log.e("Description Text ", "" + shareDescriptionText);
				Toast.makeText(ShareImageActivity.this, "Facebook Share Image",
						Toast.LENGTH_SHORT).show();

				Intent shareIntent = new Intent(ShareImageActivity.this,
						FbShareImageActivity.class);
				shareIntent.putExtra("message", shareDescriptionText);
				shareIntent.putExtra("imageByteArray", imageData);
				startActivity(shareIntent);
				finish();
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
	}	
	
	public void onBackPressed() {
		Intent i = new Intent (ShareImageActivity.this, PhotoChooserActivity.class);
		startActivity(i);
		finish();
	}
}
