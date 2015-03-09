/**
 * 
 */
package com.example.tagmyvideo.db.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Nikhil Sharma & Prashant Prabhakar
 * 
 */
public class Video implements Parcelable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String fileName;
	private Long length;
	private Boolean isBookmarked;
	private Integer frequency;
	private Integer isSync;
	private Date sync_time;
	private String path;
	private byte[] thumbData;
	private Integer localVideoId;

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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the length
	 */
	public Long getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(Long length) {
		this.length = length;
	}

	/**
	 * @return the isBookmarked
	 */
	public Boolean getIsBookmarked() {
		return isBookmarked;
	}

	/**
	 * @param isBookmarked
	 *            the isBookmarked to set
	 */
	public void setIsBookmarked(Boolean isBookmarked) {
		this.isBookmarked = isBookmarked;
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

	/**
	 * @return the isSync
	 */
	public Integer getIsSync() {
		return isSync;
	}

	/**
	 * @param isSync
	 *            the isSync to set
	 */
	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}

	/**
	 * @return the sync_time
	 */
	public Date getSync_time() {
		return sync_time;
	}

	/**
	 * @param sync_time
	 *            the sync_time to set
	 */
	public void setSync_time(Date sync_time) {
		this.sync_time = sync_time;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getThumbData() {
		return thumbData;
	}

	public void setThumbData(byte[] thumbData) {
		this.thumbData = thumbData;
	}

	public Integer getLocalVideoId() {
		return localVideoId;
	}

	public void setLocalVideoId(Integer localVideoId) {
		this.localVideoId = localVideoId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		/*
		 * dest.writeInt(id); dest.writeString(fileName);
		 * dest.writeLong(length); dest.writeInt(frequency);
		 * dest.writeInt(isSync); dest.writeString(path);
		 * dest.writeByteArray(thumbData);
		 */
	}

}
