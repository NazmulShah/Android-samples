/**
 * 
 */
package com.example.tagmyvideo;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tagmyvideo.db.dao.VideoDAO;
import com.example.tagmyvideo.db.dao.impl.VideoDAOImpl;
import com.example.tagmyvideo.db.model.Video;
import com.example.tagmyvideo.utils.TagMyVideoList;

/**
 * @author Ashish R Agre
 * 
 */
public class BrowseTaggedVideoActivity extends FragmentActivity {
	private ListView taggedVideoList;
	private TextView information;
	private OnItemClickListener listener;
	private VideoDAO videoDAO;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private FrameLayout layout;
	private List<Video> vList;
	private Fragment myVideoList;

	@Override
	protected void onCreate(Bundle savedBundle) {
		super.onCreate(savedBundle);
		//TODO : make fullscreen and No-Titlebar in Manifest file		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tagged_videos_layout);
		initComponents();
		setupView();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private void initComponents() {
		// taggedVideoList = (ListView) findViewById(R.id.taggedVideosList);
		taggedVideoList = new ListView(this);
		layout = (FrameLayout) findViewById(R.id.fragmentBookmark);
		fragmentManager = getSupportFragmentManager();
	}

	private boolean verifyVideoList() {
		videoDAO = new VideoDAOImpl(this);
		videoDAO.open();
		List<Video> vL = videoDAO.getAllVideos();
		for (Video v : vL) {
			Log.d("Video name ", v.getFileName());
		}
		vList = vL;
		videoDAO.close();
		return vList.size() > 0;
	}

	private void setupView() {
		fragmentTransaction = fragmentManager.beginTransaction();
		if (verifyVideoList()) {
			myVideoList = new TagMyVideoList(this, R.layout.tag_my_video_list,
					vList);
			fragmentTransaction.replace(R.id.fragmentBookmark, myVideoList);
		} else {
			TextView noData = new TextView(this);
			noData.setText("No Videos found here.");
			noData.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			layout.addView(noData);
			Log.d("BrowseTaggedVideoActivity", "!!" + "list verification fails");
		}
		fragmentTransaction.commit();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Log.d("Came", "Here");
			return true;
		} else {
			Log.d("Came", "Here....");
			return super.onKeyDown(keyCode, event);
		}

	}

}
