package com.example.cmsc355androidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsFragment extends Fragment {
    private Switch switch_btn;
    SaveState saveState;

    public SettingsFragment(){
    }

    //sets the view for the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_fragment, container, false);

        saveState = new SaveState(getContext());
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);

        if (saveState.getState()==true) {
            bottomNavigationView.setSelectedItemId(R.id.settings);
            ((AppCompatActivity) getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else {
            bottomNavigationView.setSelectedItemId(R.id.settings);
            ((AppCompatActivity) getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        switch_btn = v.findViewById(R.id.switch_btn);

        if (saveState.getState()==true)
            switch_btn.setChecked(true);

        switch_btn.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked) {
                saveState.setState(true);
                ((AppCompatActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else {
                saveState.setState(false);
                ((AppCompatActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            bottomNavigationView.setSelectedItemId(R.id.settings);
        });
        return v;
    }
}
