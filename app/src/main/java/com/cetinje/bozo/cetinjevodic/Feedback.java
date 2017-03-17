package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Petar on 2/11/2017.
 */

public class Feedback {
    int id;
    int id_restaurant;
    int mark;
    String impression;

    public Feedback() {
    }

    public Feedback(int id, int id_restaurant, int mark, String impression) {
        this.id = id;
        this.id_restaurant = id_restaurant;
        this.mark = mark;
        this.impression = impression;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(int id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }
}
