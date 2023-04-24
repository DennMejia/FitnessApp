package com.example.cmsc355androidproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    public ProfileFragment(){

    }


    @Override                                                                                       //sets the view for the fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView name = rootView.findViewById(R.id.profileNameText);                                //gets all the textviews in the xml
        TextView age = rootView.findViewById(R.id.profileAgeText);
        TextView weight = rootView.findViewById(R.id.profileWeightText);
        TextView height = rootView.findViewById(R.id.profileHeightText);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("sp_userData", Context.MODE_PRIVATE);
        name.setText("Name: " + sharedPref.getString("userName", ""));                  //sets all the textviews to show the profile values
        age.setText("Age: " + sharedPref.getInt("userAge", 0));
        weight.setText("Weight: " + sharedPref.getInt("userWeight", 0));
        float h = sharedPref.getFloat("userHeight", 0);
        int ft =  (int)h;
        int in = (int)(12*(h-ft));                                                                  //the height is in decimal so we need to do this to get accurate height
        height.setText("Height: " + ft + "' " + in + "'' ");
        return rootView;
    }


    
    public View inCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { // sets the view for fragment extension
        View bmiview = inflater.inflate(R.layout.profile_fragment, container, false);
        // creates button 
        Button button = bmiview.findViewById(R.id.bmi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BMI.class);
                startActivity(intent); 
            }
        });
        
        return bmiview;
    }

}
