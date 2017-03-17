package com.cetinje.bozo.cetinjevodic;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Petar on 2/11/2017.
 */

public class Restaurant {
    int id;
    int id_town;
    String name;
    String description;
    int discount;
    float lat;
    float lng;
    ArrayList<Feedback> feedbacks;
    String logo; // putanja do loga

    public Restaurant() {
    }

    public Restaurant(int id, int id_town, String name, String description, int discount, float lat, float lng, ArrayList<Feedback> feedbacks, String logo) {
        this.id = id;
        this.id_town = id_town;
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.lat = lat;
        this.lng = lng;
        this.feedbacks = feedbacks;
        this.logo = logo;
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
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

    public ArrayList<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(ArrayList<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public float getAverageMark(){
        float sum = 0;
        for(Feedback f:this.feedbacks)
        {
            sum += f.getMark();
        }
        return (this.feedbacks.size()!=0) ? sum/this.feedbacks.size():0;
    }
}
