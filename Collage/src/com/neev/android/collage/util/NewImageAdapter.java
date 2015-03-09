package com.neev.android.collage.util;

import com.neev.android.collage.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class NewImageAdapter extends BaseAdapter{

	Context context;
	public NewImageAdapter(Context context) {
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ImageView imageView;
		if(view == null) {
			imageView = new ImageView(context);
			imageView.setImageResource(R.drawable.bacaground9);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(150, 200));
		} else {
			imageView = (ImageView) view;
		}
		
		return imageView;
	}

}
