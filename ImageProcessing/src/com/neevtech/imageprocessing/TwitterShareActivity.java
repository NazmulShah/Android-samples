package com.neevtech.imageprocessing;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.neevtech.imageprocessing.twitter.sdk.Constants;
import com.neevtech.imageprocessing.twitter.sdk.PrepareRequestTokenActivity;

public class TwitterShareActivity extends Activity {
	private Context context = this;
	private String screenName;
	private Button button;
	private SharedPreferences sharedPreferences;
	private String text = "I have shared asdadas Image.";
	private Drawable tempDraw;
	private Bitmap bitmap;
	private File twitPic;
	private static final int TWEET_ERROR = 0;
	private Twitter tw;
	byte[] data;
	private Intent receivedIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.twitter_share_activity);
		button = (Button) findViewById(R.id.tweet_button);
		this.sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		/*
		 * Receive the Intent and get the sharing data for the image
		 */
		receivedIntent = getIntent();
		text = receivedIntent.getStringExtra("message");
		data = receivedIntent.getByteArrayExtra("imageByteArray");

		/*
		 * Getting file from the resource folder
		 */

		twitPic = new File(context.getCacheDir(), "twitpic.jpeg");
		// tempDraw = getResources().getDrawable(R.drawable.img);
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, baos);
		try {
			FileOutputStream fos = new FileOutputStream(twitPic);
			data = baos.toByteArray();
			fos.write(data);
			fos.flush();
			fos.close();
			baos.close();
			Toast.makeText(
					context,
					"File Size and contents and text :: " + text + " ::: "
							+ twitPic.length(), Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		new CheckAuthentication(sharedPreferences, TwitterShareActivity.this,
				text).execute();
		/*
		 * button.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { new CheckAuthentication(sharedPreferences,
		 * TwitterShareActivity.this, text).execute();
		 * 
		 * } });
		 */

	}

	public class CheckAuthentication extends AsyncTask<Uri, Void, Boolean> {
		private SharedPreferences prefs;// = new TwitterFactory().getInstance();
		private Activity activity;
		private ProgressDialog progressDialog;
		private String tweet;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(activity);
			progressDialog.setTitle("Authenticating session with twitter");
			progressDialog.setMessage("Please...wait");
			progressDialog.show();
		}

		public CheckAuthentication(SharedPreferences prefs, Activity activity,
				String tweet) {
			this.prefs = prefs;
			this.activity = activity;
			this.tweet = tweet;
		}

		@Override
		protected Boolean doInBackground(Uri... params) {
			String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
			String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
			if (token == "" && secret == "") {
				return false;
			} else {
				return true;
			}

			/*
			 * if (token != null || token != "" || secret != null || secret !=
			 * "") { Log.e("Access Tocken", "Token :: " + token);
			 * Log.e("Access secret Tocken", "Token :: " + secret); return true;
			 * } else if(token == null || token == "" || secret == null ||
			 * secret == ""){ Log.e("Access Tocken null ", "Token :: " + token);
			 * Log.e("Access secret Tocken null ", "Token :: " + secret); return
			 * false; }
			 * 
			 * AccessToken a = new AccessToken(token, secret); Twitter twitter =
			 * new TwitterFactory().getInstance(); tw = twitter;
			 * twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY,
			 * Constants.TWITTER_CONSUMER_SECRET);
			 * twitter.setOAuthAccessToken(a); try {
			 * twitter.getAccountSettings(); screenName =
			 * twitter.getScreenName(); Log.e("Screen Name ",
			 * "inside doInbackground"); } catch (TwitterException e) { //
			 * e.printStackTrace(); Log.e("Exception here ", "" +
			 * e.getMessage()); return false; } return true;
			 */
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if (result) {
				Log.e("onPostResult", "Rsult" + result);
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
					shareImage(prefs, twitPic, text, TwitterShareActivity.this);
				}

			} else {
				Intent getTokenActivity = new Intent(TwitterShareActivity.this,
						PrepareRequestTokenActivity.class);
				getTokenActivity.putExtra(Constants.REQUEST_PARAM, tweet);
				getTokenActivity.putExtra("ImageByteData", data);
				activity.startActivity(getTokenActivity);
			}

		}
	}

	/*
	 * Local method to share image on the Twitter
	 * 
	 * @param pref SharedPreferences
	 * 
	 * @param file File
	 * 
	 * @param message String
	 * 
	 * @param activity Activity
	 */
	public boolean shareImage(final SharedPreferences prefs, final File file,
			String message, final Activity activity) {
		// con = context;
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
					progressDialog
					.setMessage("Please wait while we share your image on twitter...");
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
					if (result.startsWith("http")) {
						Log.e("Yipee", "All is well for image");
						progressDialog.cancel();
						progressDialog.dismiss();
						String tweet = text + " Img :: " + result;
						sendTweet(prefs, tweet);
					} else if (result.equals("ERROR")) {
						Log.e("Alass", "sab gaya ry....");
						progressDialog.cancel();
						progressDialog.dismiss();
					}
				}

			}
			TwitterImageUploadTask uploadImageOnTwitter = new TwitterImageUploadTask();
			uploadImageOnTwitter.execute();
			// sendTweet(prefs, message, con);

		} catch (TwitterException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * Tweet Method
	 */
	public boolean sendTweet(final SharedPreferences prefs, final String msg) {
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

		AccessToken a = new AccessToken(token, secret);
		final Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY,
				Constants.TWITTER_CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);
		try {
			class TweetImagePath extends
			AsyncTask<Void, Void, twitter4j.Status> {
				private ProgressDialog progressDialog;

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					progressDialog = new ProgressDialog(
							TwitterShareActivity.this);
					progressDialog.setTitle("Updating Tweet");
					progressDialog
					.setMessage("Please wait while we tweet about the newly uploaded image...");
					progressDialog.show();
				}

				@Override
				protected twitter4j.Status doInBackground(Void... params) {
					try {
						twitter4j.Status s = twitter.updateStatus(msg);
						return s;
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}

				}

				@Override
				protected void onPostExecute(twitter4j.Status result) {
					if (result != null) {
						Log.e("Image Url Tweeted successfully", "Yipee");
						progressDialog.cancel();
						progressDialog.dismiss();
						Toast.makeText(TwitterShareActivity.this, "Your image has been successfully posted on twitter", Toast.LENGTH_LONG).show();
						Intent i = new Intent(TwitterShareActivity.this,
								PhotoChooserActivity.class);
						startActivity(i);
						finish();
					} else {
						Log.e("There was errortweeeting", "error tweeting");
						showDialog(TWEET_ERROR);
						progressDialog.cancel();
						progressDialog.dismiss();
					}
				}
			}
			TweetImagePath tweet = new TweetImagePath();
			tweet.execute();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TWEET_ERROR:
			AlertDialog.Builder builder;
			builder = new AlertDialog.Builder(TwitterShareActivity.this);
			builder.setCancelable(true);
			builder.setIcon(R.drawable.alert);
			builder.setTitle("Tweet error");
			builder.setMessage("Cannot share the same image on the twitter");
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					removeDialog(TWEET_ERROR);
					Intent i = new Intent(TwitterShareActivity.this,
							PhotoChooserActivity.class);
					startActivity(i);
				}
			});
			AlertDialog tempDlg = builder.create();
			tempDlg.show();
			return tempDlg;
		}
		return null;
	}

}
