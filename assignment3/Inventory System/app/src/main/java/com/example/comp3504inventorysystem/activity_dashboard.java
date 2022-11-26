package com.example.comp3504inventorysystem;

import android.os.Bundle;

import com.example.comp3504inventorysystem.databinding.ActivityDashbardBinding;


public class activity_dashboard extends activity_drawer_base {

    ActivityDashbardBinding activityDashbardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashbardBinding = ActivityDashbardBinding.inflate(getLayoutInflater());
        setContentView(activityDashbardBinding.getRoot());
        allocateActivityTitle("Dashboard");
    }
}