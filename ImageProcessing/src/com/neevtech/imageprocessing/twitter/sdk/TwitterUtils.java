<<<<<<< HEAD
package com.neevtech.imageprocessing.twitter.sdk;

import java.io.File;

import oauth.signpost.OAuth;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import twitter4j.media.MediaProvider;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class TwitterUtils extends Activity{
	public static Context con;

	// public static boolean isAuthenticated(SharedPreferences prefs) {
	// String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
	// String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
	//
	// AccessToken a = new AccessToken(token, secret);
	// Twitter twitter = new TwitterFactory().getInstance();
	// twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY,
	// Constants.TWITTER_CONSUMER_SECRET);
	// twitter.setOAuthAccessToken(a);
	// try {
	// twitter.getAccountSettings();
	// } catch (TwitterException e) {
	// e.printStackTrace();
	// return false;
	// }
	// return true;
	// }

	public static boolean sendTweet(SharedPreferences prefs, String msg,
			Context context) {
		con = context;
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

		AccessToken a = new AccessToken(token, secret);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY,
				Constants.TWITTER_CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);
		try {
			twitter.updateStatus(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean shareImage(SharedPreferences prefs, final File file,
			String message, final Activity activity) {
		//con = context;
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

		AccessToken a = new AccessToken(token, secret);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY,
				Constants.TWITTER_CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);

		try {
			String screenName = twitter.getScreenName();			
			Log.e("Screen Name", "Twitter username :: " + screenName);
			Configuration conf = new ConfigurationBuilder()
					.setMediaProviderAPIKey("05ba757c2f8222789eba74dc526ce142")
					.build();
			ImageUploadFactory factory = new ImageUploadFactory(conf);
			System.out.println(conf.getMediaProviderAPIKey());
			final ImageUpload upload = factory.getInstance(
					MediaProvider.TWITPIC, twitter.getAuthorization());
			class TwitterImageUploadTask extends AsyncTask<Void, Void, String> {
				private ProgressDialog progressDialog;

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					progressDialog = new ProgressDialog(activity);
					progressDialog.setTitle("Uploading Image");
					progressDialog.setMessage("Please wait while we share your image on twitter...");
					progressDialog.show();
				}
				@Override
				protected String doInBackground(Void... params) {
					String urlImg;
					try {
						urlImg = upload.upload(file);
						return urlImg;
					} catch (Exception e) {
						e.printStackTrace();
						return "ERROR";
					}
				}
				@Override
				protected void onPostExecute(String result) {
					Log.e("Uploading Image", "Image Uploading :: " + result);
					if(result.startsWith("http")) {
						Log.e("Yipee", "All is well for image");
						progressDialog.cancel();
						progressDialog.dismiss();
					} else if(result.equals("ERROR")) {
						Log.e("Alass", "sab gaya ry....");
						progressDialog.cancel();
						progressDialog.dismiss();
					}
				}

			}
			TwitterImageUploadTask uploadImageOnTwitter = new TwitterImageUploadTask();
			uploadImageOnTwitter.execute();
			//sendTweet(prefs, message, con);
			
		} catch (TwitterException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {
		private Twitter twitter;// = new TwitterFactory().getInstance();

		public RetrieveAccessTokenTask(Twitter twitter2) {
			twitter = twitter2;
		}

		@Override
		protected Void doInBackground(Uri... params) {

			return null;
		}

	}

}
=======
package com.neevtech.imageprocessing.twitter.sdk;

import java.io.File;

import oauth.signpost.OAuth;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import twitter4j.media.MediaProvider;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class TwitterUtils extends Activity{
	public static Context con;

	// public static boolean isAuthenticated(SharedPreferences prefs) {
	// String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
	// String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
	//
	// AccessToken a = new AccessToken(token, secret);
	// Twitter twitter = new TwitterFactory().getInstance();
	// twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY,
	// Constants.TWITTER_CONSUMER_SECRET);
	// twitter.setOAuthAccessToken(a);
	// try {
	// twitter.getAccountSettings();
	// } catch (TwitterException e) {
	// e.printStackTrace();
	// return false;
	// }
	// return true;
	// }

	public static boolean sendTweet(SharedPreferences prefs, String msg,
			Context context) {
		con = context;
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

		AccessToken a = new AccessToken(token, secret);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY,
				Constants.TWITTER_CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);
		try {
			twitter.updateStatus(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean shareImage(SharedPreferences prefs, final File file,
			String message, final Activity activity) {
		//con = context;
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

		AccessToken a = new AccessToken(token, secret);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY,
				Constants.TWITTER_CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);

		try {
			String screenName = twitter.getScreenName();			
			Log.e("Screen Name", "Twitter username :: " + screenName);
			Configuration conf = new ConfigurationBuilder()
					.setMediaProviderAPIKey("05ba757c2f8222789eba74dc526ce142")
					.build();
			ImageUploadFactory factory = new ImageUploadFactory(conf);
			System.out.println(conf.getMediaProviderAPIKey());
			final ImageUpload upload = factory.getInstance(
					MediaProvider.TWITPIC, twitter.getAuthorization());
			class TwitterImageUploadTask extends AsyncTask<Void, Void, String> {
				private ProgressDialog progressDialog;

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					progressDialog = new ProgressDialog(activity);
					progressDialog.setTitle("Uploading Image");
					progressDialog.setMessage("Please wait while we share your image on twitter...");
					progressDialog.show();
				}
				@Override
				protected String doInBackground(Void... params) {
					String urlImg;
					try {
						urlImg = upload.upload(file);
						return urlImg;
					} catch (Exception e) {
						e.printStackTrace();
						return "ERROR";
					}
				}
				@Override
				protected void onPostExecute(String result) {
					Log.e("Uploading Image", "Image Uploading :: " + result);
					if(result.startsWith("http")) {
						Log.e("Yipee", "All is well for image");
						progressDialog.cancel();
						progressDialog.dismiss();
					} else if(result.equals("ERROR")) {
						Log.e("Alass", "sab gaya ry....");
						progressDialog.cancel();
						progressDialog.dismiss();
					}
				}

			}
			TwitterImageUploadTask uploadImageOnTwitter = new TwitterImageUploadTask();
			uploadImageOnTwitter.execute();
			//sendTweet(prefs, message, con);
			
		} catch (TwitterException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {
		private Twitter twitter;// = new TwitterFactory().getInstance();

		public RetrieveAccessTokenTask(Twitter twitter2) {
			twitter = twitter2;
		}

		@Override
		protected Void doInBackground(Uri... params) {

			return null;
		}

	}

}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
