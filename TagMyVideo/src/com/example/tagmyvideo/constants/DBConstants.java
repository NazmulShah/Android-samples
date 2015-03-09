package com.example.tagmyvideo.constants;

public final class DBConstants {

	public static final String TABLE_VIDEO = "videos";
	public static final String COLUMN_VIDEO_ID = "id";
	public static final String COLUMN_VIDEO_FILE_NAME = "filename";
	public static final String COLUMN_VIDEO_LENGTH = "length";
	public static final String COLUMN_VIDEO_IS_BOOKMARKED = "is_bookmarked";
	public static final String COLUMN_VIDEO_FREQUENCY = "frequency";
	public static final String COLUMN_VIDEO_IS_SYNC = "is_sync";
	public static final String COLUMN_VIDEO_SYNC_TIME = "sync_time";
	public static final String COLUMN_VIDEO_PATH = "path";
	public static final String COLUMN_VIDEO_THUMB = "thumb";

	public static final String TABLE_TAG = "bookmarks";
	public static final String COLUMN_TAG_ID = "id";
	public static final String COLUMN_TAG_VIDEO_ID = "video_id";
	public static final String COLUMN_TAG_CAPTION = "caption";
	public static final String COLUMN_TAG_CREATION_TIME = "creation_time";
	public static final String COLUMN_TAG_BOOKMARK_TIME = "bookmark_time";
	public static final String COLUMN_TAG_FREQUENCY = "frequency";

}
