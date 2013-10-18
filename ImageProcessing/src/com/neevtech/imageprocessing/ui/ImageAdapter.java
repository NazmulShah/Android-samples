package com.neevtech.imageprocessing.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.neevtech.imageprocessing.R;

/**
 * @author Shruti
 * 
 */
public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private int width, height;
	// Keep all Images in array
	public Integer[] mThumbIds = { R.drawable.flowers, R.drawable.flowers1,
			R.drawable.man, R.drawable.man1, R.drawable.lady, R.drawable.lady1, };

	// Constructor
	public ImageAdapter(Context c, int width, int height) {
		mContext = c;
		this.width = width;
		this.height = height;

	}

	@Override
	public int getCount() {
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		return mThumbIds[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(mThumbIds[position]);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		Log.e("Width and height","Width "+width+" Height" +height);
		if (width == 600 && height == 1024) {
			imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
		} else {
			imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
		}
		return imageView;
	}

}
