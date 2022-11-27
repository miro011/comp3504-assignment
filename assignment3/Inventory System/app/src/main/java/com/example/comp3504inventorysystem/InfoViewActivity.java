package com.example.comp3504inventorysystem;

import android.os.Bundle;
import com.example.comp3504inventorysystem.databinding.ActivityInfoViewBinding;

public class InfoViewActivity extends DrawerBaseActivity {

    ActivityInfoViewBinding activityInfoViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInfoViewBinding = ActivityInfoViewBinding.inflate(getLayoutInflater());
        setContentView(activityInfoViewBinding.getRoot());
        allocateActivityTitle("App Info");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}