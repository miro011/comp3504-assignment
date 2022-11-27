package com.example.comp3504inventorysystem;

import android.os.Bundle;

import com.example.comp3504inventorysystem.databinding.ActivitySearchItemViewBinding;

public class activity_search_item_view extends activity_drawer_base {

    private long backClickTime;
    ActivitySearchItemViewBinding activitySearchItemViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchItemViewBinding = ActivitySearchItemViewBinding.inflate(getLayoutInflater());
        setContentView(activitySearchItemViewBinding.getRoot());
        allocateActivityTitle("Search Item");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}