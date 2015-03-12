package com.nikhil.fragex.app.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.nikhil.fragex.app.R;

/**
 * Created by Nikhil Sharma on 004-04-03.
 *
 * mainFragment
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initView();

        return rootView;
    }

    private void initView() {
        Button buttonOne = (Button) rootView.findViewById(R.id.fragment_one_main_button);
        Button buttonTwo = (Button) rootView.findViewById(R.id.fragment_two_main_button);
        buttonTwo.setOnClickListener(onClickListener);
        buttonOne.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        Fragment innerFragment;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_one_main_button: {
                    Log.e(TAG,"one button");
                    innerFragment = new OneFragment();
                    break;
                }
                case R.id.fragment_two_main_button: {
                    Log.e(TAG,"two button");
                    innerFragment = new TwoFragment();
                    break;
                }
            }
            getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, innerFragment).commit();
        }
    };
}
