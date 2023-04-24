package com.example.cmsc355androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.io.File;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getSharedPreferences("sp_userData",Context.MODE_PRIVATE);

        if(sharedPref.contains("userName"))                                                         //we check if the key is present if not then the profile wasnt made
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        else
            startActivity(new Intent(SplashScreenActivity.this, ProfileCreate.class));
        finish();
    }
}