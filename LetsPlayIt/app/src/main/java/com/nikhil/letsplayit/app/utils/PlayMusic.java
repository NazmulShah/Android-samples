package com.nikhil.letsplayit.app.utils;

import android.media.MediaPlayer;

/**
 * Created by USER on 007-07-04.
 */
public class PlayMusic extends Thread {

    MediaPlayer mediaPlayer;

    public PlayMusic(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void run() {
        mediaPlayer.start();
    }
}
