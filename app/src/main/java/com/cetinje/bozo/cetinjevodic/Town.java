package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Petar on 2/11/2017.
 */

public class Town {
    int id;
    int id_country;
    String name;

    public Town() {
    }

    public Town(int id, int id_country, String name) {
        this.id = id;
        this.id_country = id_country;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getId_country() {
        return id_country;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_country(int id_country) {
        this.id_country = id_country;
    }

    public void setName(String name) {
        this.name = name;
    }
}
