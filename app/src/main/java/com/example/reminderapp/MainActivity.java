package com.example.reminderapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton createRem;
    RecyclerView mRecyclerview;
    //Array list to add reminders and display in recyclerview
    ArrayList<Data> dataHolder = new ArrayList<>();
    myAdapter adapter;
    CardView cardView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //passing toolbar into main activity
        toolbar = findViewById(R.id.cabToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Application");


        mRecyclerview = findViewById(R.id.recView);
        cardView = findViewById(R.id.cd_view);

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
        Cursor cursor = new DataManager(getApplicationContext()).readallreminders();
        while (cursor.moveToNext()) {
            Data data= new Data(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            dataHolder.add(data);
        }

        adapter = new myAdapter(this, dataHolder);
        mRecyclerview.setAdapter(adapter);


    }
    //Makes the user to exit from the app
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }
    //TODO; to add notifications by system sound
}