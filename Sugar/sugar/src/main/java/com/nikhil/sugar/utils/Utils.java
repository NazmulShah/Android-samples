package com.peelworks.hul.me.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by exort on 18/3/15.
 */
public class Utils {
  public static String getCurrentTimeDate() {
    Date date = new Date();
    Calendar calDate = Calendar.getInstance();
    calDate.setTime(date);
    long currentDate = calDate.getTimeInMillis();
    Date parsedDate = new Date(currentDate + 19800000);
    SimpleDateFormat destFormat = new SimpleDateFormat("HH:mm, dd MMM");
    destFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

    return destFormat.format(parsedDate);
  }

  public static String getDayMonth()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return sdf.format(new Date());
  }

  public static boolean isConnectedToInternet(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    //DivaLog.i(TAG, "isConnectedToInternet : " + connected);
    return connectivityManager.getActiveNetworkInfo() != null && connectivityManager
        .getActiveNetworkInfo().isConnectedOrConnecting();

  }
}
