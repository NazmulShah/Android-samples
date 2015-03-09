package com.example.tagmyvideo.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tagmyvideo.R;
import com.example.tagmyvideo.db.model.Tag;

public class TagListAdapter extends ArrayAdapter<Tag> {

	private Context context;
	private List<Tag> tagList;
	Utilities util;

	public TagListAdapter(Context context, List<Tag> tagList) {
		super(context, R.layout.tag_list, tagList);
		this.context = context;
		this.tagList = tagList;
		util = new Utilities();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Tag currentTag = tagList.get(position);
		View rowView = inflater.inflate(R.layout.tag_list, parent, false);
		TextView textName = (TextView) rowView.findViewById(R.id.name);
		if (currentTag.getCaption() == null || currentTag.getCaption().equals("")) {
			textName.setText("Chapter " + (position + 1) + " : "
					+ util.milliSecondsToTimer(currentTag.getBookmarkTime()));
		} else {
			textName.setText(currentTag.getCaption() + " " + (position + 1)
					+ " : "
					+ util.milliSecondsToTimer(currentTag.getBookmarkTime()));
		}
		TextView hiddenTime = (TextView) rowView
				.findViewById(R.id.hiddentTimeinmillis);
		View editImg = rowView.findViewById(R.id.edit_tag);
		editImg.setTag(position);
		editImg.setOnClickListener((OnClickListener) context);

		View deleteImg = rowView.findViewById(R.id.delete_tag);
		deleteImg.setTag(position);
		deleteImg.setOnClickListener((OnClickListener) context);

		hiddenTime.setText(currentTag.getBookmarkTime().toString());
		return rowView;
	}

}
