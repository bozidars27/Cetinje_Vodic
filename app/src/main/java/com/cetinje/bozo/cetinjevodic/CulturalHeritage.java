package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Petar on 2/11/2017.
 */

public class CulturalHeritage {
    int id;
    int id_tour;
    String name;
    String description;
    float lat;
    float lng;
    String logo;

    public CulturalHeritage() {
    }

    public CulturalHeritage(int id, int id_tour, String name, String description, float lat, float lng, String logo) {
        this.id = id;
        this.id_tour = id_tour;
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tour() {
        return id_tour;
    }

    public void setId_tour(int id_tour) {
        this.id_tour = id_tour;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
