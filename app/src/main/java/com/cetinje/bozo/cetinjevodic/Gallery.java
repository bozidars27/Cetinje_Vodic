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

        Image image1 = new Image(R.drawable.sl1, 1, "Prva slika", "Opis slike 1", "Prvi grad");
        Image image2 = new Image(R.drawable.sl2, 1, "Druga slika", "Opis slike 2", "Prvi grad");
        Image image3 = new Image(R.drawable.sl3, 1, "Treca slika", "Opis slike 3", "Prvi grad");
        Image image4 = new Image(R.drawable.sl4, 1, "Cetvrta slika", "Opis slike 4", "Prvi grad");
        Image image5 = new Image(R.drawable.sl5, 1, "Peta slika", "Opis slike 5", "Drugi grad");
        Image image6 = new Image(R.drawable.sl6, 1, "Sesta slika", "Opis slike 6", "Drugi grad");
        Image image7 = new Image(R.drawable.sl7, 1, "Sedma slika", "Opis slike 7", "Drugi grad");
        Image image8 = new Image(R.drawable.sl8, 1, "Osma slika", "Opis slike 8", "Drugi grad");
        Image image9 = new Image(R.drawable.sl9, 1, "Deveta slika", "Opis slike 9", "Treći grad");
        Image image10 = new Image(R.drawable.sl10, 1, "Deseta slika", "Opis slike 10", "Treći grad");
        Image image11 = new Image(R.drawable.sl11, 1, "Jedanaesta slika", "Opis slike 11", "Treći grad");
        Image image12 = new Image(R.drawable.sl12, 1, "Dvanaesta slika", "Opis slike 12", "Treći grad");

        cities.add( new City ("Prvi grad", new ArrayList<>(Arrays.asList(image1, image2, image3, image4))) );
        cities.add( new City ("Drugi grad", new ArrayList<>(Arrays.asList(image5, image6, image7, image8))) );
        cities.add( new City ("Treći grad", new ArrayList<>(Arrays.asList(image9, image10, image11, image12))) );

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

}
