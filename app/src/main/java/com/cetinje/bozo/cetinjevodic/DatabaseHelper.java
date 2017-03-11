package com.cetinje.bozo.cetinjevodic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petar on 2/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "travelGuide";

    // Table Names
    private static final String TABLE_COUNTRY = "country";
    private static final String TABLE_TOWN = "town";
    private static final String TABLE_TOUR = "tour";
    private static final String TABLE_USER = "user";
    private static final String TABLE_PATH = "path";
    private static final String TABLE_QUIZ = "quiz";
    private static final String TABLE_RESTAURANT = "restaurant";
    private static final String TABLE_FEEDBACK = "feedback";
    private static final String TABLE_CULTURAL_HERITAGE = "cultural_heritage";
    private static final String TABLE_IMAGE = "image";
    private static final String TABLE_VIDEO = "video";
    private static final String TABLE_TEXT = "text";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ID_TOUR = "id_tour";
    private static final String KEY_ID_TOWN = "id_town";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_ID_CULTURAL_HERITAGE = "id_cultural_heritage";
    private static final String KEY_IND = "ind";
    private static final String KEY_DESCRIPTION = "description";

    // TOWN Table - column names
    private static final String KEY_ID_COUNTRY = "id_country";

    // TOUR Table - column names
    private static final String KEY_CODE = "code";

    // QUIZ Table - column names
    private static final String KEY_QUESTION = "question";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TANS = "Tans";
    private static final String KEY_FANS1 = "Fans1";
    private static final String KEY_FANS2 = "Fans2";
    private static final String KEY_FANS3 = "Fans3";

    // RESTAURANT Table - column names
    private static final String KEY_PRICE_LIST = "price_list";

    // FEEDBACK Table - column names
    private static final String KEY_ID_RESTAURANT = "id_restaurant";
    private static final String KEY_MARK = "mark";
    private static final String KEY_IMPRESSION = "impression";

    // IMAGE Table - column names
    private static final String KEY_CITY_NAME = "city_name";

    // CULTURAL_HERITAGE Table - column names
    private static final String KEY_LOGO = "logo";

    // TEXT Table - column names
    private static final String KEY_ID_VIDEO = "id_video";
    private static final String KEY_TITLE = "title";

    // Table Create Statements
    // Country table create statement
    private static final String CREATE_TABLE_COUNTRY = "CREATE TABLE "
            + TABLE_COUNTRY + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME
            + " TEXT)";

    // Town table create statement
    private static final String CREATE_TABLE_TOWN = "CREATE TABLE " + TABLE_TOWN
            + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_COUNTRY + " INTEGER, "
            + KEY_NAME + " TEXT, "
            + " FOREIGN KEY ("+KEY_ID_COUNTRY+") REFERENCES " + TABLE_COUNTRY + "(" +KEY_ID +"))";

    // Tour table create statement
    private static final String CREATE_TABLE_TOUR = "CREATE TABLE "
            + TABLE_TOUR + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_TOWN + " INTEGER, "
            + KEY_CODE + " TEXT, "
            + KEY_NAME + " TEXT, "
            + " FOREIGN KEY ("+KEY_ID_TOWN+") REFERENCES " + TABLE_TOWN + "(" +KEY_ID +"))";

    // User table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_TOUR + " INTEGER, "
            + KEY_NAME + " TEXT, "
            + " FOREIGN KEY ("+KEY_ID_TOUR+") REFERENCES " + TABLE_TOUR + "(" +KEY_ID +"))";

    // Path table create statement
    private static final String CREATE_TABLE_PATH = "CREATE TABLE "
            + TABLE_PATH + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_TOUR + " INTEGER, "
            + KEY_LAT + " REAL, "
            + KEY_LNG + " REAL, "
            + KEY_IND + " INTEGER, "
            + " FOREIGN KEY ("+KEY_ID_TOUR+") REFERENCES " + TABLE_TOUR + "(" +KEY_ID +"))";

    // Quiz table create statement
    private static final String CREATE_TABLE_QUIZ = "CREATE TABLE "
            + TABLE_QUIZ + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_TOUR + " INTEGER, "
            + KEY_QUESTION + " TEXT, "
            + KEY_TYPE + " INTEGER, "
            + KEY_TANS + " TEXT, "
            + KEY_FANS1 + " TEXT, "
            + KEY_FANS2 + " TEXT, "
            + KEY_FANS3 + " TEXT, "
            + " FOREIGN KEY ("+KEY_ID_TOUR+") REFERENCES " + TABLE_TOUR + "(" +KEY_ID +"))";

    // Restaurant table create statement
    private static final String CREATE_TABLE_RESTAURANT = "CREATE TABLE "
            + TABLE_RESTAURANT + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_TOWN + " INTEGER, "
            + KEY_NAME + " TEXT, "
            + KEY_LAT + " REAL, "
            + KEY_LNG + " REAL, "
            + KEY_PRICE_LIST + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + " FOREIGN KEY ("+KEY_ID_TOWN+") REFERENCES " + TABLE_TOWN + "(" +KEY_ID +"))";

    // Feedback table create statement
    private static final String CREATE_TABLE_FEEDBACK = "CREATE TABLE "
            + TABLE_FEEDBACK + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_RESTAURANT + " INTEGER, "
            + KEY_MARK + " INTEGER"
            + KEY_IMPRESSION + " TEXT, "
            + " FOREIGN KEY ("+KEY_ID_RESTAURANT+") REFERENCES " + TABLE_RESTAURANT + "(" +KEY_ID +"))";

    // CulturalHeritage table create statement
    private static final String CREATE_TABLE_CULTURAL_HERITAGE = "CREATE TABLE "
            + TABLE_CULTURAL_HERITAGE + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_TOWN + " INTEGER, "
            + KEY_ID_TOUR + " INTEGER, "
            + KEY_NAME + " TEXT, "
            + KEY_LAT + " REAL, "
            + KEY_LNG + " REAL, "
            + KEY_LOGO + " TEXT, "
            + " FOREIGN KEY ("+KEY_ID_TOWN+") REFERENCES " + TABLE_TOWN + "(" +KEY_ID +"), "
            + " FOREIGN KEY ("+KEY_ID_TOUR+") REFERENCES " + TABLE_TOUR + "(" +KEY_ID +"))";

    // Image table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE "
            + TABLE_IMAGE + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_CULTURAL_HERITAGE + " INTEGER, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CITY_NAME + " TEXT, "
            + " FOREIGN KEY ("+KEY_ID_CULTURAL_HERITAGE+") REFERENCES " + TABLE_CULTURAL_HERITAGE + "(" +KEY_ID +"))";

    // Video table create statement
    private static final String CREATE_TABLE_VIDEO = "CREATE TABLE "
            + TABLE_VIDEO + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_CULTURAL_HERITAGE + " INTEGER, "
            + KEY_NAME + " TEXT, "
            + KEY_IND + " INTEGER, "
            + " FOREIGN KEY ("+KEY_ID_CULTURAL_HERITAGE+") REFERENCES " + TABLE_CULTURAL_HERITAGE + "(" +KEY_ID +"))";

    // Text table create statement
    private static final String CREATE_TABLE_TEXT = "CREATE TABLE "
            + TABLE_TEXT + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_VIDEO + " INTEGER, "
            + KEY_TITLE + " TEXT, "
            + " FOREIGN KEY ("+KEY_ID_VIDEO+") REFERENCES " + TABLE_VIDEO + "(" +KEY_ID +"))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_COUNTRY);
        db.execSQL(CREATE_TABLE_TOWN);
        db.execSQL(CREATE_TABLE_TOUR);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PATH);
        db.execSQL(CREATE_TABLE_QUIZ);
        db.execSQL(CREATE_TABLE_RESTAURANT);
        db.execSQL(CREATE_TABLE_FEEDBACK);
        db.execSQL(CREATE_TABLE_CULTURAL_HERITAGE);
        db.execSQL(CREATE_TABLE_IMAGE);
        db.execSQL(CREATE_TABLE_VIDEO);
        db.execSQL(CREATE_TABLE_TEXT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOWN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CULTURAL_HERITAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEXT);

        // create new tables
        onCreate(db);
    }

    public double createCountry(Country country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, country.getId());
        values.put(KEY_NAME, country.getName());

        // insert row
        float country_id = db.insert(TABLE_COUNTRY, null, values);

        return country_id;
    }

    public Country getCountry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_COUNTRY + " WHERE "
                + KEY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Country country = new Country();
        country.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        country.setName((c.getString(c.getColumnIndex(KEY_NAME))));

        return country;
    }

    public ArrayList<Country> getAllCountrys() {
        ArrayList<Country> countrys = new ArrayList<Country>();
        String selectQuery = "SELECT  * FROM " + TABLE_COUNTRY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Country country = new Country(c.getInt((c.getColumnIndex(KEY_ID))), c.getString(c.getColumnIndex(KEY_NAME)));

                // adding to todo list
                countrys.add(country);
            } while (c.moveToNext());
        }
        return countrys;
    }

    public double createTown(Town town) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, country.getId());
        values.put(KEY_ID_COUNTRY, town.getId_country());
        values.put(KEY_NAME, town.getName());

        // insert row
        float town_id = db.insert(TABLE_TOWN, null, values);

        return town_id;
    }

    public ArrayList<Town> getAllTowns() {
        ArrayList<Town> towns = new ArrayList<Town>();
        String selectQuery = "SELECT  * FROM " + TABLE_TOWN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Town town = new Town(c.getInt(c.getColumnIndex(KEY_ID)),
                        c.getInt(c.getColumnIndex(KEY_ID_COUNTRY)),
                        c.getString(c.getColumnIndex(KEY_NAME)));

                // adding to todo list
                towns.add(town);
            } while (c.moveToNext());
        }
        return towns;
    }

    public float createTour(Tour tour) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, country.getId());
        values.put(KEY_ID_TOWN, tour.getId_town());
        values.put(KEY_NAME, tour.getName());
        values.put(KEY_CODE, tour.getCode());

        // insert row
        float tour_id = db.insert(TABLE_TOUR, null, values);

        return tour_id;
    }

    public ArrayList<Tour> getAllTours() {
        ArrayList<Tour> tours = new ArrayList<Tour>();
        String selectQuery = "SELECT  * FROM " + TABLE_TOUR;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tour tour = new Tour(c.getInt((c.getColumnIndex(KEY_ID))), c.getInt(c.getColumnIndex(KEY_ID_TOWN)),
                        c.getString(c.getColumnIndex(KEY_NAME)), c.getString(c.getColumnIndex(KEY_CODE)));

                // adding to todo list
                tours.add(tour);
            } while (c.moveToNext());
        }
        return tours;
    }

    public float createPath(Path path) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, country.getId());
        values.put(KEY_ID_TOUR, path.getId_tour());
        values.put(KEY_LAT, path.getLat());
        values.put(KEY_LNG, path.getLng());
        values.put(KEY_IND, path.isInd());

        // insert row
        float path_id = db.insert(TABLE_PATH, null, values);

        return path_id;
    }

    public ArrayList<Path> getAllPaths() {
        ArrayList<Path> paths = new ArrayList<Path>();
        String selectQuery = "SELECT  * FROM " + TABLE_PATH;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Path path = new Path(c.getInt((c.getColumnIndex(KEY_ID))), c.getInt(c.getColumnIndex(KEY_ID_TOUR)),
                        c.getFloat(c.getColumnIndex(KEY_LAT)), c.getFloat(c.getColumnIndex(KEY_LNG)),
                        c.getInt(c.getColumnIndex(KEY_IND)) > 0);

                // adding to todo list
                paths.add(path);
            } while (c.moveToNext());
        }
        return paths;
    }

    public float createCulturalHeritage(CulturalHeritage cultural_heritage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, country.getId());
        values.put(KEY_ID_TOWN, cultural_heritage.getId_town());
        values.put(KEY_ID_TOUR, cultural_heritage.getId_tour());
        values.put(KEY_NAME, cultural_heritage.getName());
        values.put(KEY_LAT, cultural_heritage.getLat());
        values.put(KEY_LNG, cultural_heritage.getLng());
        values.put(KEY_LOGO, cultural_heritage.getLogo());

        // insert row
        float cultural_heritage_id = db.insert(TABLE_CULTURAL_HERITAGE, null, values);

        return cultural_heritage_id;
    }

    public ArrayList<CulturalHeritage> getAllCulturalHeritages() {
        ArrayList<CulturalHeritage> cultural_heritages = new ArrayList<CulturalHeritage>();
        String selectQuery = "SELECT  * FROM " + TABLE_CULTURAL_HERITAGE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                CulturalHeritage cultural_heritage = new CulturalHeritage(c.getInt((c.getColumnIndex(KEY_ID))),
                        c.getInt(c.getColumnIndex(KEY_ID_TOWN)),
                        c.getInt(c.getColumnIndex(KEY_ID_TOUR)),
                        c.getString(c.getColumnIndex(KEY_NAME)),
                        c.getFloat(c.getColumnIndex(KEY_LAT)),
                        c.getFloat(c.getColumnIndex(KEY_LNG)),
                        c.getString(c.getColumnIndex(KEY_LOGO)));

                // adding to todo list
                cultural_heritages.add(cultural_heritage);
            } while (c.moveToNext());
        }
        return cultural_heritages;
    }

    public float createVideo(Video video) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, country.getId());
        values.put(KEY_ID_CULTURAL_HERITAGE, video.getId_cultural_heritage());
        values.put(KEY_NAME, video.getName());
        values.put(KEY_IND, video.isInd());

        // insert row
        float video_id = db.insert(TABLE_VIDEO, null, values);

        return video_id;
    }

    public ArrayList<Video> getAllVideos() {
        ArrayList<Video> videos = new ArrayList<Video>();
        String selectQuery = "SELECT  * FROM " + TABLE_VIDEO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Video video = new Video(c.getInt(c.getColumnIndex(KEY_ID)),
                        c.getInt(c.getColumnIndex(KEY_ID_CULTURAL_HERITAGE)),
                        c.getString(c.getColumnIndex(KEY_NAME)),
                        c.getInt(c.getColumnIndex(KEY_IND)) > 0);

                // adding to todo list
                videos.add(video);
            } while (c.moveToNext());
        }
        return videos;
    }




    //upis u lokalnu bazu
    public double createRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_TOWN, restaurant.getId_town());
        values.put(KEY_NAME, restaurant.getName());
        values.put(KEY_DESCRIPTION, restaurant.getDescription());
        values.put(KEY_LAT, restaurant.getLat());
        values.put(KEY_LNG, restaurant.getLng());
        values.put(KEY_PRICE_LIST, restaurant.getPrice_list());

        // insert row
        float restaurant_id = db.insert(TABLE_RESTAURANT, null, values);
        //vraca -1 ako nije upisao
        return restaurant_id;
    }
    //
    public ArrayList<String> getAllRestaurantsName() {
        ArrayList<String> restaurants = new ArrayList<String>();
        String selectQuery = "SELECT  " + KEY_NAME + " FROM " + TABLE_RESTAURANT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to todo list
                restaurants.add(c.getString(c.getColumnIndex(KEY_NAME)));
            } while (c.moveToNext());
        }
        return restaurants;
    }
    //lista restorana iz lokalne baze
    public ArrayList<Restaurant> getAllRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant(c.getInt(c.getColumnIndex(KEY_ID)),
                        c.getInt(c.getColumnIndex(KEY_ID_TOWN)),
                        c.getString(c.getColumnIndex(KEY_NAME)),
                        c.getString(c.getColumnIndex(KEY_DESCRIPTION)),
                        c.getFloat(c.getColumnIndex(KEY_LAT)),
                        c.getFloat(c.getColumnIndex(KEY_LNG)),
                        c.getString(c.getColumnIndex(KEY_PRICE_LIST)));

                // adding to todo list
                restaurants.add(restaurant);
            } while (c.moveToNext());
        }
        return restaurants;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
