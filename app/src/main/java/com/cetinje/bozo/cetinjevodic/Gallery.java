package com.cetinje.bozo.cetinjevodic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class Gallery extends AppCompatActivity {

    RecyclerView imageRecycler;
    GalleryRecyclerAdapter recyclerAdapter;
    GridLayoutManager recyclerLayoutManager;

    ArrayList<City> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());

        ArrayList<Image> allImages = dbHelper.getAllImages();

        cities = formCitiesList(allImages);

        imageRecycler = (RecyclerView) findViewById(R.id.image_recycler);

        imageRecycler.setHasFixedSize(true);

        recyclerAdapter = new GalleryRecyclerAdapter(cities, getBaseContext());
        imageRecycler.setAdapter(recyclerAdapter);

        recyclerLayoutManager = new GridLayoutManager(this, 2);
        recyclerLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                if (recyclerAdapter.getGalleryData().get(position) instanceof City) {
                    return 2;
                }
                return 1;
            }
        });
        imageRecycler.setLayoutManager(recyclerLayoutManager);

    }

    //Funkcija koja služi da premosti staru i novu logiku formiranja galerije koje se međusobno
    //dosta razlikuju. Logika je promijenjena nakon vezivanja sa bazom.
    private ArrayList<City> formCitiesList(ArrayList<Image> images) {

        ArrayList<City> cities = new ArrayList<>();

        String currentCityName = "";
        ArrayList<Image> currentCityImages = new ArrayList<>();


        for (Image i : images) {

            if ( currentCityName.equals("") )
                currentCityName = i.getCityName();

            if ( !i.getCityName().equals(currentCityName) ) {
                cities.add(new City(currentCityName, currentCityImages));
                currentCityName = i.getCityName();
                currentCityImages = new ArrayList<>();
                currentCityImages.add(i);
            } else {
                currentCityImages.add(i);
            }

        }

        return cities;

    }

}
