package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Petar on 2/11/2017.
 */

public class Path {
    int id;
    int id_tour;
    float lat;
    float lng;
    boolean ind;

    public Path() {
    }

    public Path(int id, int id_tour, float lat, float lng, boolean ind) {
        this.id = id;
        this.id_tour = id_tour;
        this.lat = lat;
        this.lng = lng;
        this.ind = ind;
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

    public boolean isInd() {
        return ind;
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

    public void setInd(boolean ind) {
        this.ind = ind;
    }
}
