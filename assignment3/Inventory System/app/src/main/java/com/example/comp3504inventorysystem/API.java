package com.example.comp3504inventorysystem;

import java.util.ArrayList;

public class API {
    private String host;
    private int port;

    public API(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Add a new item to inventory (not just increase qty).
     * @param item
     */
    public void addItem(Item item) {
        System.out.format("Adding item %s\n.", item.toString());
    }

    /**
     * Remove an item from inventory entirely (not just zero qty).
     * @param id
     */
    public void removeItem(int id) {
        System.out.format("Removing item %d\n", id);
    }

    /**
     * Search for an item by the ID.
     * @param id
     * @return A list of matching results
     */
    public ArrayList<Item> searchItem(int id) {
        System.out.format("Searching items by id=%d", id);
        return new ArrayList<Item>();
    }

    /**
     * Search for an item by a regex matched against item names.
     * @param nameRegex
     * @return A list of matching results
     */
    public ArrayList<Item> searchItem(String nameRegex) {
        System.out.format("Searching items by nameRegex like %s", nameRegex);
        return new ArrayList<Item>();
    }

    /**
     * Get all items in inventory and their information.
     * @return A list of all items currently in inventory
     */
    public ArrayList<Item> getItems() {
        System.out.println("Getting all items");
        return new ArrayList<Item>();
    }

    /**
     * Modifies the contents of the item in inventory specified by newItem.id.
     * Overwrites the entire item, so if you don't want something to change, set it to the current
     * value.
     * @param newItem ID is the item to overwrite, all other fields are the new values.
     */
    public void modifyItem(Item newItem) {
        System.out.format("Modifying item %d\n", newItem);
    }
}
