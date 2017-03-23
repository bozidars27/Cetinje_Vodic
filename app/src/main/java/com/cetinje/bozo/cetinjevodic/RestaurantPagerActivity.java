package com.cetinje.bozo.cetinjevodic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RestaurantPagerActivity extends AppCompatActivity {

    private TextView textView, textViewTitle;
    private ImageView imageView;
    private DatabaseHelper db;
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_pager);

        context = getApplicationContext();
        db = new DatabaseHelper(getApplicationContext());
        textView = (TextView) findViewById(R.id.restaurant_description);
        textViewTitle = (TextView) findViewById(R.id.restaurant_title);
        imageView = (ImageView) findViewById(R.id.image_restaurant);
        Intent i = getIntent();
        Restaurant restaurant = db.getSpecificRestaurants(i.getStringExtra("id"));
        Glide.with(context).load(context.getFileStreamPath(restaurant.getLogo())).into(imageView);
        textViewTitle.append(" " + restaurant.getName());
        textView.append(" " + restaurant.getDescription());
        recyclerView = (RecyclerView) findViewById(R.id.feedback_recycler_view);
        adapter = new FeedbackRecyclerAdapter(getApplicationContext(), restaurant.getFeedbacks());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
}
