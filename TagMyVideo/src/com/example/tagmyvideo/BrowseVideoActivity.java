<<<<<<< HEAD
/**
 * 
 */
package com.example.tagmyvideo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagmyvideo.constants.VideoConstants;
import com.example.tagmyvideo.ui.widget.FileListAdapter;
import com.example.tagmyvideo.utils.MediaExplorer;
import com.example.tagmyvideo.utils.NameComparator;

/**
 * @author Ashish R Agre
 * 
 */
public class BrowseVideoActivity extends Activity {
	private ListView fileListView;
	private FileListAdapter fileListAdapter;
	private List<File> filesList;
	private List<File> videoList;
	private List<File> directoryContentList;
	private File currentRootDir;
	private FolderFileListClickListener listClickListener;
	private static String previouPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.browse_video_activity);

		currentRootDir = Environment.getExternalStorageDirectory();
		previouPath = currentRootDir.getAbsolutePath();

		/*
		 * Initializing of the List view after getting the fileList
		 */

		fileListView = (ListView) findViewById(R.id.browseFolder);

	}

	@Override
	protected void onResume() {
		super.onResume();
		directoryContentList = listDirectory(currentRootDir);
		if (directoryContentList.size() > 0) {
			setFileListAdapter(directoryContentList);
			addListClickListener();
		}
	}

	private List<File> listDirectory(File currentPath) {
		MediaExplorer me = new MediaExplorer();
		filesList = me.getDirectoryList(currentPath);
		videoList = me.getVideoFiles();
		if (videoList != null) {
			Collections.sort(videoList, new NameComparator());
		}
		if (filesList != null) {
			Collections.sort(filesList, new NameComparator());
		}
		List<File> directoryContentList;
		if (videoList != null) {
			directoryContentList = new ArrayList<File>(videoList);
		} else {
			directoryContentList = new ArrayList<File>();
		}
		if (filesList != null) {
			directoryContentList.addAll(filesList);
		}
		return directoryContentList;
	}

	private void setFileListAdapter(List<File> filesList) {
		if (fileListView != null) {
			fileListAdapter = new FileListAdapter(this,
					R.layout.file_list_row_data, directoryContentList);
			fileListView.setAdapter(fileListAdapter);
		}
	}

	private void addListClickListener() {
		if (listClickListener == null) {
			listClickListener = new FolderFileListClickListener();
		}
		fileListView.setOnItemClickListener(listClickListener);
	}

	@Override
	public void onBackPressed() {
		if (!currentRootDir.getAbsolutePath().equals(previouPath)) {
			currentRootDir = currentRootDir.getParentFile();
			directoryContentList = listDirectory(currentRootDir);
			if (directoryContentList.size() > 0) {
				setFileListAdapter(directoryContentList);
				addListClickListener();
			}
		}
	}

	private class FolderFileListClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long id) {
			RelativeLayout rl = (RelativeLayout) view;
			TextView type = (TextView) rl.findViewById(R.id.type);
			/*
			 * Toast.makeText(BrowseVideoActivity.this, "List Is clicked (" +
			 * videoUrl.getText() + ")" + position, Toast.LENGTH_SHORT).show();
			 */
			if (type.getText().equals(VideoConstants.FILE)) {
				Toast.makeText(BrowseVideoActivity.this, "File",
						Toast.LENGTH_SHORT).show();
			} else if (type.getText().equals(VideoConstants.FOLDER)) {
				TextView folderView = (TextView) rl.findViewById(R.id.typeDesc);
				Toast.makeText(BrowseVideoActivity.this,
						"Folder :: " + folderView.getText(), Toast.LENGTH_SHORT)
						.show();
				directoryContentList = null;
				currentRootDir = new File(folderView.getText().toString());
				directoryContentList = listDirectory(currentRootDir);
				if (directoryContentList.size() > 0) {
					setFileListAdapter(directoryContentList);
					addListClickListener();
				}
			}

		}
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
=======
/**
 * 
 */
package com.example.tagmyvideo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagmyvideo.constants.VideoConstants;
import com.example.tagmyvideo.ui.widget.FileListAdapter;
import com.example.tagmyvideo.utils.MediaExplorer;
import com.example.tagmyvideo.utils.NameComparator;

/**
 * @author Ashish R Agre
 * 
 */
public class BrowseVideoActivity extends Activity {
	private ListView fileListView;
	private FileListAdapter fileListAdapter;
	private List<File> filesList;
	private List<File> videoList;
	private List<File> directoryContentList;
	private File currentRootDir;
	private FolderFileListClickListener listClickListener;
	private static String previouPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.browse_video_activity);

		currentRootDir = Environment.getExternalStorageDirectory();
		previouPath = currentRootDir.getAbsolutePath();

		/*
		 * Initializing of the List view after getting the fileList
		 */

		fileListView = (ListView) findViewById(R.id.browseFolder);

	}

	@Override
	protected void onResume() {
		super.onResume();
		directoryContentList = listDirectory(currentRootDir);
		if (directoryContentList.size() > 0) {
			setFileListAdapter(directoryContentList);
			addListClickListener();
		}
	}

	private List<File> listDirectory(File currentPath) {
		MediaExplorer me = new MediaExplorer();
		filesList = me.getDirectoryList(currentPath);
		videoList = me.getVideoFiles();
		if (videoList != null) {
			Collections.sort(videoList, new NameComparator());
		}
		if (filesList != null) {
			Collections.sort(filesList, new NameComparator());
		}
		List<File> directoryContentList;
		if (videoList != null) {
			directoryContentList = new ArrayList<File>(videoList);
		} else {
			directoryContentList = new ArrayList<File>();
		}
		if (filesList != null) {
			directoryContentList.addAll(filesList);
		}
		return directoryContentList;
	}

	private void setFileListAdapter(List<File> filesList) {
		if (fileListView != null) {
			fileListAdapter = new FileListAdapter(this,
					R.layout.file_list_row_data, directoryContentList);
			fileListView.setAdapter(fileListAdapter);
		}
	}

	private void addListClickListener() {
		if (listClickListener == null) {
			listClickListener = new FolderFileListClickListener();
		}
		fileListView.setOnItemClickListener(listClickListener);
	}

	@Override
	public void onBackPressed() {
		if (!currentRootDir.getAbsolutePath().equals(previouPath)) {
			currentRootDir = currentRootDir.getParentFile();
			directoryContentList = listDirectory(currentRootDir);
			if (directoryContentList.size() > 0) {
				setFileListAdapter(directoryContentList);
				addListClickListener();
			}
		}
	}

	private class FolderFileListClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long id) {
			RelativeLayout rl = (RelativeLayout) view;
			TextView type = (TextView) rl.findViewById(R.id.type);
			/*
			 * Toast.makeText(BrowseVideoActivity.this, "List Is clicked (" +
			 * videoUrl.getText() + ")" + position, Toast.LENGTH_SHORT).show();
			 */
			if (type.getText().equals(VideoConstants.FILE)) {
				Toast.makeText(BrowseVideoActivity.this, "File",
						Toast.LENGTH_SHORT).show();
			} else if (type.getText().equals(VideoConstants.FOLDER)) {
				TextView folderView = (TextView) rl.findViewById(R.id.typeDesc);
				Toast.makeText(BrowseVideoActivity.this,
						"Folder :: " + folderView.getText(), Toast.LENGTH_SHORT)
						.show();
				directoryContentList = null;
				currentRootDir = new File(folderView.getText().toString());
				directoryContentList = listDirectory(currentRootDir);
				if (directoryContentList.size() > 0) {
					setFileListAdapter(directoryContentList);
					addListClickListener();
				}
			}

		}
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
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
