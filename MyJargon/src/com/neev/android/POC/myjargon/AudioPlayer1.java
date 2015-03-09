package com.neev.android.POC.myjargon;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;

public class AudioPlayer1 extends Activity {

	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundsMap;
	int SOUND1 = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_player1);
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundsMap = new HashMap<Integer, Integer>();
		soundsMap.put(SOUND1, soundPool.load(this, R.raw.dolat, 1));
	}

	public void playSound(int sound, float fSpeed) {
		AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;

		soundPool.play(soundsMap.get(sound), volume, volume, 1, 0, fSpeed);
	}

	// User has clicked "Play" . . .
	public void Audio1ButtonHandler(View target) {
		/*
		 * RadioButton radioSound1 =
		 * (RadioButton)this.findViewById(R.id.radioSound1); RadioButton
		 * radioSpeedLo = (RadioButton)this.findViewById(R.id.radioSpeedLo);
		 * RadioButton radioSpeedNorm =
		 * (RadioButton)this.findViewById(R.id.radioSpeedNorm); float fSpeed =
		 * 1.0f; if (radioSpeedLo.isChecked()) { fSpeed = 0.5f; } else if
		 * (radioSpeedNorm.isChecked()) { fSpeed = 1.0f; } else { fSpeed = 2.0f;
		 * }
		 */
		// select the sound file
		playSound(SOUND1, 0.5f);
	}
}