package com.cetinje.bozo.cetinjevodic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Petar on 3/20/2017.
 */

public class FeedbackRecyclerAdapter extends RecyclerView.Adapter<FeedbackRecyclerAdapter.RecyclerViewHolder> {

    Context context;
    ArrayList<Feedback> feedbacks;

    FeedbackRecyclerAdapter(Context context, ArrayList<Feedback> feedbacks)
    {
        this.context = context;
        this.feedbacks = feedbacks;
    }

    @Override
    public FeedbackRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_row_layout, parent, false);
        FeedbackRecyclerAdapter.RecyclerViewHolder recyclerViewHolder = new FeedbackRecyclerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(FeedbackRecyclerAdapter.RecyclerViewHolder holder, int position) {
        holder.feedbackRatingBar.setRating(feedbacks.get(position).getMark());
        holder.feedbackTextView.setText(feedbacks.get(position).getImpression());
    }

    @Override
    public int getItemCount() {
        return feedbacks.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView feedbackTextView;
        RatingBar feedbackRatingBar;
        public RecyclerViewHolder(final View view){
            super(view);
            feedbackTextView = (TextView) view.findViewById(R.id.impression_text_view);
            feedbackRatingBar = (RatingBar) view.findViewById(R.id.feedback_rating_bar);
        }
    }
}
