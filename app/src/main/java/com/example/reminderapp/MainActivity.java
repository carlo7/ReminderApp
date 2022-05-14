package com.example.reminderapp;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton createRem;
    RecyclerView mRecyclerview;
    ArrayList<Data> dataHolder = new ArrayList<>();//Array list to add reminders and display in recyclerview
    myAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //passing toolbar into main activity
        toolbar = findViewById(R.id.cabToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("My Application");

        mRecyclerview = findViewById(R.id.recView);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        createRem = findViewById(R.id.addReminder);

        //Redirects to ReminderActivity for Task creation
        createRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ReminderActivity.class));
            }
        });

        //Cursor To Load data From the database
        Cursor cursor = new DataManager(getApplicationContext()).readAllReminders();
        while (cursor.moveToNext()) {
            Data data= new Data(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            dataHolder.add(data);
        }

        adapter = new myAdapter(this, dataHolder);//initialize adapter
        mRecyclerview.setAdapter(adapter);               //set adapter

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //TODO: Implement instance save methods
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //TODO: Implement restore save methods
    }

    //Makes the user to exit from the app
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

}