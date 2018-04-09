package com.ust.thesis.lightsandsockets.objects;

/**
 * Created by BINS on 09/04/2018.
 */

public class RecentActivities {

    private int id;
    private String activity;
    private String time;
    private String username;

    public RecentActivities(int id, String activity, String time, String username){
        this.id = id;
        this.activity = activity;
        this.time = time;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
