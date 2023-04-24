package com.example.cmsc355androidproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private int pos = 0;
    private workoutTable db;
    private RecyclerView workoutView;

    public HomeFragment(){
    }

    //sets the view fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        db = new workoutTable(getContext());
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        TextView header = rootView.findViewById(R.id.headers);
        FloatingActionButton btn = rootView.findViewById(R.id.fab);

        //listener for the floating button
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showPopup(v, btn);
            }
        });

        workoutView = rootView.findViewById(R.id.workoutView);
        WorkoutAdapter wa = new WorkoutAdapter(db.getAllWorkouts());
        workoutView.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutView.setItemAnimator(new DefaultItemAnimator());
        workoutView.setAdapter(wa);
        return rootView;

    }

    //shows the popup menu with adding or tracking buttons
    public void showPopup(View v, FloatingActionButton btn){
        PopupWindow popup = new PopupWindow(btn);
        //inflates the layout
        LayoutInflater inflater = (LayoutInflater)   getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup, null);
        popup.setFocusable(true);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setContentView(view);
        //places the windows above the floating button
        //positioning acts weird and is mostly trial and error from what I have tested
        popup.showAsDropDown(btn, -45, -390);
        //Create button to track activity
        Button trackButton = (Button)view.findViewById(R.id.button1);
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //When clicking Track Button, takes you to ListView to select com.example.cmsc355androidproject.workout
            public void onClick(View view) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), WorkoutList.class);
                startActivity(intent);
            }
        });

        Button addButton = view.findViewById(R.id.button2);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
                showAddWorkout(view);
            }
        });
    }

    public void showAddWorkout(View v){
        PopupWindow popup = new PopupWindow();
        //inflates the layout
        LayoutInflater inflater = (LayoutInflater)   getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_workout_popup, null);

        popup.setFocusable(true);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setContentView(view);
        popup.showAtLocation(getView(), Gravity.CENTER, 50, 0);

        Spinner spin = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.workouts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                System.out.println(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 popup.dismiss();
             }
         });

        Button addButton = view.findViewById(R.id.button4);
        TextInputEditText minuteBox = view.findViewById(R.id.minutesText);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //retrives the array of met values
                String[] metArray = getResources().getStringArray(R.array.METValues);
                //retrives the array of workout names
                String[] workoutArray = getResources().getStringArray(R.array.workouts);
                //retrives the metvalue at pos
                double metVal = Double.parseDouble(metArray[pos]);
                //retrives the workout name at pos
                String name = workoutArray[pos];
                //gets the shared preferences
                SharedPreferences sharedPref = getActivity().getSharedPreferences("sp_userData", Context.MODE_PRIVATE);
                //gets the user inputted time from the text box
                int time = Integer.parseInt(minuteBox.getText().toString());
                //calculates the calories of the exercise
                int cals = (int) (metVal * 3.5 * sharedPref.getInt("userWeight", 0)/2.205/200 * time);
                //assign an id to the workout based on size
                int id = db.getAllWorkouts().size() + 1;
                //makes the new workout object
                Workout workout = new Workout(id, name, cals, time);
                //adds the workout to the database
                db.addWorkout(workout);
                //closes the popup
                WorkoutAdapter wa = new WorkoutAdapter(db.getAllWorkouts());
                workoutView.setAdapter(wa);
                List<Workout> list = db.getAllWorkouts();
                for(int i = 0; i<list.size(); i++){
                    System.out.println(list.get(i).getName());
                }
                popup.dismiss();
            }
        });
    }
}
