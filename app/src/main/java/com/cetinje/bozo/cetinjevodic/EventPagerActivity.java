package com.cetinje.bozo.cetinjevodic;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class EventPagerActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textViewTitle, textViewDescription, textViewStart;
    DatabaseHelper db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pager);

        context = getApplicationContext();
        db = new DatabaseHelper(getApplicationContext());
        imageView = (ImageView) findViewById(R.id.image_event);
        textViewTitle = (TextView) findViewById(R.id.event_title);
        textViewDescription = (TextView) findViewById(R.id.event_description);
        textViewStart = (TextView) findViewById(R.id.event_start);
        Intent i = getIntent();
        Events event = db.getSpecificEvent(i.getStringExtra("id"));
        textViewTitle.append(event.getName());
        textViewDescription.append(event.getDescription());
        textViewStart.append(event.getDate_time());
        Glide.with(context).load(context.getFileStreamPath(event.getLogo())).into(imageView);
    }
}
