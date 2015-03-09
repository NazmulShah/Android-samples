package com.neevtech.imageprocessing.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * @author Shruti
 *
 */
public class MemCacheManager {

	//
	private static LruCache<String, Bitmap> mMemoryCache;

	public static void initializeCache() {
		final int cacheSize = 2 * 1024 * 1024;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
		};
	}

	/**
	 * @param key
	 * @param bitmap
	 */
	public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) != null) {
			mMemoryCache.remove(key);
		}
		mMemoryCache.put(key, bitmap);
	}

	public static Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}

	public static void removeBitmapFromCache(String key) {
		if (mMemoryCache.get(key) != null)
			mMemoryCache.remove(key);
	}
}
