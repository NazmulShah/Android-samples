<<<<<<< HEAD
package com.neevtech.imageprocessing.twitter.sdk;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.neevtech.imageprocessing.TwitterShareActivity;

/**
 * Prepares a OAuthConsumer and OAuthProvider
 * 
 * OAuthConsumer is configured with the consumer key & consumer secret.
 * OAuthProvider is configured with the 3 OAuth endpoints.
 * 
 * Execute the OAuthRequestTokenTask to retrieve the request, and authorize the
 * request.
 * 
 * After the request is authorized, a callback is made here.
 * 
 */
public class PrepareRequestTokenActivity extends Activity {

	final String TAG = getClass().getName();

	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private boolean authComplete = false;
	private int authCount = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		try {
			this.consumer = new CommonsHttpOAuthConsumer(
					Constants.TWITTER_CONSUMER_KEY,
					Constants.TWITTER_CONSUMER_SECRET);
			this.provider = new CommonsHttpOAuthProvider(Constants.REQUEST_URL,
					Constants.ACCESS_URL, Constants.AUTHORIZE_URL);
		} catch (Exception e) {
			Log.e(TAG, "Error creating consumer / provider", e);
		}

		Log.i(TAG, "Starting task to retrieve request token.");
		new OAuthRequestTokenTask(this, consumer, provider).execute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (authCount > 0 && !authComplete) {
			finish();
		}
		authCount++;
	}

	/**
	 * Called when the OAuthRequestTokenTask finishes (user has authorized the
	 * request token). The callback URL will be intercepted here.
	 */
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if (uri != null
				&& uri.getScheme().equals(Constants.OAUTH_CALLBACK_SCHEME)) {
			Log.i(TAG, "Callback received : " + uri);
			Log.i(TAG, "Retrieving Access Token");
			new RetrieveAccessTokenTask(this, consumer, provider, prefs)
					.execute(uri);
			authComplete = true;			
		}
	}

	public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

		private Context context;
		private OAuthProvider provider;
		private OAuthConsumer consumer;
		private SharedPreferences prefs;

		public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,
				OAuthProvider provider, SharedPreferences prefs) {
			this.context = context;
			this.consumer = consumer;
			this.provider = provider;
			this.prefs = prefs;
		}

		/**
		 * Retrieve the oauth_verifier, and store the oauth and
		 * oauth_token_secret for future API calls.
		 */
		@Override
		protected Void doInBackground(Uri... params) {
			final Uri uri = params[0];
			final String oauth_verifier = uri
					.getQueryParameter(OAuth.OAUTH_VERIFIER);

			try {
				provider.retrieveAccessToken(consumer, oauth_verifier);

				final Editor edit = prefs.edit();
				edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
				edit.putString(OAuth.OAUTH_TOKEN_SECRET,
						consumer.getTokenSecret());
				edit.commit();

				String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
				String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

				consumer.setTokenWithSecret(token, secret);
				// context.startActivity(new
				// Intent(context,AndroidTwitterSample.class));

				Log.i(TAG, "OAuth - Access Token Retrieved");
				executeAfterAccessTokenRetrieval(context);				

			} catch (Exception e) {
				Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
			}

			return null;
		}

		private void executeAfterAccessTokenRetrieval(Context context) {
			String msg = getIntent().getExtras().getString(Constants.REQUEST_PARAM);
			byte[] twitPic = getIntent().getByteArrayExtra("ImageByteData");			
			Log.e("After getting token from the Server", "Token from the server");
			Log.e("MSG", msg);
			Log.e("byte[] data", "" + twitPic.length);
			Intent shareIntent = new Intent(PrepareRequestTokenActivity.this, TwitterShareActivity.class);
			shareIntent.putExtra("message", msg);
			shareIntent.putExtra("imageByteArray", twitPic);
			startActivity(shareIntent);
			finish();
		}
	}
	
	

}
=======
package com.neevtech.imageprocessing.twitter.sdk;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.neevtech.imageprocessing.TwitterShareActivity;

/**
 * Prepares a OAuthConsumer and OAuthProvider
 * 
 * OAuthConsumer is configured with the consumer key & consumer secret.
 * OAuthProvider is configured with the 3 OAuth endpoints.
 * 
 * Execute the OAuthRequestTokenTask to retrieve the request, and authorize the
 * request.
 * 
 * After the request is authorized, a callback is made here.
 * 
 */
public class PrepareRequestTokenActivity extends Activity {

	final String TAG = getClass().getName();

	private OAuthConsumer consumer;
	private OAuthProvider provider;
	private boolean authComplete = false;
	private int authCount = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		try {
			this.consumer = new CommonsHttpOAuthConsumer(
					Constants.TWITTER_CONSUMER_KEY,
					Constants.TWITTER_CONSUMER_SECRET);
			this.provider = new CommonsHttpOAuthProvider(Constants.REQUEST_URL,
					Constants.ACCESS_URL, Constants.AUTHORIZE_URL);
		} catch (Exception e) {
			Log.e(TAG, "Error creating consumer / provider", e);
		}

		Log.i(TAG, "Starting task to retrieve request token.");
		new OAuthRequestTokenTask(this, consumer, provider).execute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (authCount > 0 && !authComplete) {
			finish();
		}
		authCount++;
	}

	/**
	 * Called when the OAuthRequestTokenTask finishes (user has authorized the
	 * request token). The callback URL will be intercepted here.
	 */
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if (uri != null
				&& uri.getScheme().equals(Constants.OAUTH_CALLBACK_SCHEME)) {
			Log.i(TAG, "Callback received : " + uri);
			Log.i(TAG, "Retrieving Access Token");
			new RetrieveAccessTokenTask(this, consumer, provider, prefs)
					.execute(uri);
			authComplete = true;			
		}
	}

	public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

		private Context context;
		private OAuthProvider provider;
		private OAuthConsumer consumer;
		private SharedPreferences prefs;

		public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,
				OAuthProvider provider, SharedPreferences prefs) {
			this.context = context;
			this.consumer = consumer;
			this.provider = provider;
			this.prefs = prefs;
		}

		/**
		 * Retrieve the oauth_verifier, and store the oauth and
		 * oauth_token_secret for future API calls.
		 */
		@Override
		protected Void doInBackground(Uri... params) {
			final Uri uri = params[0];
			final String oauth_verifier = uri
					.getQueryParameter(OAuth.OAUTH_VERIFIER);

			try {
				provider.retrieveAccessToken(consumer, oauth_verifier);

				final Editor edit = prefs.edit();
				edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
				edit.putString(OAuth.OAUTH_TOKEN_SECRET,
						consumer.getTokenSecret());
				edit.commit();

				String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
				String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

				consumer.setTokenWithSecret(token, secret);
				// context.startActivity(new
				// Intent(context,AndroidTwitterSample.class));

				Log.i(TAG, "OAuth - Access Token Retrieved");
				executeAfterAccessTokenRetrieval(context);				

			} catch (Exception e) {
				Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
			}

			return null;
		}

		private void executeAfterAccessTokenRetrieval(Context context) {
			String msg = getIntent().getExtras().getString(Constants.REQUEST_PARAM);
			byte[] twitPic = getIntent().getByteArrayExtra("ImageByteData");			
			Log.e("After getting token from the Server", "Token from the server");
			Log.e("MSG", msg);
			Log.e("byte[] data", "" + twitPic.length);
			Intent shareIntent = new Intent(PrepareRequestTokenActivity.this, TwitterShareActivity.class);
			shareIntent.putExtra("message", msg);
			shareIntent.putExtra("imageByteArray", twitPic);
			startActivity(shareIntent);
			finish();
		}
	}
	
	

}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
