<<<<<<< HEAD
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tagmyvideo.R;
import com.example.tagmyvideo.db.model.Video;

/**
 * @author Ashish R Agre
 * 
 */
public class VideoGalleryGridAdapter extends ArrayAdapter<Video> {
	private Context context;
	private List<Video> thumbnails;
	private int resource;
	private Video tempVideo;

	public VideoGalleryGridAdapter(Context context, int resource,
			List<Video> thumbnails) {
		super(context, resource, thumbnails);
		this.context = context;
		this.thumbnails = thumbnails;
		this.resource = resource;
		Log.d("Video List Size ", "VS Gallery " + thumbnails.size());
	}

	@Override
	public int getCount() {
		return thumbnails.size();
	}

	@Override
	public Video getItem(int arg0) {
		return this.thumbnails.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGp) {
		tempVideo = this.thumbnails.get(position);
		if (view == null) {
			LayoutInflater li = LayoutInflater.from(context);
			View rowView = li.inflate(this.resource, viewGp, false);
			view = rowView;
		}
		populateList(view);
		return view;
	}

	private void populateList(View rowView) {
		ImageView videoThumbnail = (ImageView) rowView
				.findViewById(R.id.videoThumbnail);
		TextView videoTitle = (TextView) rowView.findViewById(R.id.videoTitle);
		Bitmap b = BitmapFactory.decodeByteArray(tempVideo.getThumbData(), 0,
				tempVideo.getThumbData().length);
		videoThumbnail.setImageBitmap(b);
		Log.d("Bitmap Byte value ", "VS Gallery "
				+ tempVideo.getThumbData().length);
		/*
		 * TextView videoTitle = (TextView) rowView
		 * .findViewById(R.id.videoTitle); if (tempVideo.getPath() != null &&
		 * tempVideo.getPath() != "") { videoTitle.setText(tempVideo.getPath());
		 * }
		 */
		if (tempVideo.getFileName() != null && tempVideo.getFileName() != ""
				&& !tempVideo.getFileName().equals("empty")) {
			videoTitle.setText(tempVideo.getFileName());
		}
	}

=======
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tagmyvideo.R;
import com.example.tagmyvideo.db.model.Video;

/**
 * @author Ashish R Agre
 * 
 */
public class VideoGalleryGridAdapter extends ArrayAdapter<Video> {
	private Context context;
	private List<Video> thumbnails;
	private int resource;
	private Video tempVideo;

	public VideoGalleryGridAdapter(Context context, int resource,
			List<Video> thumbnails) {
		super(context, resource, thumbnails);
		this.context = context;
		this.thumbnails = thumbnails;
		this.resource = resource;
		Log.d("Video List Size ", "VS Gallery " + thumbnails.size());
	}

	@Override
	public int getCount() {
		return thumbnails.size();
	}

	@Override
	public Video getItem(int arg0) {
		return this.thumbnails.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGp) {
		tempVideo = this.thumbnails.get(position);
		if (view == null) {
			LayoutInflater li = LayoutInflater.from(context);
			View rowView = li.inflate(this.resource, viewGp, false);
			view = rowView;
		}
		populateList(view);
		return view;
	}

	private void populateList(View rowView) {
		ImageView videoThumbnail = (ImageView) rowView
				.findViewById(R.id.videoThumbnail);
		TextView videoTitle = (TextView) rowView.findViewById(R.id.videoTitle);
		Bitmap b = BitmapFactory.decodeByteArray(tempVideo.getThumbData(), 0,
				tempVideo.getThumbData().length);
		videoThumbnail.setImageBitmap(b);
		Log.d("Bitmap Byte value ", "VS Gallery "
				+ tempVideo.getThumbData().length);
		/*
		 * TextView videoTitle = (TextView) rowView
		 * .findViewById(R.id.videoTitle); if (tempVideo.getPath() != null &&
		 * tempVideo.getPath() != "") { videoTitle.setText(tempVideo.getPath());
		 * }
		 */
		if (tempVideo.getFileName() != null && tempVideo.getFileName() != ""
				&& !tempVideo.getFileName().equals("empty")) {
			videoTitle.setText(tempVideo.getFileName());
		}
	}

>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
}