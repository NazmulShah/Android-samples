package com.neev.android.POC.myjargon.util;

import com.neev.android.POC.myjargon.MainActivity;
import com.neev.android.POC.myjargon.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;

public class SoundChange extends AsyncTask<String, Void, String>{
	String path="";
	public SoundPool soundPool;
	boolean loaded=false;
	Context context;	
	public SoundChange(String path, MainActivity mnt) {
		this.path=path;
		context=mnt;
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		
	}int soundId;
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		
		new SoundPool(1,AudioManager.STREAM_MUSIC,0);
	       //explosion = sp.load(this, R.raw.hh,0);
	       soundId = soundPool.load(context,R.raw.dolat,0);
	}
	@Override
	protected String doInBackground(String... params) {
			System.out.println("starting sound.......");
           soundPool.play(soundId, 1, 1, 1, 0, 1f);
           System.out.println("sound is playing..........");
       return null;
	}
}
