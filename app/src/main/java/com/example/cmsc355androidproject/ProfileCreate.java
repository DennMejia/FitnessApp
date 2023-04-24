package com.example.cmsc355androidproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProfileCreate extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_create);                                           //sets the content to the profile create layout

        Button btn = findViewById(R.id.button3);                                                    //finds the button that is in the xml

        TextInputEditText nameEdit = findViewById(R.id.nameTextEdit);                               //finds all the text fields in the xml
        TextInputEditText weightEdit = findViewById(R.id.weightTextEdit);
        TextInputEditText ageEdit = findViewById(R.id.ageTextEdit);
        TextInputEditText ftEdit = findViewById(R.id.heightftTextEdit);
        TextInputEditText inEdit = findViewById(R.id.heightinTextEdit);
        btn.setOnClickListener(new View.OnClickListener(){                                          //we set a listener to the button in the xml
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v){
                String name = nameEdit.getText().toString();                                        //get get the text from the first text field
                int weight, age;
                try{
                    weight = Integer.parseInt(weightEdit.getText().toString());                     //we do a try catch to make sure that a valid number is entered
                    age = Integer.parseInt(ageEdit.getText().toString());
                    float ft =  Integer.parseInt(ftEdit.getText().toString());                      //we do a try catch to make sure that a valid number is entered
                    float in =  Integer.parseInt(inEdit.getText().toString());

                    float height = ft + in/12.0f;
                    System.out.println(name + " " + weight + " " + age + " " );

                    SharedPreferences sharedPref = getSharedPreferences("sp_userData",Context.MODE_PRIVATE); //The we put all the values we got into the preferences to share and use
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putString("userName", name);                                             //we put the values in with their keys
                    editor.putInt("userAge", age);
                    editor.putInt("userWeight", weight);
                    editor.putFloat("userHeight", height);
                    editor.commit();                                                                //we then need to commit the changes from the editor

                    startActivity(new Intent(ProfileCreate.this, MainActivity.class));
                    finish();
                }catch (NumberFormatException e){
                    System.out.println("oh no error");
                    e.printStackTrace();
                }
            }
        });
    }
}
