package com.example.comp3504inventorysystem;

import android.os.Bundle;
import com.example.comp3504inventorysystem.databinding.ActivityListItemViewBinding;

public class ListItemActivity extends DrawerBaseActivity {

    private long backClickTime;
    ActivityListItemViewBinding activityListItemViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListItemViewBinding = ActivityListItemViewBinding.inflate(getLayoutInflater());
        setContentView(activityListItemViewBinding.getRoot());
        allocateActivityTitle("List Item");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}