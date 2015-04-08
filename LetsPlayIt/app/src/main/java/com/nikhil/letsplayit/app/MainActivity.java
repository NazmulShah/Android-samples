package com.nikhil.letsplayit.app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.nikhil.letsplayit.app.utils.PlayMusic;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    ArrayList<Integer> pressedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        pressedNote = new ArrayList<Integer>();

        Button a = (Button) findViewById(R.id.demo_a);
        a.setOnTouchListener(touchListener);
        Button b = (Button) findViewById(R.id.demo_b);
        b.setOnTouchListener(touchListener);
        Button c = (Button) findViewById(R.id.demo_c);
        c.setOnTouchListener(touchListener);
        Button d = (Button) findViewById(R.id.demo_d);
        d.setOnTouchListener(touchListener);
        Button e = (Button) findViewById(R.id.demo_e);
        e.setOnTouchListener(touchListener);

        Button play = (Button) findViewById(R.id.demo_p);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringToast = "Number of notes : ";
                for (Integer i : pressedNote) {
                    MediaPlayer mediaPlayer;
                    switch (i) {
                        case R.id.demo_a:
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.a);
                            stringToast += " A";
                            break;
                        case R.id.demo_b:
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.b);
                            stringToast += " B";
                            break;
                        case R.id.demo_c:
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.c);
                            stringToast += " C";
                            break;
                        case R.id.demo_d:
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.d);
                            stringToast += " D";
                            break;
                        case R.id.demo_e:
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.e);
                            stringToast += " E";
                            break;
                        default:
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.e);
                            stringToast += " Default";
                    }
                    new PlayMusic(mediaPlayer).start();
                }
                Toast.makeText(MainActivity.this, stringToast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    pressedNote.add(view.getId());
                }
                break;
                case MotionEvent.ACTION_UP: {
                    pressedNote.remove(Integer.valueOf(view.getId()));
                }
            }
            return true;
        }
    };

}
