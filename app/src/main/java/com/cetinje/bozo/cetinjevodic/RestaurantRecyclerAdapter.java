package com.cetinje.bozo.cetinjevodic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Petar on 3/17/2017.
 */

public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RestaurantRecyclerAdapter.RecyclerViewHolder>{
    Context context;
    ArrayList<Restaurant> restaurants;

    public RestaurantRecyclerAdapter(ArrayList<Restaurant> restaurants, Context context)
    {
        this.context = context;
        this.restaurants = restaurants;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.restaurantTextView.setText(restaurants.get(position).getName());
        holder.restaurantTextView.setTag(restaurants.get(position).getId());
        Glide.with(context).load(context.getFileStreamPath(restaurants.get(position).getLogo())).into(holder.restaurantImageView);
        holder.restaurantRatingBar.setRating(restaurants.get(position).getAverageMark());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView restaurantImageView;
        TextView restaurantTextView;
        RatingBar restaurantRatingBar;
        public RecyclerViewHolder(final View view)
        {
            super(view);
            restaurantImageView = (ImageView) view.findViewById(R.id.restaurant_image_view);
            restaurantTextView = (TextView) view.findViewById(R.id.restaurant_text_view);
            restaurantRatingBar = (RatingBar) view.findViewById(R.id.restaurant_rating_bar);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, RestaurantPagerActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    String id = String.valueOf(restaurantTextView.getTag());
                    i.putExtra("id", id);
                    context.startActivity(i);
                }
            });
        }
    }
}
