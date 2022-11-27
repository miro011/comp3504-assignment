package com.example.comp3504inventorysystem;

import android.os.Bundle;
import com.example.comp3504inventorysystem.databinding.ActivitySearchItemViewBinding;

public class SearchItemActivity extends DrawerBaseActivity {

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