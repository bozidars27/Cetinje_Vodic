package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Milija on 3/16/2017.
 */

public class Events {
    int id;
    int id_town;
    String name;
    String description;
    String date_time;

    public Events() {
    }

    public Events(int id, int id_town, String name, String description, String date_time) {
        this.id = id;
        this.id_town = id_town;
        this.name = name;
        this.description = description;
        this.date_time = date_time;
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

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
