/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.tagmyvideo.constants.VideoConstants;

/**
 * @author Ashish R Agre
 * 
 */
public class MediaExplorer {
	private List<File> videoFiles;
	private List<File> filesList;

	public MediaExplorer() {

	}

	public List<File> getDirectoryList(File file) {
		String files[] = null;
		if (file != null) {
			files = file.list();
			if (files.length > 0) {
				Log.d("Folder Not empty", "!!Good folder" + file.getName());
				videoFiles = new ArrayList<File>();
				filesList = new ArrayList<File>();
				for (String temp : files) {
					File tempFile = new File(file.getAbsolutePath() + "/"
							+ temp);
					if (tempFile.isDirectory() && !tempFile.isHidden()) {
						filesList.add(tempFile);
					} else if (tempFile.isFile()) {
						boolean isVideoFile = isExtensionAvailable(tempFile
								.getAbsolutePath());
						if (isVideoFile) {
							videoFiles.add(tempFile);
							// filesList.add(tempFile);
						}
					}
				}
				if (filesList.size() > 0) {
					return filesList;
				} else {
					return null;
				}
			} else {
				Log.d("Folder is empty", "!!bad folder");
				return null;
			}
		}
		return null;
	}

	private boolean isExtensionAvailable(String filepath) {
		int index = filepath.lastIndexOf("/");
		boolean isVideoFile = false;
		for (int indexExt = 0; indexExt < VideoConstants.VIDEO_EXTENSIONS.length; indexExt++) {
			isVideoFile = filepath.substring(index, filepath.length())
					.endsWith(VideoConstants.VIDEO_EXTENSIONS[indexExt]);
			if (isVideoFile)
				break;
		}
		return isVideoFile;
	}

	public List<File> getVideoFiles() {
		if (videoFiles != null) {
			if (videoFiles.size() > 0) {
				return this.videoFiles;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
