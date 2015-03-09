<<<<<<< HEAD
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.io.File;
import java.util.Comparator;

/**
 * @author Ashish R Agre
 * 
 */
public class NameComparator implements Comparator<File> {
	public int compare(File one, File two) {
		return one.getName().compareToIgnoreCase(two.getName());
	}
}
=======
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.io.File;
import java.util.Comparator;

/**
 * @author Ashish R Agre
 * 
 */
public class NameComparator implements Comparator<File> {
	public int compare(File one, File two) {
		return one.getName().compareToIgnoreCase(two.getName());
	}
}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
