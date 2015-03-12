package com.nikhil.fragex.app.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nikhil.fragex.app.R;

/**
 * Created by Nikhil Sharma on 004-04-03.
 */
public class OneFragment extends Fragment {
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_one,container,false);
        return rootView;
    }
}
