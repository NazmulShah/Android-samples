/**
 * 
 */
package com.example.tagmyvideo.db.dao;

import java.util.List;

import com.example.tagmyvideo.db.model.Tag;

/**
 * This is used to access the tag table in the database.
 * 
 * @author Nikhil Sharma & Prashant Prabhakar
 * 
 */
public interface TagDAO {

	/**
	 * It is used to open database connection for tag table.
	 */
	public void open();

	/**
	 * It is used to close database connection for tag table.
	 */
	public void close();

	/**
	 * It is used to store the tag in the tag table.
	 * 
	 * @param newTag
	 * @return
	 */
	public long createTag(Tag newTag);

	/**
	 * It is used to delete tag from database.
	 * 
	 * @param tagId
	 *            id of the tag to be deleted.
	 * @return 0 if not deleted <br>
	 *         1 if deleted
	 */
	public int deleteTag(int tagId);

	/**
	 * It is used to get all the tags for a particular video.
	 * 
	 * @param videoId
	 *            id of the video for tags are required.
	 * @return {@link List} of {@link Tag}.
	 */
	public List<Tag> getAllTags(int videoId);

	/**
	 * It is used to update the caption of the tag in the database.
	 * 
	 * @param tagId
	 *            id of tag to be updated.
	 * @param caption
	 *            caption of the tag.
	 * @return 0 if not updated <br>
	 *         1 otherwise
	 */

	public int updateCaption(int tagId, String caption);

	/**
	 * It is used to update the frequency of the tag.
	 * 
	 * @param tagId
	 *            id of the tag to be updated.
	 * @return 0 if not updated <br>
	 *         1 otherwise
	 */
	public int updateFrequency(int tagId);

	/**
	 * It is used to find the tag by bookmark time.
	 * 
	 * @param videoId
	 *            id of the video for which tag to be searched.
	 * @param bookmarkTime
	 *            time of the bookmark to be searched.
	 * @return id of the tag if tag for the given bookmark time is present <br>
	 *         otherwise -1
	 */
	public Tag getTagByBookmarkTime(final int videoId, final int bookmarkTime);

}
