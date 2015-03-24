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

    public void testingButton(View view) {
        Toast.makeText(this, "GSK testing", Toast.LENGTH_SHORT).show();
    }

    public void incentiveButton(View view) {
        Toast.makeText(this, "GSK incentive", Toast.LENGTH_SHORT).show();
    }

    public void crmButton(View view) {
        Toast.makeText(this, "GSK crm", Toast.LENGTH_SHORT).show();
    }

    public void attendanceButton(View view) {
        Toast.makeText(this, "GSK attendance", Toast.LENGTH_SHORT).show();
    }

}
