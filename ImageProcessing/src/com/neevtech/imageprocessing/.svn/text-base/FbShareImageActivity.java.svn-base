package com.neevtech.imageprocessing;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.neevtech.imageprocessing.constants.Neevimagick;
import com.neevtech.imageprocessing.facebook.sdk.helper.FacebookConnector;
import com.neevtech.imageprocessing.facebook.sdk.helper.SessionEvents.AuthListener;
import com.neevtech.imageprocessing.facebook.sdk.model.FacebookProfile;

public class FbShareImageActivity extends Activity implements AuthListener {
	FacebookConnector facebookConnector;
	private String message = "Hello Image";
	private byte[] imageByteData;
	public static String[] FACEBOOK_PERMISSIONS = new String[] {
			"user_about_me", "email", "user_checkins", "friends_checkins",
			"offline_access", "user_relationships", "user_birthday",
			"user_likes", "user_photos", "user_status", "publish_checkins",
			"friends_photos", "friends_status", "publish_stream" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		/*Drawable tempDraw = imgView.getDrawable();
		Bitmap image = ((BitmapDrawable)tempDraw).getBitmap();		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		data = baos.toByteArray();*/		
		
		Intent receivedIntent = getIntent();
		message = receivedIntent.getStringExtra("message");
		imageByteData = receivedIntent.getByteArrayExtra("imageByteArray");		
	//	Toast.makeText(this, "Message is " + message + " and image data length is ::: " + imageByteData.length, Toast.LENGTH_SHORT).show();
		fbShare();
	

	}

	
	public void fbShare() {
		if (facebookConnector == null) {
			facebookConnector = new FacebookConnector(Neevimagick.FACEBOOK_APP_ID,
					FbShareImageActivity.this, getApplicationContext(),
					FACEBOOK_PERMISSIONS);
			facebookConnector.addAuthListener(FbShareImageActivity.this);
		}
		facebookConnector.logout();
		facebookConnector.login();

		// if (isLogggedIn) {
		// Toast.makeText(mContext, Constants.FB_SHARE_MSG, Toast.LENGTH_LONG)
		// .show();
		// }
		// System.err.println("fbSHare");
	}

	public void onAuthSucceed() {
		Toast.makeText(this, "Logged successfully into facebook", Toast.LENGTH_SHORT).show();
		//facebookConnector.postImageOnWall(message, data);
		FacebookShareAsyncTask share = new FacebookShareAsyncTask(this);
		share.execute();
		/*try {
			facebookConnector.postMessageOnWall("Hello Pasdasdasosting as image Again...!!!");
			facebookConnector.postImageOnWall(message, data);
			//String res = facebookConnector.getFbFriends();
			//Log.d("I got response for fb friends list", "..."+res);
		//	Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//	String jsonObj = gson.toJson(res);
		//	Log.d("I got response for fb friends list", jsonObj);
			//printFriendList(res);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	public void onAuthFail(String error) {
		Toast.makeText(this, "Facebook login error, please try again", Toast.LENGTH_SHORT).show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case FacebookConnector.REQUEST_CODE_FB:

			facebookConnector.getFacebook().authorizeCallback(requestCode,
					resultCode, data);
			if (resultCode != Activity.RESULT_OK) {
				facebookConnector.logout();

			}

			break;
		}

	}
	
	
	private void printFriendList(String jsonString) {
		List<FacebookProfile> friendList = new ArrayList<FacebookProfile>();
		FacebookProfile tempProfile = null;
		Log.i("Json object started", "JSON object stareted here");
		int dataCount = 0;
		try{
			JSONArray jsonArray = new JSONArray("[" + jsonString + "]");
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			//Log.i("JSON Object response us " , "::: " + jsonObject.toString());
			JSONArray dataArray = jsonObject.getJSONArray("data");
			//Log.i("JSON DATA ARRAY", "::: " + dataArray.toString());
			JSONObject tempDataJsonObject = null;
			
			for(int i = 0;i<dataArray.length();i++) {
				dataCount++;
				tempDataJsonObject = dataArray.getJSONObject(i);
				Log.i("url", tempDataJsonObject.toString());
				/*JSONArray pictureJsonArray = tempDataJsonObject.getJSONArray("picture");
				Log.i("PICTURE DETAILS", "URL::: " + pictureJsonArray.toString());*/
				JSONObject pictureJsonArray = tempDataJsonObject.getJSONObject("picture");
				Log.i("PICTURE DETAILS", "URL::: " + pictureJsonArray.toString());					
			}
			Log.i("JSON DATA ARRAY", "::: Count :::" + dataCount);
		}catch(Exception e) {
			Log.e("Error Message", "JSON Pasrsing Error message" + e);
		}
		
		Log.i("Json object started", "JSON object finished here");
	}
	
	private class FacebookShareAsyncTask extends AsyncTask<Void, Void, Boolean> {
		private ProgressDialog progressDialog;
		private Context context;
		FacebookShareAsyncTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setTitle("Uploading to facebook");
			progressDialog.setMessage("Please wait... while the image is being uploaded onto facebook!");
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			return facebookConnector.postImageOnWall(message, imageByteData);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(result) {
				progressDialog.cancel();
				progressDialog.dismiss();
				Intent mainScreenIntent = new Intent(FbShareImageActivity.this, PhotoChooserActivity.class);
				mainScreenIntent.putExtra("sharedImage", true);
				startActivity(mainScreenIntent);
				finish();
			}
		}
	}	
	
}
