package com.example.cmsc355androidproject;

public class Workout {

    public int id, cal, time;
    public String name;
    public Workout(int i, String n, int c, int t){
        id = i;
        name = n;
        cal = c;
        time = t;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCal() {
        return cal;
    }

    public int getTime() {
        return time;
    }
}
