package com.peelworks.hul.me.utils;

/**
 * Created by prabhat on 21/10/14.
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

public class VolleySingleton  {

    public static final String TAG = VolleySingleton.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private RequestQueue mRequestQueueCache;
    private static VolleySingleton mInstance;
    private static Context mCtx;

    private VolleySingleton(Context context)
    {
        this.mCtx = context;
    }


    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }
    private HttpStack getHttpStack()
    {
      DefaultHttpClient mDefaultHttpClient = new DefaultHttpClient();
      final ClientConnectionManager
          mClientConnectionManager = mDefaultHttpClient.getConnectionManager();
      final HttpParams mHttpParams = mDefaultHttpClient.getParams();
      final ThreadSafeClientConnManager mThreadSafeClientConnManager = new ThreadSafeClientConnManager( mHttpParams, mClientConnectionManager.getSchemeRegistry() );

      mDefaultHttpClient = new DefaultHttpClient( mThreadSafeClientConnManager, mHttpParams );

      return new HttpClientStack( mDefaultHttpClient );
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(),getHttpStack());
        }

        return mRequestQueue;
    }
    public RequestQueue getRequestQueueWithCache() {
        if (mRequestQueueCache == null) {
            mRequestQueueCache = Volley.newRequestQueue(mCtx.getApplicationContext(),getHttpStack());
        }
        return mRequestQueueCache;
    }

    public <T> void addToRequestQueueWithCache(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueueWithCache().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req,String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(String tag) {

        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
        if(mRequestQueueCache!=null){
            mRequestQueueCache.cancelAll(tag);
        }
    }
}