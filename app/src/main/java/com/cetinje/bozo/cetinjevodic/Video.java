package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Petar on 2/11/2017.
 */

public class Video {
    int id;
    int id_cultural_heritage;
    String name;
    boolean ind;

    public Video() {

    }

    public Video(int id, int id_cultural_heritage, String name, boolean ind) {
        this.id = id;
        this.id_cultural_heritage = id_cultural_heritage;
        this.name = name;
        this.ind = ind;
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

    public boolean isInd() {
        return ind;
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

    public void setInd(boolean ind) {
        this.ind = ind;
    }
}
