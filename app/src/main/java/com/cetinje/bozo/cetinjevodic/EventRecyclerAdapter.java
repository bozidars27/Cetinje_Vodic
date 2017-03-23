package com.cetinje.bozo.cetinjevodic;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Petar on 3/20/2017.
 */

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.RecyclerViewHolder> {

    Context context;
    ArrayList<Events> events;

    EventRecyclerAdapter(Context context, ArrayList<Events> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row_layout, parent, false);
        EventRecyclerAdapter.RecyclerViewHolder recyclerViewHolder = new EventRecyclerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.eventTextViewTitle.setText(events.get(position).getName());
        holder.eventTextViewTitle.setTag(events.get(position).getId());
        holder.eventTextViewStart.setText(events.get(position).getDate_time());
        Glide.with(context).load(context.getFileStreamPath(events.get(position).getLogo())).into(holder.eventImageView);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView eventImageView;
        TextView eventTextViewTitle, eventTextViewStart;
        public RecyclerViewHolder(final View view) {
            super(view);
            eventImageView = (ImageView) view.findViewById(R.id.event_image_view);
            eventTextViewTitle = (TextView) view.findViewById(R.id.event_text_view_title);
            eventTextViewStart = (TextView) view.findViewById(R.id.event_text_view_start);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, EventPagerActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    String id = String.valueOf(eventTextViewTitle.getTag());
                    i.putExtra("id", id);
                    context.startActivity(i);
                }
            });
        }
    }
}
