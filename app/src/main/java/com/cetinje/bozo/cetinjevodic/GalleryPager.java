package com.cetinje.bozo.cetinjevodic;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class GalleryPager extends AppCompatActivity {

    FloatingActionButton fab;
    ViewPager viewPager;
    TextView description;

    int descriptionHeight;

    boolean descriptionShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_pager);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        description = (TextView) findViewById(R.id.description);

        description.setVisibility(View.INVISIBLE);

        final Animation hopIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.hop_in);
        final Animation hopOut = AnimationUtils.loadAnimation(getBaseContext(), R.anim.hop_out);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!descriptionShown) {
                    description.setVisibility(View.VISIBLE);
                    description.setText(GalleryRecyclerAdapter.PAGER_IMAGES.get(viewPager.getCurrentItem()).getDescription());
                    description.startAnimation(hopIn);
                    descriptionShown = true;
                } else {
                    description.startAnimation(hopOut);
                    descriptionShown = false;
                }

            }
        });

        viewPager.setAdapter( new GalleryPagerAdapter(getBaseContext()) );
        viewPager.setCurrentItem(GalleryRecyclerAdapter.CLICKED_POSITION);

    }

}
