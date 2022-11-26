package com.example.comp3504inventorysystem;

import android.os.Bundle;

import com.example.comp3504inventorysystem.databinding.ActivityListItemViewBinding;

public class activity_list_item_view extends activity_drawer_base {

    ActivityListItemViewBinding activityListItemViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListItemViewBinding = ActivityListItemViewBinding.inflate(getLayoutInflater());
        setContentView(activityListItemViewBinding.getRoot());
        allocateActivityTitle("List Item");
    }
}