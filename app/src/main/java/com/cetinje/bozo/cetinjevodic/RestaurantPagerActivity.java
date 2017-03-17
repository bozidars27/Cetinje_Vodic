package com.cetinje.bozo.cetinjevodic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RestaurantPagerActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private DatabaseHelper db;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_pager);

        context = getApplicationContext();
        db = new DatabaseHelper(getApplicationContext());
        textView = (TextView) findViewById(R.id.restaurant_description);
        imageView = (ImageView) findViewById(R.id.image_restaurant);
        Intent i = getIntent();
        Restaurant restaurant = db.getSpecificRestaurants(Integer.parseInt(i.getStringExtra("id")));
        Glide.with(context).load(context.getFileStreamPath(restaurant.getLogo())).into(imageView);
        textView.append(" " + restaurant.getDescription());

    }
}
