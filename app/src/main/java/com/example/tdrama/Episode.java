package com.example.tdrama;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Episode implements Serializable {
    @Exclude
    private String id;
    private String number,server1,server2,dramaId;

    public Episode() {
    }

    public Episode(String id, String number, String server1, String server2, String dramaId) {
        this.id = id;
        this.number = number;
        this.server1 = server1;
        this.server2 = server2;
        this.dramaId = dramaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getServer1() {
        return server1;
    }

    public void setServer1(String server1) {
        this.server1 = server1;
    }

    public String getServer2() {
        return server2;
    }

    public void setServer2(String server2) {
        this.server2 = server2;
    }

    public String getDramaId() {
        return dramaId;
    }

    public void setDramaId(String dramaId) {
        this.dramaId = dramaId;
    }
}
