package com.example.cmsc355androidproject;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.text.NumberFormat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    //used to mock database, sharedpreferences
    private HomeFragment fragment;
    private workoutTable db;
    private List<Workout> workoutList;

    @Mock
    private SharedPreferences sharedPreferences;

    @Before
    public void setUp() {
        fragment = new HomeFragment();
        db = mock(workoutTable.class);
        workoutList = new ArrayList<>();
        workoutList.add(new Workout(1, "Workout 1", 100, 200));
        when(db.getAllWorkouts()).thenReturn(workoutList);

        sharedPreferences = mock(SharedPreferences.class);
        when(sharedPreferences.getInt("userWeight", 0)).thenReturn(150);
        Context context = mock(Context.class);
        when(context.getSharedPreferences("sp_userData", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences);
        fragment.setContext(context);


    }


    //BMI Class
    //Base Case
    //TT characteristics
    @Test
    public void testBMIBaseCase() {
        BMI bmi = new BMI();
        float weight = 70;
        float height = 1.7f;
        float expected = 24.22f;
        float delta = 0.01f;
        assertEquals(expected, bmi.calculateBMI(weight, height), delta);
    }

    //BMI CLass
    //FT characteristics
    @Test
    public void testWeightLowerThanOne() {
        BMI bmi = new BMI();
        float weight = -1;
        float height = 1.7f;
        float expected = -0.34f;
        float delta = 0.01f;
        assertEquals(expected, bmi.calculateBMI(weight, height), delta);
    }


    //BMI Class
    //TF characteristics
    @Test
    public void testNonNumericInput() {
        BMI bmi = new BMI();
        float weight = 100;
        String height = "abc";
        assertThrows(NumberFormatException.class, () -> {
            bmi.calculateBMI(weight, Float.parseFloat(height));
        });

    }

    //HomeFragmentClass
    //TT characteristics
    //Base Case
    @Test
    public void testHomeFragmentBaseCase() {
        View view = fragment.getView();
        fragment.showAddWorkout(view);
        assertNotNull(view);
    }

    //HomeFragmentClass
    //TF characteristics
    @Test
    public void testIncorrectlyAddWorkout() {
        // Mock empty input fields
        EditText nameField = mock(EditText.class);
        when(nameField.getText()).thenReturn(mock(SpannableStringBuilder.class));
        EditText setsField = mock(EditText.class);
        when(setsField.getText()).thenReturn(mock(SpannableStringBuilder.class));
        EditText repsField = mock(EditText.class);
        when(repsField.getText()).thenReturn(mock(SpannableStringBuilder.class));

        // Add the empty workout
        fragment.addWorkout(nameField, setsField, repsField);

        // Verify that the database was not updated
        verify(db, never()).addWorkout(any(Workout.class));
    }

    //TrackActivity Class
    //Base Case
    //TT
    @Test
    public void testTrackActivityBaseCase() {
        TrackActivity activity = new TrackActivity();
        activity.setActivityType("running");
        activity.setActivityDuration(60); // 1 hour
        double caloriesBurned = activity.getCaloriesBurned();
        assertEquals(600, caloriesBurned, 0.0);
    }

    //TrackActivity Class
    //FT
    @Test
    public void testWorkoutTimeLessThanZero() {
        TrackActivity activity = new TrackActivity();
        activity.setActivityType("running");
        activity.setActivityDuration(-10); // negative duration
        caloriesBurned = activity.getCaloriesBurned();
        assertEquals(-100, caloriesBurned);
    }

    //TrackActivityClass
    //TF
    @Test
    public void testWorkoutNullActivity() {
        TrackActivity activity = new TrackActivity();
        activity.setActivityDuration(60);
        assertThrows(NullPointerException.class, () -> {
            activity.setActivityType(null);
            caloriesBurned = activity.getCaloriesBurned();
        });

    }

}