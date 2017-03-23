package com.cetinje.bozo.cetinjevodic;

/**
 * Created by Petar on 2/11/2017.
 */

public class Quiz {
    int id;
    int id_tour;
    String question;
    int type; // 1 predstavlja opciono pitanje, dok 2 predstavlja numericko pitanje
    String Tans;
    String Fans1;
    String Fans2;
    String Fans3;

    public Quiz(int id, int id_tour, String question, int type, String tans, String fans1, String fans2, String fans3) {
        this.id = id;
        this.id_tour = id_tour;
        this.question = question;
        this.type = type;
        Tans = tans;
        Fans1 = fans1;
        Fans2 = fans2;
        Fans3 = fans3;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTans() {
        return Tans;
    }

    public void setTans(String tans) {
        Tans = tans;
    }

    public String getFans1() {
        return Fans1;
    }

    public void setFans1(String fans1) {
        Fans1 = fans1;
    }

    public String getFans2() {
        return Fans2;
    }

    public void setFans2(String fans2) {
        Fans2 = fans2;
    }

    public String getFans3() {
        return Fans3;
    }

    public void setFans3(String fans3) {
        Fans3 = fans3;
    }
}


