package com.cetinje.bozo.cetinjevodic;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DataBaseHandler {

    Context applicationContext;
    //private String server = "http://192.168.100.3/";
    //private String server = "http://89.188.33.189/";
    private String server = "http://192.168.100.5/";

    private RequestQueue requestQueue;
    private String readUrlCountry = server + "countries";
    private String readUrlTown = server + "towns";
    private String readUrlTour = server + "tours";
    private String readUrlPath = server + "paths";
    private String getReadUrlCulturalHeritage = server + "caltural_heritages";
    private String getReadUrlVideo = server + "video";
    private String getReadUrlMap = server + "map";
    private String readUrlRestaurant = server + "restaurant";
    private String readUrlEvents = server + "events";
    private String readUrlGallery = server + "image";
    private String getGalleryImagesUrl = server + "Images/gallery_images.zip";
    private String getLogoImagesUrl = server + "Logos/Logos.zip";
    private String getFeedbacksUrl = server + "feedback";
    private DatabaseHelper db;
    public DataBaseHandler(Context applicationContext, DatabaseHelper db) {
        this.applicationContext = applicationContext;
        this.db = db;
    }


    public void readFromDatabase(String code){

        getReadUrlVideo += "?code=" + code;
        getReadUrlMap += "?code="+code;
        readUrlRestaurant += "?code="+code;
        readUrlPath += "?code="+code;
        getReadUrlCulturalHeritage += "?code="+code;
        readUrlEvents += "?code="+code;
        getFeedbacksUrl += "?code="+code;
        requestQueue = Volley.newRequestQueue(applicationContext);


        InputStreamVolleyRequest logoImagesDownloadRequest = new InputStreamVolleyRequest(Request.Method.GET, getLogoImagesUrl,

                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        try {
                            if(response != null) {
                                String savingPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/logo_images";
                                File dir = new File(savingPath);
                                if (!dir.exists())
                                    dir.mkdir();
                                File imagesSave = new File(dir, "logo_images.zip");
                                if(!imagesSave.exists()) {

                                    try {
                                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imagesSave));
                                        bos.write(response);
                                        bos.flush();
                                        bos.close();

                                    } catch (Exception e) {
                                    }
                                    unzipPrivate(imagesSave, applicationContext);

                                }

                            }


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }, null);


        InputStreamVolleyRequest galleryImagesDownloadRequest = new InputStreamVolleyRequest(Request.Method.GET, getGalleryImagesUrl,

                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {

                        try {
                            if(response != null) {

                                String savingPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gallery_images";
                                File dir = new File(savingPath);
                                File unzipDir = new File(savingPath + "/gallery_images_unzip");

                                if (!dir.exists())
                                    dir.mkdir();

                                File imagesSave = new File(dir, "gallery_images.zip");
                                if(!imagesSave.exists()) {

                                    try {

                                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imagesSave));
                                        bos.write(response);
                                        bos.flush();
                                        bos.close();

                                    } catch (Exception e) {

                                    }

                                    unzip(imagesSave, unzipDir);

                                }

                            }


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }, null);


        //Povlačenje tekstualnih podataka za galeriju
        JsonObjectRequest galleryDataRequest = new JsonObjectRequest(Request.Method.GET, readUrlGallery, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (db.getAllImages().size() == 0) {

                            try {
                                JSONArray galleryDataJsonArray = response.getJSONArray("Images");

                                for (int i = 0; i < galleryDataJsonArray.length(); i++) {
                                    JSONObject galleryDataJsonObject = galleryDataJsonArray.getJSONObject(i);

                                    String imageName = galleryDataJsonObject.getString("image_name");

                                    db.createImage(new Image(imageName, 0,
                                            galleryDataJsonObject.getString("title"),
                                            galleryDataJsonObject.getString("description"),
                                            galleryDataJsonObject.getString("town_name")));

                                    int a = 2;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        JsonObjectRequest jsonObjectRequestCountry = new JsonObjectRequest(Request.Method.GET, readUrlCountry, null,

        new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                        try {
                            JSONArray speciesJsonArray = response.getJSONArray("Countries");
                            if(db.getAllCountrys().size() != speciesJsonArray.length()) {
                                db.reCreateTableCountry();
                                for (int i = 0; i < speciesJsonArray.length(); i++) {
                                    JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                    db.createCountry(new Country(speciesJsonObject.getInt("id"),
                                            speciesJsonObject.getString("name")));
                                }
                            }
                            else
                            {
                                //Toast.makeText(applicationContext, "Baza je ista.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        JsonObjectRequest jsonObjectRequestTown = new JsonObjectRequest(Request.Method.GET, readUrlTown, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                                JSONArray speciesJsonArray = response.getJSONArray("Towns");
                                if(db.getAllTowns().size() != speciesJsonArray.length()) {
                                    db.reCreateTableTown();
                                    for (int i = 0; i < speciesJsonArray.length(); i++) {
                                        JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                        db.createTown(new Town(speciesJsonObject.getInt("id"),
                                                speciesJsonObject.getInt("id_country"),
                                                speciesJsonObject.getString("name")));
                                    }
                                }
                                else
                                {
                                    //Toast.makeText(applicationContext, "Baza je ista.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });



        JsonObjectRequest jsonObjectRequestFeedback = new JsonObjectRequest(Request.Method.GET, getFeedbacksUrl, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray speciesJsonArray = response.getJSONArray("Feedback");
                            if(db.getAllFeedbacks().size() != speciesJsonArray.length()) {
                                db.reCreateTableFeedback();
                                for (int i = 0; i < speciesJsonArray.length(); i++) {
                                    JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                    db.createFeedback(new Feedback(speciesJsonObject.getInt("id"),
                                            speciesJsonObject.getInt("id_restaurant"),
                                            speciesJsonObject.getInt("mark"),
                                            speciesJsonObject.getString("impression")));
                                }
                            }
                            else
                            {
                                //Toast.makeText(applicationContext, "Baza je ista.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        JsonObjectRequest jsonObjectRequestPath = new JsonObjectRequest(Request.Method.GET, readUrlPath, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                                JSONArray speciesJsonArray = response.getJSONArray("Paths");
                                if(db.getAllPaths().size() != speciesJsonArray.length()) {
                                    db.reCreateTablePath();
                                    for (int i = 0; i < speciesJsonArray.length(); i++) {
                                        JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                        db.createPath(new Path(speciesJsonObject.getInt("id"), speciesJsonObject.getInt("id_tour"),
                                                (float) speciesJsonObject.getDouble("lat"), (float) speciesJsonObject.getDouble("lng")));
                                    }
                                }
                                else
                                {
                                    //Toast.makeText(applicationContext, "Baza je ista.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                    e.printStackTrace();
                            }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        JsonObjectRequest jsonObjectRequestCulturalHeritage = new JsonObjectRequest(Request.Method.GET, getReadUrlCulturalHeritage, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                                JSONArray speciesJsonArray = response.getJSONArray("CalturalHeritages");
                                if(db.getAllCulturalHeritages().size() != speciesJsonArray.length()) {
                                    db.reCreateTableCulturalHeritage();
                                    for (int i = 0; i < speciesJsonArray.length(); i++) {
                                        JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                        db.createCulturalHeritage(new CulturalHeritage(speciesJsonObject.getInt("id"),
                                                speciesJsonObject.getInt("id_tour"),
                                                speciesJsonObject.getString("name"),
                                                speciesJsonObject.getString("description"),
                                                (float) speciesJsonObject.getDouble("lat"),
                                                (float) speciesJsonObject.getDouble("lng"),
                                                speciesJsonObject.getString("logo")));
                                    }
                                }
                                else
                                {
                                    //Toast.makeText(applicationContext, "Baza je ista"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        JsonObjectRequest jsonObjectRequestVideo = new JsonObjectRequest(Request.Method.GET, getReadUrlVideo, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                                JSONArray speciesJsonArray = response.getJSONArray("Videos");
                                if(db.getAllVideos().size() != speciesJsonArray.length()) {
                                    db.reCreateTableVideo();
                                    for (int i = 0; i < speciesJsonArray.length(); i++) {
                                        JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                        /*Skidanje fajlova sa servera*/
                                        final String finalname = speciesJsonObject.getString("name");
                                        InputStreamVolleyRequest requestFileVideo = new InputStreamVolleyRequest(Request.Method.GET, server + "Videos/" + speciesJsonObject.getString("name"),
                                                new Response.Listener<byte[]>() {
                                                    @Override
                                                    public void onResponse(byte[] response) {
                                                        // TODO handle the response
                                                        try {
                                                            if (response != null) {

                                                                FileOutputStream outputStream;
                                                                String name = finalname;
                                                                outputStream = applicationContext.openFileOutput(name, Context.MODE_PRIVATE);
                                                                outputStream.write(response);
                                                                outputStream.close();
                                                            }
                                                        } catch (Exception e) {
                                                            // TODO Auto-generated catch block
                                                            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // TODO handle the error
                                                error.printStackTrace();
                                            }
                                        }, null);
                                        //RequestQueue mRequestQueue = Volley.newRequestQueue(applicationContext, new HurlStack()); //za HTTPS konekciju
                                        requestQueue.add(requestFileVideo);

                                        db.createVideo(new Video(speciesJsonObject.getInt("id"),
                                                speciesJsonObject.getInt("id_cultural_heritage"),
                                                speciesJsonObject.getString("name"),
                                                speciesJsonObject.getInt("ind") > 0));
                                    }
                                }
                                else
                                {
                                    //Toast.makeText(applicationContext, "Znamenitost" + String.valueOf(db.getAllCulturalHeritages().size()), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.getMessage());
                    }
                });


        JsonObjectRequest jsonObjectRequestMap = new JsonObjectRequest(Request.Method.GET, getReadUrlMap, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray speciesJsonArray = response.getJSONArray("Town");
                            for (int i = 0; i < speciesJsonArray.length(); i++) {
                                JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                InputStreamVolleyRequest requestFileMap = new InputStreamVolleyRequest(Request.Method.GET, server + "Maps/" +speciesJsonObject.getString("name") + "/Mapa.zip",
                                        new Response.Listener<byte[]>() {
                                            @Override
                                            public void onResponse(byte[] response) {
                                                // TODO handle the response
                                                try {
                                                    if (response!=null) {
                                                        String savingPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/osmdroid";
                                                        File dir = new File(savingPath);
                                                        if (!dir.exists())
                                                            dir.mkdir();
                                                        File mapSave = new File(dir, "Mapa.zip");
                                                        if(!mapSave.exists())
                                                        {
                                                            try {
                                                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(mapSave));
                                                                bos.write(response);
                                                                bos.flush();
                                                                bos.close();
                                                                //Toast.makeText(applicationContext, "Download complete.", Toast.LENGTH_LONG).show();
                                                            } catch (Exception e) {
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    // TODO Auto-generated catch block
                                                    Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                                                    e.printStackTrace();
                                                }
                                            }
                                        } ,new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // TODO handle the error
                                        error.printStackTrace();
                                    }
                                }, null);
                                requestQueue.add(requestFileMap);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        //popunjavanje restorana
        JsonObjectRequest jsonObjectRestaurant = new JsonObjectRequest(Request.Method.GET, readUrlRestaurant, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray speciesJsonArray = response.getJSONArray("Restaurants");
                            if(db.getAllRestaurants().size() != speciesJsonArray.length()) {
                                db.reCreateTableRestaurant();
                                for (int i = 0; i < speciesJsonArray.length(); i++){
                                    JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                    db.createRestaurant(new Restaurant(speciesJsonObject.getInt("id"),
                                            speciesJsonObject.getInt("id_town"),
                                            speciesJsonObject.getString("name"),
                                            speciesJsonObject.getString("description"),
                                            speciesJsonObject.getInt("discount"),
                                            (float) speciesJsonObject.getDouble("lat"),
                                            (float) speciesJsonObject.getDouble("lng"),
                                            null,
                                            speciesJsonObject.getString("logo")
                                    ));
                                }
                            }
                            else
                            {
                                //Toast.makeText(applicationContext, "Baza je vec puna.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("greska", e.getMessage());
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //popunjavanje dogadjaja
        JsonObjectRequest jsonObjectEvent = new JsonObjectRequest(Request.Method.GET, readUrlEvents, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray speciesJsonArray = response.getJSONArray("Events");
                            if(db.getAllEvents().size() != speciesJsonArray.length()) {
                                db.reCreateTableEvent();
                                for (int i = 0; i < speciesJsonArray.length(); i++){
                                    JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                    db.createEvent(new Events(speciesJsonObject.getInt("id"),
                                            speciesJsonObject.getInt("id_town"),
                                            speciesJsonObject.getString("name"),
                                            speciesJsonObject.getString("description"),
                                            speciesJsonObject.getString("date_time")
                                    ));
                                }
                            }
                            else
                            {
                                //Toast.makeText(applicationContext, "Baza je vec puna.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("greska", e.getMessage());
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


        //RequestQueue mRequestQueue = Volley.newRequestQueue(applicationContext, new HurlStack()); //za HTTPS konekciju
        requestQueue.add(jsonObjectRequestCountry);
        requestQueue.add(jsonObjectRequestTown);
        requestQueue.add(jsonObjectRequestPath);
        requestQueue.add(jsonObjectRequestCulturalHeritage);
        requestQueue.add(jsonObjectRequestVideo);
        requestQueue.add(jsonObjectRequestMap);
        requestQueue.add(jsonObjectRestaurant);
        requestQueue.add(jsonObjectEvent);
        requestQueue.add(galleryImagesDownloadRequest);
        requestQueue.add(galleryDataRequest);
        requestQueue.add(logoImagesDownloadRequest);
        requestQueue.add(jsonObjectRequestFeedback);

    }

    public void getTour(){
        requestQueue = Volley.newRequestQueue(applicationContext);
        JsonObjectRequest jsonObjectRequestTour = new JsonObjectRequest(Request.Method.GET, readUrlTour, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray speciesJsonArray = response.getJSONArray("Tours");
                            if(db.getAllTours().size() != speciesJsonArray.length()) {
                                db.reCreateTableTour();
                                for (int i = 0; i < speciesJsonArray.length(); i++) {
                                    JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                    db.createTour(new Tour(speciesJsonObject.getInt("id"),
                                            speciesJsonObject.getInt("id_town"),
                                            speciesJsonObject.getString("name"),
                                            speciesJsonObject.getString("code"),
                                            speciesJsonObject.getInt("ind") > 0));
                                }
                            }
                            else
                            {
                                //Toast.makeText(applicationContext, "Baza je ista.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonObjectRequestTour);
    }

    public void insertIntoDatabase(){

        requestQueue = Volley.newRequestQueue(applicationContext);
    }

    public static void unzip(File zipFile, File targetDirectory) throws IOException {

        ZipInputStream zipInputStream = new ZipInputStream( new BufferedInputStream( new FileInputStream(zipFile) ) );

        try {

            ZipEntry zipEntry;
            int count;

            byte[] buffer = new byte[8192];

            if (!targetDirectory.exists())
                targetDirectory.mkdir();

            while( (zipEntry = zipInputStream.getNextEntry()) != null ) {

                File file = new File(targetDirectory, zipEntry.getName());
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                try {
                    while ( (count = zipInputStream.read(buffer)) != -1 ) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                } finally {
                    fileOutputStream.close();
                }

            }

        } finally{
            zipInputStream.close();
        }

    }

    public static void unzipPrivate(File zipFile, Context applicationContext) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream( new BufferedInputStream( new FileInputStream(zipFile) ) );

        try {

            ZipEntry zipEntry;
            int count;

            byte[] buffer = new byte[8192];

            while( (zipEntry = zipInputStream.getNextEntry()) != null ) {

                String name = zipEntry.getName();
                FileOutputStream fileOutputStream = applicationContext.openFileOutput(name, Context.MODE_PRIVATE);

                try {
                    while ( (count = zipInputStream.read(buffer)) != -1 ) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                } finally {
                    fileOutputStream.close();
                }

            }

        } finally{
            zipInputStream.close();
            //FileUtils.deleteDirectory(zipFile);
        }

    }
}
