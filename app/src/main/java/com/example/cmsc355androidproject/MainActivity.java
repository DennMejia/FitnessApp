package com.example.cmsc355androidproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.widget.TextView;

import java.util.Calendar;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    workoutTable db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the content of the main view with the main display
        setContentView(R.layout.activity_main);

        //create a new bottom nav panel
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //we add a listener for the different items
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //set the default selection for the bottom nav
        bottomNavigationView.setSelectedItemId(R.id.home);


    }
        //create the different pages of the nav to switch between
        HomeFragment homeFragment = new HomeFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        SettingsFragment settingsFragment = new SettingsFragment();

        //switches between the pages when the nav is selected
        public boolean onNavigationItemSelected(MenuItem item){
            switch(item.getItemId()){
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                    return true;
                case R.id.profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, profileFragment).commit();
                    return true;
                case R.id.settings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, settingsFragment).commit();
                    return true;
            }
            return false;
        }

    public void createNotification(View view) {
            Intent myIntent = new Intent(getApplicationContext(), NotifyService.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.AM_PM, Calendar.AM);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            alarmManager.setRepeating(AlarmManager. RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
    }
}