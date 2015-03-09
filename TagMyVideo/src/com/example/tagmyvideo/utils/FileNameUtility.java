<<<<<<< HEAD
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author Ashish R Agre
 * 
 */
public class FileNameUtility {
	private File file;

	public FileNameUtility(String filePath) throws IOException {
		if (filePath != null && !filePath.equalsIgnoreCase("")) {
			file = new File(filePath);
		}
	}

	public String getFileName() {
		if (file != null) {
			return file.getName();
		} else {
			return null;
		}

	}

	public String getFilePath() {
		if (file != null) {
			String fullFilePath = file.getAbsolutePath();
			String fileName = file.getName();
			String filePath = fullFilePath.substring(0,
					(fullFilePath.length() - fileName.length()));
			return filePath;
		} else {
			return null;
		}
	}

}
=======
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author Ashish R Agre
 * 
 */
public class FileNameUtility {
	private File file;

	public FileNameUtility(String filePath) throws IOException {
		if (filePath != null && !filePath.equalsIgnoreCase("")) {
			file = new File(filePath);
		}
	}

	public String getFileName() {
		if (file != null) {
			return file.getName();
		} else {
			return null;
		}

	}

	public String getFilePath() {
		if (file != null) {
			String fullFilePath = file.getAbsolutePath();
			String fileName = file.getName();
			String filePath = fullFilePath.substring(0,
					(fullFilePath.length() - fileName.length()));
			return filePath;
		} else {
			return null;
		}
	}

}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
