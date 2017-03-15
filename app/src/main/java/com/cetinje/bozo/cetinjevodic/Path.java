package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Petar on 2/11/2017.
 */

public class Path {
    int id;
    int id_tour;
    float lat;
    float lng;

    public Path() {
    }

    public Path(int id, int id_tour, float lat, float lng) {
        this.id = id;
        this.id_tour = id_tour;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public int getId_tour() {
        return id_tour;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_tour(int id_tour) {
        this.id_tour = id_tour;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
