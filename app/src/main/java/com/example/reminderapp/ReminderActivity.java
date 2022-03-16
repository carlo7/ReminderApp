package com.example.reminderapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Date;


//this class takes input(s) from the user and inserts into database
public class ReminderActivity extends AppCompatActivity {
    Button btSubmit, btDate, btTime;
    EditText edtTitle;
    String timeToNotify;
    GregorianCalendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        //assigned all the material reference to get and set data
        edtTitle = findViewById(R.id.editTitle);
        btDate = findViewById(R.id.btnDate);
        btTime = findViewById(R.id.btnTime);
        btSubmit = findViewById(R.id.btnSubmit);


        btTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//when we click on the choose time button it calls the select time method
                selectTime();
            }
        });

        btDate.setOnClickListener(new View.OnClickListener() {
            //when we click on the choose date button it calls the select date method
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString().trim();//access the data form the input field
                String date = btDate.getText().toString().trim();//access the date form the choose date button
                String time = btTime.getText().toString().trim();//access the time form the choose time button

                if (title.isEmpty()) {
                    Toast.makeText(ReminderActivity.this, "Please Enter text", Toast.LENGTH_SHORT).show();
                } else {
                    if (time.equals("time") || date.equals("date")) {
                        Toast.makeText(ReminderActivity.this, "Please select date and time", Toast.LENGTH_SHORT).show();
                    } else {
                        processInsert(title, date, time);

                    }
                }


            }
        });
    }


    private void processInsert(String title, String date, String time) {
        String result = new DataManager(this).addReminder(title, date, time);                  //inserts the title,date,time into sql lite database
        setAlarm(title, date, time);                                                                //calls the set alarm method to set alarm
        edtTitle.setText("");
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }

    private void selectTime() {                                                                     //this method performs the time picker task
        calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int hour = calendar.get(GregorianCalendar.HOUR_OF_DAY);
        int minute = calendar.get(GregorianCalendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeToNotify = i + ":" + i1;                                                        //temp variable to store the time to set alarm
                btTime.setText(FormatTime(i, i1));                                                //sets the button text as selected time
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    private void selectDate() {                                                                     //this method performs the date picker task
        calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int year = calendar.get(GregorianCalendar.YEAR);
        int month = calendar.get(GregorianCalendar.MONTH);
        int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                btDate.setText(day + "-" + (month + 1) + "-" + year);                             //sets the selected date as test for button
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public String FormatTime(int hour, int minute) {                                                //this method converts the time into 12hr farmat and assigns am or pm

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }


    private void setAlarm(String text, String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);                   //assigining alaram manager object to set alaram

        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
        intent.putExtra("event", text);                                                       //sending data to alarm class to create channel and notification
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + timeToNotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
            Toast.makeText(getApplicationContext(), "Alarm created", Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intentBack = new Intent(getApplicationContext(), MainActivity.class);                //this intent will be called once the setting alaram is completes
        intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentBack);                                                                  //navigates from adding reminder activity ot mainactivity

    }
}
