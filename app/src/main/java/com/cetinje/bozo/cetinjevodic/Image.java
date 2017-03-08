package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Petar on 2/11/2017.
 */

public class Image {
    int id;
    int id_cultural_heritage;
    String name;
    String description;
    String cityName;

    public Image() {
    }

    public Image(int id, int id_cultural_heritage, String name, String description, String cityName) {
        this.id = id;
        this.id_cultural_heritage = id_cultural_heritage;
        this.name = name;
        this.description = description;
        this.cityName = cityName;

    }

    public int getId() {
        return id;
    }

    public int getId_cultural_heritage() {
        return id_cultural_heritage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCityName() {
        return cityName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_cultural_heritage(int id_cultural_heritage) {
        this.id_cultural_heritage = id_cultural_heritage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
