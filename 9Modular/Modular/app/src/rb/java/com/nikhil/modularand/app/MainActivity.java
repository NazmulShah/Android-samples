package com.nikhil.modularand.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void attendanceButton(View view) {
        Toast.makeText(this, "RB attendance", Toast.LENGTH_SHORT).show();
    }

    public void checkInButton(View view) {
        Toast.makeText(this, "RB CheckIn", Toast.LENGTH_SHORT).show();
    }
}
