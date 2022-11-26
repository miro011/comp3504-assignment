package com.example.comp3504inventorysystem;

import android.os.Bundle;

import com.example.comp3504inventorysystem.databinding.ActivityAddItemViewBinding;

public class activity_add_item_view extends activity_drawer_base {

    ActivityAddItemViewBinding activityAddItemViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddItemViewBinding = ActivityAddItemViewBinding.inflate(getLayoutInflater());
        setContentView(activityAddItemViewBinding.getRoot());
        allocateActivityTitle("Add Item");
    }
}