/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class TagVideoListAdapter extends ArrayAdapter<Video> {
	private Context context;
	private int resource;
	private List<Video> taggedVideos;
	private Video video;

	public TagVideoListAdapter(Context context, int resource,
			List<Video> taggedVideos) {
		super(context, resource, taggedVideos);
		this.context = context;
		this.resource = resource;
		this.taggedVideos = taggedVideos;
	}

	@Override
	public int getCount() {
		return taggedVideos.size();
	}

	@Override
	public Video getItem(int position) {
		return taggedVideos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(resource, parent, false);
		video = taggedVideos.get(position);
		ImageView imageView = (ImageView) rowView
				.findViewById(R.id.taggedVideoThumbnail);
		if (video.getThumbData() != null) {
			Bitmap img = BitmapFactory.decodeByteArray(video.getThumbData(), 0,
					video.getThumbData().length);
			imageView.setImageBitmap(img);
			img = null;
		} else {
			imageView.setImageResource(R.drawable.no_preview_avail);
		}
		TextView taggedVideoTitle = (TextView) rowView
				.findViewById(R.id.taggedVideoDesc);
		if (video.getFileName() != null) {
			taggedVideoTitle.setText(video.getFileName());
		}
		TextView listOfBmks = (TextView) rowView
				.findViewById(R.id.listofBookmarks);
		return rowView;
	}

}
