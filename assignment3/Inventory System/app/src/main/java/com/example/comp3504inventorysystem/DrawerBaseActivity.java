package com.example.comp3504inventorysystem;

import static com.example.comp3504inventorysystem.R.id.nav_addItem;
import static com.example.comp3504inventorysystem.R.id.nav_exit;
import static com.example.comp3504inventorysystem.R.id.nav_info;
import static com.example.comp3504inventorysystem.R.id.nav_listItem;
import static com.example.comp3504inventorysystem.R.id.nav_searchItem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    API api;
    Resources res;

    @SuppressLint("InflateParams")
    @Override
    public void setContentView(View view) {
        res = getResources();

        drawerLayout =(DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activity_container);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navView = drawerLayout.findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        navView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        this.api = new API(getString(R.string.host), res.getInteger(R.integer.port));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer((GravityCompat.START));

        switch (item.getItemId()){
            case R.id.nav_home:
                startActivity(new Intent(this, DashboardActivity.class));
                overridePendingTransition(0,0);
                break;
            case nav_addItem:
                startActivity(new Intent(this, AddItemActivity.class));
                overridePendingTransition(0,0);
                break;
            case nav_listItem:
                startActivity(new Intent(this, ListItemActivity.class));
                overridePendingTransition(0,0);
                break;
            case nav_searchItem:
                startActivity(new Intent(this, SearchItemActivity.class));
                overridePendingTransition(0,0);
                break;
            case nav_info:
                startActivity(new Intent(this, InfoViewActivity.class));
                overridePendingTransition(0,0);
                break;
            case nav_exit:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                break;
        }
    return false;
    }

    protected void allocateActivityTitle (String titleString){
        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle(titleString);
        }
    }

    // https://stackoverflow.com/a/50188704
    public void showPopup(View view, String textToShow, String popupType) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView;
        if (popupType.equals("success")) popupView = inflater.inflate(R.layout.popup_success, null);
        else if (popupType.equals("error")) popupView = inflater.inflate(R.layout.popup_error, null);
        else popupView = inflater.inflate(R.layout.popup_generic, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // change the text of the popup - https://stackoverflow.com/a/59544347
        TextView popupTextview = popupView.findViewById(R.id.popup_text);
        popupTextview.setText(textToShow);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}

