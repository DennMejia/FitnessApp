package com.example.cmsc355androidproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Timer;
import java.util.TimerTask;

public class TrackActivity extends AppCompatActivity {

    TextView timerText;
    TextView textViewCalories;
    ImageButton buttonStartPause;
    ImageButton stopButton;
    Double time = 0.0;
    Timer timer;
    TimerTask timerTask;
    boolean timerStarted;
    int METValue;
    private workoutTable db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        timerText = (TextView)findViewById(R.id.textViewTimer);
        textViewCalories = (TextView)findViewById(R.id.textViewCalories);
        buttonStartPause = (ImageButton)findViewById(R.id.buttonStartPause);
        stopButton = (ImageButton)findViewById(R.id.StopButton);
        timer = new Timer();
    }

    //Pause/Play button on TrackActivity
    public void PauseButton(View view) {
        //if timer is stopped, start timer when you click on button, change button to pause
        if(timerStarted == false) {
            timerStarted = true;
            buttonStartPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            startTimer();
        }
        //else if timer is running, stop timer when you click on button, change button to play
        else {
            timerStarted = false;
            buttonStartPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
            timerTask.cancel();
        }
    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                        textViewCalories.setText(getCaloriesBurnedText());
                    }
                });

            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);
        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
    }

    //Stop button stops com.example.cmsc355androidproject.workout
    public void StopButton(View view) {
        //stop timer
        timerStarted = false;
        timerTask.cancel();
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        //get user data from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("sp_userData", Context.MODE_PRIVATE);
        String[] MetValueStrings = getResources().getStringArray(R.array.workouts);
        Intent intent = getIntent();
        //get position of com.example.cmsc355androidproject.workout selected from List
        int MetValuePosition = intent.getIntExtra(WorkoutList.METValuePosition,0);
        //get database information
        db = new workoutTable(getApplicationContext());
        int id = db.getAllWorkouts().size() + 1;
        //create new workout and add to database
        Workout workout = new Workout(id,MetValueStrings[MetValuePosition],(int)getCaloriesBurned(),minutes);
        db.addWorkout(workout);
        WorkoutAdapter wa = new WorkoutAdapter(db.getAllWorkouts());
        finish();
    }

    public double getUserWeight() {
        //get user data from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("sp_userData", Context.MODE_PRIVATE);
        //cast weight to double
        double userWeight = sharedPreferences.getInt("userWeight",0);
        //convert weight to kgs
        double userWeightkgs = userWeight / 2.2046;
        return userWeightkgs;
    }

    private float getMetValue() {
        //get String array of MET Values
        String[] MetValueStrings = getResources().getStringArray(R.array.METValues);
        Intent intent = getIntent();
        //get position of com.example.cmsc355androidproject.workout selected from List
        int MetValuePosition = intent.getIntExtra(WorkoutList.METValuePosition,0);
        //cast MET Value from array into float
        float METValue = Float.parseFloat(MetValueStrings[MetValuePosition]);
        return METValue;
    }

    private String getCaloriesBurnedText() {
        return formatCalories(getCaloriesBurned());
    }

    private double getCaloriesBurned() {
        double caloriesBurned = (((3.5 * getUserWeight() * getMetValue() )/200)/60) * time;
        return caloriesBurned;
    }

    private String formatCalories(double caloriesBurned) {
        if (caloriesBurned < 10) {
            return String.format("%01d", (int) caloriesBurned);
        }
        else if (caloriesBurned < 100) {
            return String.format("%02d", (int) caloriesBurned);
        }
        else if (caloriesBurned < 1000) {
            return String.format("%03d", (int) caloriesBurned);
        }
        else if (caloriesBurned < 10000) {
            return String.format("%04d", (int) caloriesBurned);
        }
        else {
            return String.format("%05d", (int) caloriesBurned);
        }

    }

}

