package com.cetinje.bozo.cetinjevodic;

import android.content.Context;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;


public class GalleryPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public GalleryPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return GalleryRecyclerAdapter.PAGER_IMAGES.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object); //Izvršava validaciju. Provjerava da li je pozvani View zaista instanca kreiranog layout-a
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //moglo je i preko ViewGroup-a kao u slučaju GalleryRecyclerAdapter-a

        View view = layoutInflater.inflate(R.layout.pager_layout, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        final TextView textView = (TextView) view.findViewById(R.id.text_view);

        Glide.with(context).load(context.getFileStreamPath(GalleryRecyclerAdapter.PAGER_IMAGES.get(position).getImageName())).into(imageView);
        textView.setText( GalleryRecyclerAdapter.PAGER_IMAGES.get(position).getName());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView( (LinearLayout) object );
    }

}
