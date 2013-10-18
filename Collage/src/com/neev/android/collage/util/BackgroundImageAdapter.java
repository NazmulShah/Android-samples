package com.neev.android.collage.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.neev.android.collage.R;



public class BackgroundImageAdapter extends BaseAdapter {
	public static int currentBackground = R.drawable.bacaground1;
	public Integer images_ids[]  = {
			R.drawable.bacaground1,
			R.drawable.bacaground13,
			R.drawable.bacaground28,
			R.drawable.bacaground9
	};
	private Context context;
	private int itemBackground;
	public BackgroundImageAdapter(Context context) {
		this.context = context;
		TypedArray a = context.obtainStyledAttributes(R.styleable.Gallery1);
		itemBackground = a.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 0);
		a.recycle();
	}

	@Override
	public int getCount() {
		return images_ids.length;
	}

	@Override
	public Object getItem(int arg0) {
		return images_ids[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGp) {
		ImageView imageView;
		if(view == null) {
			imageView = new ImageView(context);
			imageView.setImageResource(images_ids[position]);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(150, 200));
		} else {
			imageView = (ImageView) view;
		}
		imageView.setBackgroundResource(itemBackground);
		return imageView;
	}

}

