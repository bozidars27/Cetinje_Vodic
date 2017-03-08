package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Petar on 2/11/2017.
 */

public class CulturalHeritage {
    int id;
    int id_town;
    int id_tour;
    String name;
    float lat;
    float lng;
    String logo;

    public CulturalHeritage() {
    }

    public CulturalHeritage(int id, int id_town, int id_tour, String name, float lat, float lng, String logo) {
        this.id = id;
        this.id_town = id_town;
        this.id_tour = id_tour;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public int getId_town() {
        return id_town;
    }

    public int getId_tour() {
        return id_tour;
    }

    public String getName() {
        return name;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public String getLogo() {
        return logo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_town(int id_town) {
        this.id_town = id_town;
    }

    public void setId_tour(int id_tour) {
        this.id_tour = id_tour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
