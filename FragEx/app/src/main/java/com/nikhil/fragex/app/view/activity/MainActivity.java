package com.nikhil.fragex.app.view.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.nikhil.fragex.app.R;
import com.nikhil.fragex.app.view.fragment.AppleFragment;
import com.nikhil.fragex.app.view.fragment.BlackBerryFragment;


public class MainActivity extends Activity implements OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(1);
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStackImmediate();
    }

    public void fragmentTwo(View view) {
        Log.d(TAG, "fragment Two clicked");
    }

    public void fragmentOne(View view) {
        Log.d(TAG, "fragment One clicked");
    }

    @Override
    public void changeFragment(int index) {
        FragmentTransaction ft = getFragmentManager().beginTransaction().
                setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        switch (index) {
            case 1:
                ft.replace(R.id.main_fragment_container, AppleFragment.newInstance()).commit();
                break;

            case 2:
                ft.replace(R.id.main_fragment_container, BlackBerryFragment.newInstance("Apple", "BlackBerry")).commit();
                break;
        }

    }
}
