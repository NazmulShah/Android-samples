<<<<<<< HEAD
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tagmyvideo.R;
import com.example.tagmyvideo.db.model.Video;

/**
 * @author Ashish R Agre
 * 
 */
public class TagMyVideoList extends ListFragment {
	private Context context;
	private int resource;
	private List<Video> vList;

	public TagMyVideoList(Context context, int resource, List<Video> vList) {
		this.context = context;
		this.resource = resource;
		this.vList = vList;
	}

	public TagMyVideoList() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		return inflater.inflate(resource, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(setUpListAdapter());

	}

	private ArrayAdapter<Video> setUpListAdapter() {
		ArrayAdapter<Video> adapter = new TagVideoListAdapter(context,
				R.layout.tagged_video_lay_data, vList);
		return adapter;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}

}
=======
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tagmyvideo.R;
import com.example.tagmyvideo.db.model.Video;

/**
 * @author Ashish R Agre
 * 
 */
public class TagMyVideoList extends ListFragment {
	private Context context;
	private int resource;
	private List<Video> vList;

	public TagMyVideoList(Context context, int resource, List<Video> vList) {
		this.context = context;
		this.resource = resource;
		this.vList = vList;
	}

	public TagMyVideoList() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		return inflater.inflate(resource, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(setUpListAdapter());

	}

	private ArrayAdapter<Video> setUpListAdapter() {
		ArrayAdapter<Video> adapter = new TagVideoListAdapter(context,
				R.layout.tagged_video_lay_data, vList);
		return adapter;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}

}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
