package com.example.reminderapp;

public class Data {
    String title, date, time;


     //Data constructors
    public Data(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
