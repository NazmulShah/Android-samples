package com.neev.android.POC.myjargon.util;

import java.io.IOException;

import android.media.MediaRecorder;

public class Recording {

	private MediaRecorder recorder;
	private String path;

	public Recording(String path) {
		recorder = new MediaRecorder();
		this.path = path;
	}

	private void record() {
		recorder.reset();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(path);
		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		recorder.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		recorder.stop();
	
	}

	public void execute() {
		record();
	}
}
