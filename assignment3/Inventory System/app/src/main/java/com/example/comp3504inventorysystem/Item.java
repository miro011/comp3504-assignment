package com.example.comp3504inventorysystem;

import java.util.Locale;

public class Item {
    private int id;
    private String name;
    private int quantity;
    private float price;
    private int supplierId;
    Locale locale = new Locale("en", "CA");

    public Item() {
        this.id = 0;
        this.name = "";
        this.quantity = 0;
        this.price = 0;
        this.supplierId = 0;
    }

    public Item(int id, String itemName, int quantity, float price, int supplierId) {
        this.id = id;
        this.name = itemName;
        this.quantity = quantity;
        this.price = price;
        this.supplierId = supplierId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdStr(String id) {
        int newId;

        newId = Integer.parseInt(id);
        if (newId < 0) {
            throw new IllegalArgumentException("ID must be an int >= 0");
        }

        this.id = newId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return String.format(locale,
                "Item {id: %d, name: %s, quantity: %d, price: %f, supplierId: %d",
                id,
                name,
                quantity,
                price,
                supplierId
        );
    }
}
