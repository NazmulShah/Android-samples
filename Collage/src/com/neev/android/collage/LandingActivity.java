package com.neev.android.collage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.neev.android.collage.util.BackgroundImageAdapter;

public class LandingActivity extends Activity implements OnClickListener{

	
	private ImageView newImageView;

	private ImageView zoomIn, zoomOut;
	
	private BackgroundImageAdapter backgroundImageAdapter;
	private Gallery gallery;
	private Button AddImageButtonView;
	RelativeLayout layout;
	private List<ImageView> images;
	View view;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_landing);

		initilizations();
		
	}
	
	private void initilizations(){
		
		
		AddImageButtonView = (Button)findViewById(R.id.AddImage);
		AddImageButtonView.setOnClickListener(this);

		zoomIn = (ImageView)findViewById(R.id.zoomIn);
		zoomOut = (ImageView)findViewById(R.id.zoomOut);
		
		zoomIn.setOnClickListener(this);
		zoomOut.setOnClickListener(this);
		
		layout = (RelativeLayout)findViewById(R.id.CollageFrameLayout);
		layout.setBackgroundResource(BackgroundImageAdapter.currentBackground);
			
		
		gallery = (Gallery) findViewById(R.id.gallery);
		backgroundImageAdapter = new BackgroundImageAdapter(this);
		gallery.setAdapter(backgroundImageAdapter);
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View v,
					int position, long id) {
				BackgroundImageAdapter.currentBackground = backgroundImageAdapter.images_ids[position];
				layout.setBackgroundResource(BackgroundImageAdapter.currentBackground);
				
			}

		});
		
//		images.set(location, object)
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_landing, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		if(v== zoomIn)
		{
			System.out.println(" Clicked on ZoomIn");
		}
		else if(v== zoomOut)
		{
			System.out.println(" Clicked on ZoomOut");
		}
		else if(v== AddImageButtonView)
		{
			System.out.println(" Clicked on AddButtonView");
		}
		
		
	}
	public void AddImage(View v){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 1);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


		Uri _uri = data.getData();

		if (_uri != null) {
			
			layout.addView(newImageView);
			
		}
	}
	
	
}
