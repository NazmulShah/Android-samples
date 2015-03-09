<<<<<<< HEAD
package com.neevtech.imageprocessing.facebook.sdk.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import oauth.signpost.exception.OAuthException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.BaseKeyListener;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.neevtech.imageprocessing.constants.Neevimagick;
import com.neevtech.imageprocessing.facebook.sdk.helper.SessionEvents.AuthListener;
import com.neevtech.imageprocessing.facebook.sdk.helper.SessionEvents.LogoutListener;

public class FacebookConnector {

	private Facebook facebook = null;
	private Context context;
	private String[] permissions;
	private Handler mHandler;
	private Activity activity;
	private SessionListener mSessionListener = new SessionListener();
	private String wallMessage = "";
	public static final int REQUEST_CODE_FB = 66;
	private static final String ERROR_CODE_MAX_FEED_LIMIT = "(#341)";
	private static final String ERROR_DUPLICATE_MESSAGE = "(#506)";
	private static final String ID = "id";
	private static final String MSG_ALREADY_POSTED = "Your Message is already posted on your Wall";
	private static final String MSG_FEED_ACTION_LIMIT_REACHED = "Feed action request limit reached";

	public FacebookConnector(String appId, Activity activity, Context context,
			String[] permissions) {
		this.facebook = new Facebook(appId);

		SessionStore.restore(facebook, context);
		
		//SessionEvents.addAuthListener(mSessionListener);
		 //SessionEvents.addLogoutListener(mSessionListener);

		this.context = context;
		this.permissions = permissions;
		this.mHandler = new Handler();
		this.activity = activity;
	}

	public void addAuthListener(AuthListener authListener) {
		SessionEvents.addAuthListener(authListener);
	}

	public void removeAuthListener(AuthListener authListener) {
		SessionEvents.removeAuthListener(authListener);
	}

	public void addLogoutListener(LogoutListener logoutListener) {
		SessionEvents.addLogoutListener(logoutListener);
	}

	public void removeLogoutListener(LogoutListener logoutListener) {
		SessionEvents.removeLogoutListener(logoutListener);
	}

	public boolean login() {
		if (!facebook.isSessionValid()) {
			// System.out.println("***FB AUthorizing...");
			if (activity != null)
				facebook.authorize(activity, this.permissions, REQUEST_CODE_FB,
						new LoginDialogListener());

			return false;
		} else {
			return true;
		}
	}

	public boolean loginAndPost(String message) {
		wallMessage = message;
		if (!facebook.isSessionValid()) {
			facebook.authorize(activity, permissions, REQUEST_CODE_FB,
					new LoginDialogListener());
		//	Toast.makeText(context, "Token ::: " + facebook.getAccessToken(), Toast.LENGTH_LONG).show();
			return false;
		} else {
			return true;
		}
	}

	public void logout() {
		SessionEvents.onLogoutBegin();
		AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(this.facebook);
		asyncRunner.logout(this.context, new LogoutRequestListener());
	}

	public String getFBid() throws OAuthException, FileNotFoundException,
			MalformedURLException, IOException, JSONException {
		// Bundle parameters = new Bundle();
		//
		//
		// return facebook.request("me", parameters);

		// ... login user here ...
		JSONObject me = new JSONObject(facebook.request("me"));
		return me.getString("id");

	}

	public String getFBName() throws OAuthException, FileNotFoundException,
			MalformedURLException, IOException, JSONException {
		JSONObject me = new JSONObject(facebook.request("me"));
		return me.getString("first_name");

	}

	public void postMessageOnWall(String msg) throws OAuthException,
			FileNotFoundException, MalformedURLException, IOException {

		Bundle parameters = new Bundle();
		parameters.putString("message", msg);

		String response = facebook.request("me/feed", parameters, "POST");
		if (response.contains(ERROR_CODE_MAX_FEED_LIMIT)) {
			Toast.makeText(context, MSG_FEED_ACTION_LIMIT_REACHED,
					Toast.LENGTH_LONG).show();
			//facebook.logout(context);

		} else if (response.contains(ID)) {
			Toast.makeText(context, "test", Toast.LENGTH_LONG).show();
			//facebook.logout(context);

		} else if (response.contains(ERROR_DUPLICATE_MESSAGE)) {
			Toast.makeText(context, MSG_ALREADY_POSTED, Toast.LENGTH_LONG)
					.show();
			//facebook.logout(context);
		}
		System.out.println("response" + response);

	}

	/*
	 * Post Image On Our own wall ...
	 */

	public boolean postImageOnWall(String message, byte[] imgData) {
		Bundle parameters = new Bundle();
		parameters.putString(Facebook.TOKEN, facebook.getAccessToken());
		parameters.putString ("message", message);
		parameters.putByteArray ("source", imgData);
		parameters.putByteArray("picture", imgData);
		this.facebook.setAccessToken(Neevimagick.FACEBOOK_ACCESS_TOKEN);

		AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(this.facebook);
		try {
			Log.e("Runner and the token is ", mAsyncRunner.toString() + ":::" + Neevimagick.FACEBOOK_ACCESS_TOKEN);
			mAsyncRunner.request("me/photos", parameters, "POST",
					new SampleUploadListener(), null);
			return true;
		} catch (Exception e) {
			Log.e("Facebook-Example", "Facebook Error: runner" + e.getMessage());
			return false;
		}
	}

	public void postMessageOnFriendWall(Context activityContext, String msg,
			String friendId) throws OAuthException, FileNotFoundException,
			MalformedURLException, IOException, JSONException {

		Bundle parameters = new Bundle();
		parameters.putString("message", getFBName() + msg);
		// parameters.putString("description", "APPBattler");
		// parameters.putString("caption", "Appbattler");
		String response = facebook.request(friendId + "/feed", parameters,
				"POST");
		// Log.d("FACEBOOK RESPONSE",response);
		// if (response == null || response.equals("") ||
		// response.equals("false")) {
		// Log.v("Error", "Blank response");
		// } else{
		// Toast.makeText(activityContext, "Successfuly Invited",
		// Toast.LENGTH_SHORT).show();
		// }

		/*
		 * Bundle params = new Bundle(); params.putString("to", friendId);
		 * params.putString("caption", "Appbattler");
		 * params.putString("description", "APPBattler");
		 * params.putString("message", msg); // params.putString("picture",
		 * Utility.HACK_ICON_URL); params.putString("name", "test");
		 * 
		 * // String response = facebook.request("feed", params, "POST");
		 * 
		 * facebook.dialog(activityContext, "feed", params, new DialogListener()
		 * {
		 * 
		 * @Override public void onFacebookError(FacebookError e) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onError(DialogError e) { // TODO Auto-generated
		 * method stub
		 * 
		 * }
		 * 
		 * @Override public void onComplete(Bundle values) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onCancel() { // TODO Auto-generated method stub
		 * 
		 * } });
		 */
		/*
		 * if (response.contains(ERROR_CODE_MAX_FEED_LIMIT)) {
		 * Toast.makeText(context, MSG_FEED_ACTION_LIMIT_REACHED,
		 * Toast.LENGTH_LONG).show(); facebook.logout(context);
		 * 
		 * } else if (response.contains(ID)) { Toast.makeText(context,
		 * Constants.FB_SHARE_MSG, Toast.LENGTH_LONG) .show();
		 * facebook.logout(context);
		 * 
		 * } else if (response.contains(ERROR_DUPLICATE_MESSAGE)) {
		 * Toast.makeText(context, MSG_ALREADY_POSTED, Toast.LENGTH_LONG)
		 * .show(); facebook.logout(context); } System.out.println("response" +
		 * response);
		 */

	}

	public String getFbFriends() throws OAuthException, FileNotFoundException,
			MalformedURLException, IOException {

		Bundle parameters = new Bundle();
		// parameters.putString("message", msg);
		// try
		// String query =
		// "SELECT uid,name,birthday_date, hometown_location FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me()))";
		// Bundle params = new Bundle();
		// params.putString("method", "fql.query");
		// params.putString("query", query);
		// String response = facebook.request(null, params);
		// try end
		// location
		parameters.putString("fields", "name, picture");
		// Utility.mAsyncRunner.request("me/friends", params,
		// new FriendsRequestListener());
		String response = facebook.request("me/friends", parameters);
		System.out.println("response" + response);
		return response;

	}

	private void SetFBId() {/*
							 * boolean loggedIn =
							 * AppPreferences.getBoolPreference
							 * (WebServiceConstants.IS_ON_FB); if (!loggedIn) {
							 * try {
							 * 
							 * new SetFBId(context,getFBid()).execute(); } catch
							 * (FileNotFoundException e) { // TODO
							 * Auto-generated catch block e.printStackTrace(); }
							 * catch (MalformedURLException e) { // TODO
							 * Auto-generated catch block e.printStackTrace(); }
							 * catch (OAuthException e) { // TODO Auto-generated
							 * catch block e.printStackTrace(); } catch
							 * (IOException e) { // TODO Auto-generated catch
							 * block e.printStackTrace(); } catch (JSONException
							 * e) { // TODO Auto-generated catch block
							 * e.printStackTrace(); }
							 * 
							 * }
							 */
	}

	private final class LoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			SetFBId();
			//Toast.makeText(context, "DialogListener" + facebook.getAccessToken(), Toast.LENGTH_LONG).show();
			Neevimagick.FACEBOOK_ACCESS_TOKEN = facebook.getAccessToken();
			SessionEvents.onLoginSuccess();
			

		}

		public void onFacebookError(FacebookError error) {
			SessionEvents.onLoginError(error.getMessage());
			// System.err.println("@@@@"+error.getMessage());
			// Toast.makeText(context, error.getMessage(),
			// Toast.LENGTH_LONG).show();
		}

		public void onError(DialogError error) {
			SessionEvents.onLoginError(error.getMessage());
			// Toast.makeText(context, error.getMessage(),
			// Toast.LENGTH_LONG).show();
		}

		public void onCancel() {
			SessionEvents.onLoginError("Action Canceled");
			// Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show();
		}
	}

	public class LogoutRequestListener extends BaseRequestListener {
		public void onComplete(String response, final Object state) {
			// callback should be run in the original thread,
			// not the background thread
			mHandler.post(new Runnable() {
				public void run() {
					SessionEvents.onLogoutFinish();
				}
			});
		}
	}

	private class SessionListener implements AuthListener, LogoutListener {

		public void onAuthSucceed() {
			SessionStore.save(facebook, context);
		}

		public void onAuthFail(String error) {
			// Toast.makeText(context, "Auth Failed", Toast.LENGTH_LONG).show();
			// System.out.println("***FACEBOOK: AUTH FAILED:"
			// );
		}

		public void onLogoutBegin() {
		}

		public void onLogoutFinish() {
			SessionStore.clear(context);
		}
	}

	public Facebook getFacebook() {
		return this.facebook;
	}

	/*
	 * Sampe UploadListener Class for posting image on the wall
	 */

	public class SampleUploadListener extends BaseKeyListener implements
			RequestListener {

		public void onComplete(final String response, final Object state) {
			try {
				// process the response here: (executed in background thread)
				Log.d("Facebook-Example", "SampleUploadListenercomplete: "
						+ response.toString());
				JSONObject json = Util.parseJson(response);
				//final String src = json.getString("src");
			
				// then post the processed result back to the UI thread
				// if we do not do this, an runtime exception will be generated
				// e.g. "CalledFromWrongThreadException: Only the original
				// thread that created a view hierarchy can touch its views."

			} catch (JSONException e) {
				Log.e("Facebook-Example",
						"SampleUploadListenerJSON Error in response");
				e.printStackTrace();
			} catch (FacebookError e) {
				Log.e("Facebook-Example",
						"SampleUploadListenerFacebook Error: " + e.getMessage());
				e.printStackTrace();
			}
		}

		public void onFacebookError(FacebookError e, Object state) {
			Log.e("Facebook-Example",
					"SampleUploadListener onfberror: " + e.getMessage());
			e.printStackTrace();
		}

		public Bitmap getInputType(Bitmap img) {
			// TODO Auto-generated method stub
			return img;
		}

		public int getInputType() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void onIOException(IOException e, Object state) {
			Log.e("Facebook-Example",
					"SampleUploadListenerIOEx " + e.getMessage());

		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			Log.e("Facebook-Example",
					"SampleUploadListenerFNFE " + e.getMessage());

		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			Log.e("Facebook-Example",
					"SampleUploadListenerMALError " + e.getMessage());

		}
	}

	/*
	 * End of sample upload listener class
	 */
}
=======
package com.neevtech.imageprocessing.facebook.sdk.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import oauth.signpost.exception.OAuthException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.BaseKeyListener;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.neevtech.imageprocessing.constants.Neevimagick;
import com.neevtech.imageprocessing.facebook.sdk.helper.SessionEvents.AuthListener;
import com.neevtech.imageprocessing.facebook.sdk.helper.SessionEvents.LogoutListener;

public class FacebookConnector {

	private Facebook facebook = null;
	private Context context;
	private String[] permissions;
	private Handler mHandler;
	private Activity activity;
	private SessionListener mSessionListener = new SessionListener();
	private String wallMessage = "";
	public static final int REQUEST_CODE_FB = 66;
	private static final String ERROR_CODE_MAX_FEED_LIMIT = "(#341)";
	private static final String ERROR_DUPLICATE_MESSAGE = "(#506)";
	private static final String ID = "id";
	private static final String MSG_ALREADY_POSTED = "Your Message is already posted on your Wall";
	private static final String MSG_FEED_ACTION_LIMIT_REACHED = "Feed action request limit reached";

	public FacebookConnector(String appId, Activity activity, Context context,
			String[] permissions) {
		this.facebook = new Facebook(appId);

		SessionStore.restore(facebook, context);
		
		//SessionEvents.addAuthListener(mSessionListener);
		 //SessionEvents.addLogoutListener(mSessionListener);

		this.context = context;
		this.permissions = permissions;
		this.mHandler = new Handler();
		this.activity = activity;
	}

	public void addAuthListener(AuthListener authListener) {
		SessionEvents.addAuthListener(authListener);
	}

	public void removeAuthListener(AuthListener authListener) {
		SessionEvents.removeAuthListener(authListener);
	}

	public void addLogoutListener(LogoutListener logoutListener) {
		SessionEvents.addLogoutListener(logoutListener);
	}

	public void removeLogoutListener(LogoutListener logoutListener) {
		SessionEvents.removeLogoutListener(logoutListener);
	}

	public boolean login() {
		if (!facebook.isSessionValid()) {
			// System.out.println("***FB AUthorizing...");
			if (activity != null)
				facebook.authorize(activity, this.permissions, REQUEST_CODE_FB,
						new LoginDialogListener());

			return false;
		} else {
			return true;
		}
	}

	public boolean loginAndPost(String message) {
		wallMessage = message;
		if (!facebook.isSessionValid()) {
			facebook.authorize(activity, permissions, REQUEST_CODE_FB,
					new LoginDialogListener());
		//	Toast.makeText(context, "Token ::: " + facebook.getAccessToken(), Toast.LENGTH_LONG).show();
			return false;
		} else {
			return true;
		}
	}

	public void logout() {
		SessionEvents.onLogoutBegin();
		AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(this.facebook);
		asyncRunner.logout(this.context, new LogoutRequestListener());
	}

	public String getFBid() throws OAuthException, FileNotFoundException,
			MalformedURLException, IOException, JSONException {
		// Bundle parameters = new Bundle();
		//
		//
		// return facebook.request("me", parameters);

		// ... login user here ...
		JSONObject me = new JSONObject(facebook.request("me"));
		return me.getString("id");

	}

	public String getFBName() throws OAuthException, FileNotFoundException,
			MalformedURLException, IOException, JSONException {
		JSONObject me = new JSONObject(facebook.request("me"));
		return me.getString("first_name");

	}

	public void postMessageOnWall(String msg) throws OAuthException,
			FileNotFoundException, MalformedURLException, IOException {

		Bundle parameters = new Bundle();
		parameters.putString("message", msg);

		String response = facebook.request("me/feed", parameters, "POST");
		if (response.contains(ERROR_CODE_MAX_FEED_LIMIT)) {
			Toast.makeText(context, MSG_FEED_ACTION_LIMIT_REACHED,
					Toast.LENGTH_LONG).show();
			//facebook.logout(context);

		} else if (response.contains(ID)) {
			Toast.makeText(context, "test", Toast.LENGTH_LONG).show();
			//facebook.logout(context);

		} else if (response.contains(ERROR_DUPLICATE_MESSAGE)) {
			Toast.makeText(context, MSG_ALREADY_POSTED, Toast.LENGTH_LONG)
					.show();
			//facebook.logout(context);
		}
		System.out.println("response" + response);

	}

	/*
	 * Post Image On Our own wall ...
	 */

	public boolean postImageOnWall(String message, byte[] imgData) {
		Bundle parameters = new Bundle();
		parameters.putString(Facebook.TOKEN, facebook.getAccessToken());
		parameters.putString ("message", message);
		parameters.putByteArray ("source", imgData);
		parameters.putByteArray("picture", imgData);
		this.facebook.setAccessToken(Neevimagick.FACEBOOK_ACCESS_TOKEN);

		AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(this.facebook);
		try {
			Log.e("Runner and the token is ", mAsyncRunner.toString() + ":::" + Neevimagick.FACEBOOK_ACCESS_TOKEN);
			mAsyncRunner.request("me/photos", parameters, "POST",
					new SampleUploadListener(), null);
			return true;
		} catch (Exception e) {
			Log.e("Facebook-Example", "Facebook Error: runner" + e.getMessage());
			return false;
		}
	}

	public void postMessageOnFriendWall(Context activityContext, String msg,
			String friendId) throws OAuthException, FileNotFoundException,
			MalformedURLException, IOException, JSONException {

		Bundle parameters = new Bundle();
		parameters.putString("message", getFBName() + msg);
		// parameters.putString("description", "APPBattler");
		// parameters.putString("caption", "Appbattler");
		String response = facebook.request(friendId + "/feed", parameters,
				"POST");
		// Log.d("FACEBOOK RESPONSE",response);
		// if (response == null || response.equals("") ||
		// response.equals("false")) {
		// Log.v("Error", "Blank response");
		// } else{
		// Toast.makeText(activityContext, "Successfuly Invited",
		// Toast.LENGTH_SHORT).show();
		// }

		/*
		 * Bundle params = new Bundle(); params.putString("to", friendId);
		 * params.putString("caption", "Appbattler");
		 * params.putString("description", "APPBattler");
		 * params.putString("message", msg); // params.putString("picture",
		 * Utility.HACK_ICON_URL); params.putString("name", "test");
		 * 
		 * // String response = facebook.request("feed", params, "POST");
		 * 
		 * facebook.dialog(activityContext, "feed", params, new DialogListener()
		 * {
		 * 
		 * @Override public void onFacebookError(FacebookError e) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onError(DialogError e) { // TODO Auto-generated
		 * method stub
		 * 
		 * }
		 * 
		 * @Override public void onComplete(Bundle values) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onCancel() { // TODO Auto-generated method stub
		 * 
		 * } });
		 */
		/*
		 * if (response.contains(ERROR_CODE_MAX_FEED_LIMIT)) {
		 * Toast.makeText(context, MSG_FEED_ACTION_LIMIT_REACHED,
		 * Toast.LENGTH_LONG).show(); facebook.logout(context);
		 * 
		 * } else if (response.contains(ID)) { Toast.makeText(context,
		 * Constants.FB_SHARE_MSG, Toast.LENGTH_LONG) .show();
		 * facebook.logout(context);
		 * 
		 * } else if (response.contains(ERROR_DUPLICATE_MESSAGE)) {
		 * Toast.makeText(context, MSG_ALREADY_POSTED, Toast.LENGTH_LONG)
		 * .show(); facebook.logout(context); } System.out.println("response" +
		 * response);
		 */

	}

	public String getFbFriends() throws OAuthException, FileNotFoundException,
			MalformedURLException, IOException {

		Bundle parameters = new Bundle();
		// parameters.putString("message", msg);
		// try
		// String query =
		// "SELECT uid,name,birthday_date, hometown_location FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me()))";
		// Bundle params = new Bundle();
		// params.putString("method", "fql.query");
		// params.putString("query", query);
		// String response = facebook.request(null, params);
		// try end
		// location
		parameters.putString("fields", "name, picture");
		// Utility.mAsyncRunner.request("me/friends", params,
		// new FriendsRequestListener());
		String response = facebook.request("me/friends", parameters);
		System.out.println("response" + response);
		return response;

	}

	private void SetFBId() {/*
							 * boolean loggedIn =
							 * AppPreferences.getBoolPreference
							 * (WebServiceConstants.IS_ON_FB); if (!loggedIn) {
							 * try {
							 * 
							 * new SetFBId(context,getFBid()).execute(); } catch
							 * (FileNotFoundException e) { // TODO
							 * Auto-generated catch block e.printStackTrace(); }
							 * catch (MalformedURLException e) { // TODO
							 * Auto-generated catch block e.printStackTrace(); }
							 * catch (OAuthException e) { // TODO Auto-generated
							 * catch block e.printStackTrace(); } catch
							 * (IOException e) { // TODO Auto-generated catch
							 * block e.printStackTrace(); } catch (JSONException
							 * e) { // TODO Auto-generated catch block
							 * e.printStackTrace(); }
							 * 
							 * }
							 */
	}

	private final class LoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			SetFBId();
			//Toast.makeText(context, "DialogListener" + facebook.getAccessToken(), Toast.LENGTH_LONG).show();
			Neevimagick.FACEBOOK_ACCESS_TOKEN = facebook.getAccessToken();
			SessionEvents.onLoginSuccess();
			

		}

		public void onFacebookError(FacebookError error) {
			SessionEvents.onLoginError(error.getMessage());
			// System.err.println("@@@@"+error.getMessage());
			// Toast.makeText(context, error.getMessage(),
			// Toast.LENGTH_LONG).show();
		}

		public void onError(DialogError error) {
			SessionEvents.onLoginError(error.getMessage());
			// Toast.makeText(context, error.getMessage(),
			// Toast.LENGTH_LONG).show();
		}

		public void onCancel() {
			SessionEvents.onLoginError("Action Canceled");
			// Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show();
		}
	}

	public class LogoutRequestListener extends BaseRequestListener {
		public void onComplete(String response, final Object state) {
			// callback should be run in the original thread,
			// not the background thread
			mHandler.post(new Runnable() {
				public void run() {
					SessionEvents.onLogoutFinish();
				}
			});
		}
	}

	private class SessionListener implements AuthListener, LogoutListener {

		public void onAuthSucceed() {
			SessionStore.save(facebook, context);
		}

		public void onAuthFail(String error) {
			// Toast.makeText(context, "Auth Failed", Toast.LENGTH_LONG).show();
			// System.out.println("***FACEBOOK: AUTH FAILED:"
			// );
		}

		public void onLogoutBegin() {
		}

		public void onLogoutFinish() {
			SessionStore.clear(context);
		}
	}

	public Facebook getFacebook() {
		return this.facebook;
	}

	/*
	 * Sampe UploadListener Class for posting image on the wall
	 */

	public class SampleUploadListener extends BaseKeyListener implements
			RequestListener {

		public void onComplete(final String response, final Object state) {
			try {
				// process the response here: (executed in background thread)
				Log.d("Facebook-Example", "SampleUploadListenercomplete: "
						+ response.toString());
				JSONObject json = Util.parseJson(response);
				//final String src = json.getString("src");
			
				// then post the processed result back to the UI thread
				// if we do not do this, an runtime exception will be generated
				// e.g. "CalledFromWrongThreadException: Only the original
				// thread that created a view hierarchy can touch its views."

			} catch (JSONException e) {
				Log.e("Facebook-Example",
						"SampleUploadListenerJSON Error in response");
				e.printStackTrace();
			} catch (FacebookError e) {
				Log.e("Facebook-Example",
						"SampleUploadListenerFacebook Error: " + e.getMessage());
				e.printStackTrace();
			}
		}

		public void onFacebookError(FacebookError e, Object state) {
			Log.e("Facebook-Example",
					"SampleUploadListener onfberror: " + e.getMessage());
			e.printStackTrace();
		}

		public Bitmap getInputType(Bitmap img) {
			// TODO Auto-generated method stub
			return img;
		}

		public int getInputType() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void onIOException(IOException e, Object state) {
			Log.e("Facebook-Example",
					"SampleUploadListenerIOEx " + e.getMessage());

		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			Log.e("Facebook-Example",
					"SampleUploadListenerFNFE " + e.getMessage());

		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			Log.e("Facebook-Example",
					"SampleUploadListenerMALError " + e.getMessage());

		}
	}

	/*
	 * End of sample upload listener class
	 */
}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
