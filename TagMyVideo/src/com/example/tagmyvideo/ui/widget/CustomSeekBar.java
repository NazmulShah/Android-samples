/**
 * 
 */
package com.example.tagmyvideo.ui.widget;

import java.util.LinkedHashSet;
import java.util.Set;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import com.example.tagmyvideo.R;

/**
 * @author Ashish R Agre
 * 
 */
public class CustomSeekBar extends SeekBar {
	private Context context;
	private int x;
	private boolean drawBookmark = false;
	private Paint paintRect;
	private Resources res;
	private Bitmap bitmap;
	private Set<Integer> bookmarks;

	public CustomSeekBar(Context context) {
		super(context);
		this.context = context;
		paintRect = new Paint();
		res = getResources();
		bitmap = BitmapFactory.decodeResource(res, R.drawable.tag);
		bookmarks = new LinkedHashSet<Integer>();
	}

	public CustomSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		paintRect = new Paint();
		res = getResources();
		bitmap = BitmapFactory.decodeResource(res, R.drawable.tag);
		bookmarks = new LinkedHashSet<Integer>();
	}

	public CustomSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		paintRect = new Paint();
		res = getResources();
		bitmap = BitmapFactory.decodeResource(res, R.drawable.tag);
		bookmarks = new LinkedHashSet<Integer>();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paintRect.setColor(Color.rgb(255, 0, 0));
		if (drawBookmark) {
			x = getProgress();
			bookmarks.add(x);
			Log.e("XPOS", "X" + x);
			Log.e("CurrentProgress", "Progress" + this.getProgress());
			drawBookmark = false;
		}
		for (Integer xpos : bookmarks) {
			xpos = (((getRight() - getLeft()) * xpos) / getMax());
			canvas.drawBitmap(bitmap, xpos, 0, paintRect);
		}
		
		

	}

	public void setBookmark() {
		drawBookmark = true;
	}

	public void setBookmarks(Set<Integer> bookmarks) {
		this.bookmarks = bookmarks;
	}
	public Set<Integer> getBookmarks() {
		return this.bookmarks;
	}
}
