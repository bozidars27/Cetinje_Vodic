package com.cetinje.bozo.cetinjevodic;

import java.util.ArrayList;
/*
* Bozova klasa za galeriju ;)
* */
public class City {

    private String name;
    private ArrayList<Image> pictures;

    public City() {
        this.pictures = new ArrayList<>();
    }
    public City(String name, ArrayList<Image> pictures) {
        this.name = name;
        this.pictures = pictures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Image> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<Image> pictures) {
        this.pictures = pictures;
    }
}
