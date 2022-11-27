package com.example.comp3504inventorysystem;

import android.os.Bundle;
import com.example.comp3504inventorysystem.databinding.ActivityAddItemViewBinding;

public class AddItemActivity extends DrawerBaseActivity {
    private long backClickTime;
    ActivityAddItemViewBinding activityAddItemViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddItemViewBinding = ActivityAddItemViewBinding.inflate(getLayoutInflater());
        setContentView(activityAddItemViewBinding.getRoot());
        allocateActivityTitle("Add Item");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}