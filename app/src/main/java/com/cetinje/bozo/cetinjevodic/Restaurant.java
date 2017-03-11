package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Petar on 2/11/2017.
 */

public class Restaurant {
    int id;
    int id_town;
    String name;
    String description;
    float lat;
    float lng;
    String price_list; // putanja do cjenovnika

    public Restaurant() {
    }
    public Restaurant(int id, int id_town, String name, String description, float lat, float lng, String price_list) {
        this.id = id;
        this.id_town = id_town;
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
        this.price_list = price_list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_town() {
        return id_town;
    }

    public void setId_town(int id_town) {
        this.id_town = id_town;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getPrice_list() {
        return price_list;
    }

    public void setPrice_list(String price_list) {
        this.price_list = price_list;
    }
}
