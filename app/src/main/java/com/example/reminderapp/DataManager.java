package com.example.reminderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataManager extends SQLiteOpenHelper {
    private static final String dbname = "reminder";//Table  name to store reminders in sqllite

    public DataManager(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sql query to insert data in sqllite
        String query = "create table tbl_reminder(id integer primary key autoincrement,title text,date text,time text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String query = "DROP TABLE IF EXISTS tbl_reminder";//sql query to check table with the same name or not
        sqLiteDatabase.execSQL(query); //executes the sql command
        onCreate(sqLiteDatabase);

    }

    public String addReminder(String title, String date, String time) {
        SQLiteDatabase database = this.getReadableDatabase();

        //Inserts  data into sqllite database
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("date", date);
        contentValues.put("time", time);


        //returns -1 if data successfully inserts into database
        float result = database.insert("tbl_reminder", null, contentValues);

        if (result == -1) {
            return "Failed! Please try again";
        } else {
            return "Successfully inserted";
        }

    }

    public Cursor readallreminders() {
        SQLiteDatabase database = this.getWritableDatabase();

        //Sql query to  retrieve  data from the database
        String query = "select * from tbl_reminder order by id desc";
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }
}
