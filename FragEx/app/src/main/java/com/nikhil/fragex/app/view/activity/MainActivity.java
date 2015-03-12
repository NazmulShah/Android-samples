package com.nikhil.fragex.app.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import com.nikhil.fragex.app.R;
import com.nikhil.fragex.app.view.fragment.MainFragment;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    private void initFragment() {
        getFragmentManager().beginTransaction().add(R.id.main_fragment_container, new MainFragment()).commit();
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStackImmediate();
    }

    public void fragmentTwo(View view) {
        Log.d(TAG,"fragment Two clicked");
    }

    public void fragmentOne(View view) {
        Log.d(TAG,"fragment One clicked");
    }
}
