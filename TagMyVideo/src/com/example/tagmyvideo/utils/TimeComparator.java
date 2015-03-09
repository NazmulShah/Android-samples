<<<<<<< HEAD
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.util.Comparator;

import com.example.tagmyvideo.db.model.Tag;

/**
 * @author Ashish R Agre
 * 
 */
public class TimeComparator implements Comparator<Tag> {

	@Override
	public int compare(Tag one, Tag two) {
		return one.getBookmarkTime().compareTo(two.getBookmarkTime());
	}
}
=======
/**
 * 
 */
package com.example.tagmyvideo.utils;

import java.util.Comparator;

import com.example.tagmyvideo.db.model.Tag;

/**
 * @author Ashish R Agre
 * 
 */
public class TimeComparator implements Comparator<Tag> {

	@Override
	public int compare(Tag one, Tag two) {
		return one.getBookmarkTime().compareTo(two.getBookmarkTime());
	}
}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
