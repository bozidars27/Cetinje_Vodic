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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DataBaseHandler {

    Context applicationContext;
    //private String server = "http://192.168.100.3/";
    //private String server = "http://89.188.33.100/";
    private String server = "http://192.168.0.101/";

    private RequestQueue requestQueue;
    private String readUrlCountry = server + "countries";
    private String readUrlTown = server + "towns?id=1";
    private String readUrlTour = server + "tours";
    private String readUrlPath = server + "paths?id=1";
    private String getReadUrlCulturalHeritage = server + "caltural_heritages?id_town=1&id_tour=1";
    private String getReadUrlVideo = server + "videos";
    private DatabaseHelper db;
    private ArrayList<Video> videos;
    public DataBaseHandler(Context applicationContext, DatabaseHelper db) {
        this.applicationContext = applicationContext;
        this.db = db;
    }


    public void readFromDatabase(){

        requestQueue = Volley.newRequestQueue(applicationContext);

        JsonObjectRequest jsonObjectRequestCountry = new JsonObjectRequest(Request.Method.GET, readUrlCountry, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(db.getAllCountrys().size() == 0)
                        {
                            //Toast.makeText(applicationContext, "Drzave" + String.valueOf(db.getAllCountrys().size()), Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray speciesJsonArray = response.getJSONArray("Countries");
                                for (int i = 0; i < speciesJsonArray.length(); i++){
                                    JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                    db.createCountry(new Country(speciesJsonObject.getInt("id"),
                                            speciesJsonObject.getString("name")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            //Toast.makeText(applicationContext, "Baza je vec puna.", Toast.LENGTH_SHORT).show();
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
                        if(db.getAllTowns().size() == 0)
                        {
                            //Toast.makeText(applicationContext, "Grad" + String.valueOf(db.getAllTowns().size()), Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray speciesJsonArray = response.getJSONArray("Towns");
                                for (int i = 0; i < speciesJsonArray.length(); i++){
                                    JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                    db.createTown(new Town(speciesJsonObject.getInt("id"),
                                            speciesJsonObject.getInt("id_country"),
                                            speciesJsonObject.getString("name")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            //Toast.makeText(applicationContext, "Baza je vec puna.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        JsonObjectRequest jsonObjectRequestTour = new JsonObjectRequest(Request.Method.GET, readUrlTour, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(db.getAllTours().size() == 0)
                        {
                            //Toast.makeText(applicationContext, "Tura" +  String.valueOf(db.getAllTours().size()), Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray speciesJsonArray = response.getJSONArray("Tours");
                                for (int i = 0; i < speciesJsonArray.length(); i++){
                                    JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                    db.createTour(new Tour(speciesJsonObject.getInt("id"),
                                            speciesJsonObject.getInt("id_town"),
                                            speciesJsonObject.getString("name"),
                                            speciesJsonObject.getString("code")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            //Toast.makeText(applicationContext, "Baza je vec puna.", Toast.LENGTH_SHORT).show();
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
                        if(db.getAllPaths().size() == 0)
                        {
                            //Toast.makeText(applicationContext, "Putanja" + String.valueOf(db.getAllPaths().size()), Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray speciesJsonArray = response.getJSONArray("Paths");
                                for (int i = 0; i < speciesJsonArray.length(); i++){
                                    JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                    db.createPath(new Path(speciesJsonObject.getInt("id"), speciesJsonObject.getInt("id_tour"),
                                            (float) speciesJsonObject.getDouble("lat"), (float) speciesJsonObject.getDouble("lng"),
                                            speciesJsonObject.getInt("ind") > 0));
                                }
                            } catch (JSONException e) {
                                    e.printStackTrace();
                            }
                        }
                        else
                        {
                            //Toast.makeText(applicationContext, "Baza je vec puna.", Toast.LENGTH_SHORT).show();
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
                        if(db.getAllCulturalHeritages().size() == 0)
                        {
                            //Toast.makeText(applicationContext, "Znamenitost" + String.valueOf(db.getAllCulturalHeritages().size()), Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray speciesJsonArray = response.getJSONArray("CalturalHeritages");
                                for (int i = 0; i < speciesJsonArray.length(); i++){
                                    JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                    db.createCulturalHeritage(new CulturalHeritage(speciesJsonObject.getInt("id"),
                                            speciesJsonObject.getInt("id_town"),
                                            speciesJsonObject.getInt("id_tour"),
                                            speciesJsonObject.getString("name"),
                                            (float) speciesJsonObject.getDouble("lat"),
                                            (float) speciesJsonObject.getDouble("lng"),
                                            speciesJsonObject.getString("logo")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            //Toast.makeText(applicationContext, "Znamenitost" + String.valueOf(db.getAllCulturalHeritages().size()), Toast.LENGTH_SHORT).show();
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
                        if(db.getAllVideos().size() == 0)
                        {
                            try {
                                JSONArray speciesJsonArray = response.getJSONArray("Videos");
                                for (int i = 0; i < speciesJsonArray.length(); i++){
                                    JSONObject speciesJsonObject = speciesJsonArray.getJSONObject(i);
                                    //Toast.makeText(applicationContext, speciesJsonObject.getString("name"), Toast.LENGTH_SHORT).show();

                                    /*Skidanje fajlova sa servera*/
                                    final String finalname = speciesJsonObject.getString("name");
                                    InputStreamVolleyRequest requestFileVideo = new InputStreamVolleyRequest(Request.Method.GET, server + "Cetinje/" + speciesJsonObject.getString("name"),
                                            new Response.Listener<byte[]>() {
                                                @Override
                                                public void onResponse(byte[] response) {
                                                    // TODO handle the response
                                                    try {
                                                        if (response!=null) {

                                                            FileOutputStream outputStream;
                                                            String name=finalname;
                                                            outputStream = applicationContext.openFileOutput(name, Context.MODE_PRIVATE);
                                                            outputStream.write(response);
                                                            outputStream.close();
                                                            Toast.makeText(applicationContext, "Download complete.", Toast.LENGTH_LONG).show();
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
                                    //RequestQueue mRequestQueue = Volley.newRequestQueue(applicationContext, new HurlStack()); //za HTTPS konekciju
                                    requestQueue.add(requestFileVideo);

                                    db.createVideo(new Video(speciesJsonObject.getInt("id"),
                                            speciesJsonObject.getInt("id_cultural_heritage"),
                                            speciesJsonObject.getString("name"),
                                            speciesJsonObject.getInt("ind") > 0));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            //Toast.makeText(applicationContext, "Znamenitost" + String.valueOf(db.getAllCulturalHeritages().size()), Toast.LENGTH_SHORT).show();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        InputStreamVolleyRequest requestFileMap = new InputStreamVolleyRequest(Request.Method.GET, server + "Cetinje/mapa/Mapa.zip",
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
        //RequestQueue mRequestQueue = Volley.newRequestQueue(applicationContext, new HurlStack()); //za HTTPS konekciju
        requestQueue.add(requestFileMap);


        requestQueue.add(jsonObjectRequestCountry);
        requestQueue.add(jsonObjectRequestTown);
        requestQueue.add(jsonObjectRequestTour);
        requestQueue.add(jsonObjectRequestPath);
        requestQueue.add(jsonObjectRequestCulturalHeritage);
        requestQueue.add(jsonObjectRequestVideo);

    }

    public void insertIntoDatabase(){

        requestQueue = Volley.newRequestQueue(applicationContext);
    }
}
