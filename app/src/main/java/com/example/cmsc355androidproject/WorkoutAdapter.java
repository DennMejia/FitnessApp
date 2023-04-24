package com.example.cmsc355androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {
    List<Workout> workoutList;
    Context context;

    public WorkoutAdapter(List<Workout> list){
        workoutList = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_cards, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.name.setText(String.valueOf(workout.getName()));
        holder.cals.setText(String.valueOf(workout.getCal()));
        holder.time.setText(String.valueOf(workout.getTime()));
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView name, cals, time;

        public ViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            cals = itemView.findViewById(R.id.textView3);
            time = itemView.findViewById(R.id.textView4);
        }

    }
}
