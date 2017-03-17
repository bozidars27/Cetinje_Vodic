package com.cetinje.bozo.cetinjevodic;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {


    //deklarisanje polja
    TextView polje1;
    private DatabaseHelper db;
    private ArrayList<String> restaurants;
    /*String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X","Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X","Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);




        polje1 = (TextView) findViewById(R.id.textEdit1);
        db = new DatabaseHelper(getApplicationContext());
        restaurants = db.getAllRestaurantsName();
        if(restaurants.size() == 0)
        {
            polje1.setText("Nema restorana");
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.content_restaurant, restaurants);

        ListView listView = (ListView) findViewById(R.id.list_restaurant);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
