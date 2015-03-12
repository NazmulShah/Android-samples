package com.nikhil.sugar.horizontal_scroll;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.nikhil.sugar.R;

public class CarouselActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);

        init();
    }

    private void init() {
        emptyBackStack();
        getFragmentManager().beginTransaction().replace(R.id.container_carousel_frame, new CarouselFragment()).commit();
    }

    private void emptyBackStack() {
        while (getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStackImmediate();
    }

}
