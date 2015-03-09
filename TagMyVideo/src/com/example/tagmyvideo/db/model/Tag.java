/**
 * 
 */
package com.example.tagmyvideo.db.model;

import java.util.Date;

/**
 * @author Nikhil Sharma & Prashant Prabhakar
 * 
 */
public class Tag {

	private Integer id;
	private Integer video_id;
	private String caption;
	private Integer bookmarkTime;
	private Date creationTime;
	private Integer frequency;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the video_id
	 */
	public Integer getVideo_id() {
		return video_id;
	}

	/**
	 * @param video_id
	 *            the video_id to set
	 */
	public void setVideo_id(Integer video_id) {
		this.video_id = video_id;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption
	 *            the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the bookmarkTime
	 */
	public Integer getBookmarkTime() {
		return bookmarkTime;
	}

	/**
	 * @param bookmarkTime
	 *            the bookmarkTime to set
	 */
	public void setBookmarkTime(Integer bookmarkTime) {
		this.bookmarkTime = bookmarkTime;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime
	 *            the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the frequency
	 */
	public Integer getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency
	 *            the frequency to set
	 */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

}
