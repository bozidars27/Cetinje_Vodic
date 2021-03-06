package com.cetinje.bozo.cetinjevodic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    TextView titleTextView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseHelper db;
    private ArrayList<Events> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        titleTextView = (TextView) findViewById(R.id.app_title);
        titleTextView.setText(getResources().getString(R.string.event_title));
        db = new DatabaseHelper(getApplicationContext());
        events = db.getAllEvents();
        recyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);
        adapter = new EventRecyclerAdapter(getApplicationContext(), events);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
}
