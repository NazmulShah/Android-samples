package com.neev.android.POC.myjargon.util;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class Play {
	
	private MediaPlayer mediaPlayer;
	private final String path;
	
	public Play(String path) {
		mediaPlayer = new MediaPlayer();
		this.path = path;
	}
	public void playRecorded(){
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
		mediaPlayer.reset();
		mediaPlayer.setV
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepare();
		} catch (Exception e) {
			System.out.println("Exception in media player prepare....");
			e.printStackTrace();
		}

		mediaPlayer.start();
		/*SoundPool sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		sp.load(path, 1);
		sp.play(0, 1,1,0,0,2.3f); // only changes the frequency........ last one is frequency.....
		*/
		
	}
	
}
