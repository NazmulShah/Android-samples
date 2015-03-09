/**
 * 
 */
package com.example.tagmyvideo.ui.widget;

import java.io.File;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tagmyvideo.R;
import com.example.tagmyvideo.constants.VideoConstants;
import com.example.tagmyvideo.utils.MediaExplorer;

/**
 * @author Ashish R Agre
 * 
 */
public class FileListAdapter extends ArrayAdapter<File> {
	private Context context;
	private List<File> fileList;
	private int resource;
	private MediaExplorer mediaExplorer;

	public FileListAdapter(Context context, int resource, List<File> fileList) {
		super(context, resource, fileList);
		this.context = context;
		this.fileList = fileList;
		this.resource = resource;
		Log.d("FilesListCount", "" + fileList.size());
		mediaExplorer = new MediaExplorer();
	}

	/*
	 * ArrayAdapter<T> class overridden methods are as follows
	 */

	@Override
	public int getCount() {
		return fileList.size();
	}

	@Override
	public File getItem(int position) {
		return fileList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGp) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View tempView = inflater.inflate(resource, viewGp, false);
		final File file = fileList.get(position);
		/*
		 * Resource Initialization
		 */
		ImageView folderFileImage = (ImageView) tempView
				.findViewById(R.id.fileImage);
		TextView folderNameText = (TextView) tempView
				.findViewById(R.id.folderName);
		final TextView totalFolders = (TextView) tempView
				.findViewById(R.id.nestedFolders);
		final TextView videoCount = (TextView) tempView
				.findViewById(R.id.videoCount);
		final TextView type = (TextView) tempView.findViewById(R.id.type);

		if (file.isDirectory()) {
			type.setText(VideoConstants.FOLDER);
			TextView typeDesc = (TextView) tempView.findViewById(R.id.typeDesc);
			typeDesc.setText(file.getAbsolutePath());

			class AsyncDirectoryList extends
					AsyncTask<TextView, String, String> {
				private List<File> tempFileList;
				private List<File> tempVideoList;
				private ProgressDialog dialog = new ProgressDialog(context);

				@Override
				protected void onPreExecute() {

					/*
					 * if (!dialog.isShowing()) {
					 * dialog.setMessage("Fetching directory...");
					 * dialog.show(); }
					 */
				}

				@Override
				protected String doInBackground(TextView... params) {
					tempFileList = mediaExplorer.getDirectoryList(file);
					tempVideoList = mediaExplorer.getVideoFiles();
					if (tempFileList != null && tempVideoList != null) {
						return VideoConstants.FOLDERS_AND_VIDEOS;
					} else if (tempFileList == null && tempVideoList != null) {
						return VideoConstants.VIDEOS_ONLY;
					} else if (tempFileList != null && tempVideoList == null) {
						return VideoConstants.FOLDERS_ONLY;
					} else if (tempFileList == null && tempVideoList == null) {
						return VideoConstants.FOLDERS_NOR_VIDEOS;
					} else {
						return null;
					}
				}

				@Override
				protected void onPostExecute(String result) {
					if (result.equals(VideoConstants.FOLDERS_AND_VIDEOS)) {
						totalFolders.setText(Integer.toString(tempFileList
								.size()) + " Folder");
						videoCount.setText(Integer.toString(tempVideoList
								.size()) + " Video");
					} else if (result.equals(VideoConstants.FOLDERS_ONLY)) {
						totalFolders.setText(Integer.toString(tempFileList
								.size()) + " Folder");
						videoCount.setText("");
						videoCount.setVisibility(View.INVISIBLE);

					} else if (result.equals(VideoConstants.VIDEOS_ONLY)) {
						totalFolders.setText("");
						totalFolders.setVisibility(View.INVISIBLE);
						videoCount.setText(Integer.toString(tempVideoList
								.size()) + " Video");
					} else if (result.equals(VideoConstants.FOLDERS_NOR_VIDEOS)) {
						totalFolders.setText("");
						videoCount.setText("");
						videoCount.setVisibility(View.INVISIBLE);
						totalFolders.setVisibility(View.INVISIBLE);
					}
					dialog.cancel();
					dialog.dismiss();

				}
			}

			folderFileImage.setImageResource(R.drawable.folder_music);

			folderNameText.setText(file.getName());
			/*
			 * totalFolders.setText(Integer.toString(tempFileList.size()) +
			 * " Folders");
			 * videoCount.setText(Integer.toString(tempVideoList.size()) +
			 * "Videos");
			 */
			AsyncDirectoryList asyncDirectoryList = new AsyncDirectoryList();
			asyncDirectoryList.execute(totalFolders, videoCount);
		} else {
			folderFileImage.setImageResource(R.drawable.video_file);
			folderNameText.setText(file.getName());
			type.setText(VideoConstants.FILE);
			totalFolders.setText(file.getAbsolutePath());
			totalFolders.setVisibility(View.INVISIBLE);
			videoCount.setText("");
		}

		;
		return tempView;
	}

}
