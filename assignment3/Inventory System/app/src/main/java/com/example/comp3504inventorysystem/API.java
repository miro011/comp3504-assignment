package com.example.comp3504inventorysystem;

import android.app.Application;

public class API {
    public boolean addItem(Item item) {
        System.out.format("Adding item %s\n.", item.toString());
        return true;
    }
}
