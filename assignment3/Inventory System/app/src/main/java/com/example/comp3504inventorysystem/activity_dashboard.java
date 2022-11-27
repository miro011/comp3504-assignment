package com.example.comp3504inventorysystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.comp3504inventorysystem.databinding.ActivityDashbardBinding;


public class activity_dashboard extends activity_drawer_base {

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
        finish();
        startActivity(new Intent(this, activity_add_item_view.class));
        overridePendingTransition(0,0);
    }
    public void openListItemActivity(){
        finish();
        startActivity(new Intent(this, activity_list_item_view.class));
        overridePendingTransition(0,0);
    }
    public void openSearchItemActivity(){
        finish();
        startActivity(new Intent(this, activity_search_item_view.class));
        overridePendingTransition(0,0);
    }
    public void openInfoActivity(){
        finish();
        startActivity(new Intent(this, activity_info_view.class));
        overridePendingTransition(0,0);
    }
}
