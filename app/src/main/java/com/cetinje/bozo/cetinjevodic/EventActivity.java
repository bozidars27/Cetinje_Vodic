package com.cetinje.bozo.cetinjevodic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    TextView polje1;
    private DatabaseHelper db;
    private ArrayList<Events> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        polje1 = (TextView) findViewById(R.id.textEdit1);
        db = new DatabaseHelper(getApplicationContext());
        events = db.getAllEvents();

        if(events.size() == 0)
        {
            polje1.setText("Nema dogadjaja");
        }

        for (Events i : events)
        {
            polje1.setText(polje1.getText() + " \n" + i.getName() + i.getDate_time());
        }

    }
}
