/**
 * 
 */
package com.example.tagmyvideo.db.dao;

import java.util.Date;
import java.util.List;

import com.example.tagmyvideo.db.model.Video;

/**
 * This is used to access the video table in the database.
 * 
 * @author Nikhil Sharma and Prashant Prabhakar
 * 
 */
public interface VideoDAO {

	/**
	 * It is used to open database connection for video table.
	 */
	public void open();

	/**
	 * It is used to close database connection for video table.
	 */
	public void close();

	/**
	 * It is used to store the video in the video table.
	 * 
	 * @param newVideo
	 *            video to be stored in the database.
	 * @return {@link Integer} id of the stored video.
	 */
	public Integer createVideo(Video newVideo);

	/**
	 * It is used to delete the stored video from video table.
	 * 
	 * @param videoId
	 *            id of the video to be deleted.
	 * @return 0 if not deleted <br>
	 *         1 if deleted
	 */
	public int deleteVideo(final int videoId);

	/**
	 * It is used to get list of all video stored in the database.
	 * 
	 * @return {@link List} of {@link Video}.
	 */
	public List<Video> getAllVideos();

	/**
	 * It is used to find the video by id.
	 * 
	 * @param id
	 *            id of the video to be searched.
	 * @return {@link Video} if exists. <br>
	 *         otherwise null.
	 */
	public Video getVideoById(int id);

	/**
	 * It is used to find video by its name.
	 * 
	 * @param name
	 *            name of the video to be searched.
	 * @return {@link Video} if exists <br>
	 *         otherwise null.
	 */
	public Video getVideoByName(String name);

	/**
	 * It is used to find video is already added in the database.
	 * 
	 * @param name
	 *            name of the video.
	 * @param path
	 *            path of the video.
	 * @return -1 if not present <br>
	 *         id of the video if already exists.
	 */
	public Integer isDuplicate(String name, String path);

	/**
	 * It is used to update the video in the database.
	 * 
	 * @param videoId
	 *            id of video to be updated.
	 * @param length
	 *            length of the video.
	 * @return 0 if not updated <br>
	 *         1 if updated
	 */
	public int updateLength(final int videoId, long length);

	/**
	 * @param videoId
	 *            id of video to be updated.
	 * @param status
	 *            {@link boolean} bookmark status of the video.
	 * @return 0 if not updated <br>
	 *         1 if updated
	 */
	public int updateIsBookmarked(int videoId, boolean status);

	/**
	 * @param videoId
	 *            id of video to be updated.
	 * @param syncTime
	 *            sync time of the video.
	 * @return 0 if not updated <br>
	 *         1 if updated
	 */
	public int updateSyncData(int videoId, Date syncTime);
	
	/**
	 * 
	 * @param videoThumbnail
	 * 	byte array of the video to be updated.
	 * 
	 */
	
	public int updateVideoThumbnail(int videoId, byte[] videoThumbnail);
}
