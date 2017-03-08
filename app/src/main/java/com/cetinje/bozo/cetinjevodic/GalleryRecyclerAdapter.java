package com.cetinje.bozo.cetinjevodic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


class GalleryRecyclerAdapter extends RecyclerView.Adapter{

    public static ArrayList<Image> PAGER_IMAGES = new ArrayList<>();
    public static int CLICKED_POSITION;

    private ArrayList<City> cities = new ArrayList<>();
    private Context context;
    private ArrayList<Object> data = new ArrayList<>();

    GalleryRecyclerAdapter(ArrayList<City> cities, Context context) {
        this.cities = cities;
        this.context = context;
        formDataList();
    }

    public ArrayList<Object> getGalleryData() {
        return data;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (data.get(position) instanceof City) {
            City currentCity = (City) data.get(position);
            ((SectionViewHolder) holder).title.setText(currentCity.getName());
            Glide.with(context).load(currentCity.getPictures().get(0).getId()).into(((SectionViewHolder) holder).imageView);
        } else {
            Glide.with(context).load(((Image) data.get(position)).getId()).into(((GalleryViewHolder) holder).imageView);
            ((GalleryViewHolder) holder).boundedImageObject = (Image) data.get(position);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        if (viewType == R.layout.grid_item) {
            return new GalleryViewHolder(view);
        }

        return new SectionViewHolder(view);

    }

    @Override
    public int getItemCount() {

        int numOfItems = 0;

        for (City c : cities) {
            numOfItems += c.getPictures().size() + 1;
        }

        return numOfItems;

    }

    @Override
    public int getItemViewType(int position) {
        if ( data.get(position) instanceof City ) {
            return R.layout.grid_section;
        }
        return R.layout.grid_item;
    }


    private class GalleryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        Image boundedImageObject;

        private GalleryViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.grid_item);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setImagesForPagerActivity(boundedImageObject);
                    Intent i = new Intent(context, GalleryPager.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });

        }
    }

    private class SectionViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;

        private SectionViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.grid_item);
            title = (TextView) itemView.findViewById(R.id.section_name);

        }
    }


    public void setImagesForPagerActivity(Image image) {

        City targetCity = new City();
        ArrayList<Image> images = new ArrayList<>();

        try {

            for (City c : cities) {
                if ( c.getName().equals(image.getCityName()) ){
                    targetCity = c;
                    CLICKED_POSITION = c.getPictures().indexOf(image);
                    break;
                }
            }

            for (Image i : targetCity.getPictures()) {
                images.add(i);
            }

        } catch (Exception e) {
            Log.e("City not found", "City corresponding to the name in the image not found");
        }

        PAGER_IMAGES = images;

    }

    private void formDataList() {

        for ( City city : cities ) {
            data.add(city);
            for ( Image i : city.getPictures() ) {
                data.add(i);
            }
        }

    }

}
