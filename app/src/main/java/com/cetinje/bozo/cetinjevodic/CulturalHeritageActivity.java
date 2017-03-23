package com.cetinje.bozo.cetinjevodic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class CulturalHeritageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseHelper db;
    private ArrayList<CulturalHeritage> culturalHeritages;
    private TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultural_heritage);
        textViewTitle = (TextView) findViewById(R.id.app_title);
        textViewTitle.setText(getResources().getString(R.string.cultural_heritage_title));
        db = new DatabaseHelper(getApplicationContext());
        culturalHeritages = db.getAllCulturalHeritages();
        recyclerView = (RecyclerView) findViewById(R.id.cultural_heritage_recycler_view);
        adapter = new CulturalHeritageRecyclerAdapter(getApplicationContext(), culturalHeritages);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
