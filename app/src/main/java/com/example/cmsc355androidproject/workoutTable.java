package com.example.cmsc355androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public final class workoutTable extends SQLiteOpenHelper {
    //default variables for the columns of the database
    private static final String DATABASE_NAME = "Saved Workouts";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_WORKOUTS ="workouts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CAL = "cals";
    private static final String KEY_TIME = "minutes";

    public workoutTable(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //on creation it will make the sqlite database with the given format
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_workoutTable = "CREATE TABLE "+ TABLE_WORKOUTS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_CAL + " TEXT, " + KEY_TIME + " TEXT" + ")";
        db.execSQL(create_workoutTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        onCreate(db);
    }

    void addWorkout(Workout workout){
        SQLiteDatabase db = this.getWritableDatabase();
        //adds all the values from the workout to the given columns then inserts a new empty row
        ContentValues values = new ContentValues();
        values.put(KEY_ID, workout.getId());
        values.put(KEY_NAME, workout.getName());
        values.put(KEY_CAL, workout.getCal());
        values.put(KEY_TIME, workout.getTime());

        db.insert(TABLE_WORKOUTS, null, values);
        db.close();
    }

    //gets all the workouts from the database
    public List<Workout> getAllWorkouts(){
        List<Workout> workoutList = new ArrayList<Workout>();

        String selectQuery = "SELECT * FROM " + TABLE_WORKOUTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Workout workout = new Workout(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)));
                workoutList.add(workout);
            } while(cursor.moveToNext());
        }
        return workoutList;
    }
}
