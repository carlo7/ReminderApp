package com.example.reminderapp;

public class Data {
    private static Data value;
    String title, date, time;


     //Data constructors
    public Data(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;
    }
   //Data variable getters and setters
    public static Data valueOf(int size) {
        return value;
    }

    public static void setValue(Data value) {
        Data.value = value;
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
