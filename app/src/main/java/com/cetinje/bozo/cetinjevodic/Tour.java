package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Petar on 2/11/2017.
 */

public class Tour {
    int id;
    int id_town;
    String name;
    String code;

    public Tour() {
    }

    public Tour(int id, int id_town, String name, String code) {
        this.id = id;
        this.id_town = id_town;
        this.name = name;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public int getId_town() {
        return id_town;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_town(int id_town) {
        this.id_town = id_town;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
