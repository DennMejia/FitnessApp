package com.example.cmsc355androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WorkoutList extends AppCompatActivity {
    public static final String METValuePosition = "com.example.cmsc355androidproject.METValuePosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);
        //get array of workouts from values/arrays.xml into String array
        String[] workouts = getResources().getStringArray(R.array.workouts);
        //Create ListView of all workouts
        ListView listView = findViewById(R.id.listofworkouts);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,workouts);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int position = i;
                Intent intent = new Intent(WorkoutList.this, TrackActivity.class);
                //pass the position of the com.example.cmsc355androidproject.workout clicked from listview to TrackActivity
                intent.putExtra(METValuePosition, position);
                //After selecting com.example.cmsc355androidproject.workout, begin tracking
                startActivity(intent);
            }
        });

    }
}