package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Timer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //after 500 milliseconds this block calls the mainActivity
                startActivity(new Intent(Timer.this, MainActivity.class));
                finish();
            }
        }, secondsDelayed * 500);
    }
}
