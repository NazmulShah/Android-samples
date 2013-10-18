package com.neev.android.POC.myjargon;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.neev.android.POC.myjargon.util.Constant;
import com.neev.android.POC.myjargon.util.FileCopy;
import com.neev.android.POC.myjargon.util.Play;

public class MainActivity extends Activity implements OnClickListener {

	private static boolean continueExecution = true;
//	private Recording recording;
	private Play play;
	private RestartHandler restartHandler;
	private Button start, stop, change;
//	private SoundChange soundChange;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		start = (Button) findViewById(R.id.start);
		stop = (Button) findViewById(R.id.stop);
		change = (Button) findViewById(R.id.change);

		
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		change.setOnClickListener(this);
		stop.setVisibility(View.INVISIBLE);

		restartHandler = new RestartHandler();
		createDirectory(Constant.directory);
//		recording = new Recording(Constant.recorderFile);
		play = new Play(Constant.tempFile);
		
//		soundChange = new SoundChange(Constant.tempFile,this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void createDirectory(String path) {
		File directory = new File(path);
		directory.mkdirs();
	}

	public void execution() {
		if (continueExecution) {
//			recording.execute();
//			FileCopy.copyfile(Constant.recorderFile, Constant.playFile);
			play.playRecorded();
//			restartHandler.restart();
		}
	}

	@SuppressLint("HandlerLeak")
	class RestartHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			MainActivity.this.execution();
		}

		public void restart() {
			sendMessage(obtainMessage(0));
		}
	}

	@Override
	public void onClick(View view) {
		if (view == start) {
			continueExecution = true;
			execution();
//			soundChange.execute();
			stop.setVisibility(View.VISIBLE);
			start.setVisibility(View.INVISIBLE);

		} else if (view == stop) {
			continueExecution = false;
			start.setVisibility(View.VISIBLE);
			stop.setVisibility(View.INVISIBLE);
		} else if (view == change){
			Intent intent= new Intent(this, AudioPlayer1.class);
			startActivity(intent);
		}

	};
	
}
