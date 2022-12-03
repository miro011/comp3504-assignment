package com.example.comp3504inventorysystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import com.example.comp3504inventorysystem.databinding.ActivityDashbardBinding;

public class DashboardActivity extends DrawerBaseActivity {
    ActivityDashbardBinding activityDashbardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashbardBinding = ActivityDashbardBinding.inflate(getLayoutInflater());
        setContentView(activityDashbardBinding.getRoot());
        allocateActivityTitle("Dashboard");

        ImageButton addItem_button = (ImageButton) findViewById(R.id.main_add_item);
        ImageButton listItem_button = (ImageButton) findViewById(R.id.main_list_item);
        ImageButton SearchItem_button = (ImageButton) findViewById(R.id.main_search_item);
        ImageButton info_button = (ImageButton) findViewById(R.id.main_Info);

        addItem_button.setOnClickListener(view -> openAddItemActivity());
        listItem_button.setOnClickListener(view -> openListItemActivity());
        SearchItem_button.setOnClickListener(view -> openSearchItemActivity());
        info_button.setOnClickListener(view -> openInfoActivity());
    }

    public void openAddItemActivity(){
        startActivity(new Intent(this, AddItemActivity.class));
    }
    public void openListItemActivity(){
        startActivity(new Intent(this, ListItemActivity.class));
    }
    public void openSearchItemActivity(){
        startActivity(new Intent(this, SearchItemActivity.class));
    }
    public void openInfoActivity(){
        startActivity(new Intent(this, InfoViewActivity.class));
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
            System.exit(0);
    }
}
