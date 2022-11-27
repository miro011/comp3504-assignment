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

    public void setIdStr(String idStr) {
        int newId = Integer.parseInt(idStr);

        if (newId < 0) {
            throw new IllegalArgumentException("ID must be an int >= 0");
        }

        this.id = newId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.equals("")) {
            throw new IllegalArgumentException("Name can't be empty");
        }
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setQuantityStr(String qtyStr) {
        int newQty = Integer.parseInt(qtyStr);
        this.quantity = newQty;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPriceStr(String priceStr) {
        float newPrice = Float.parseFloat(priceStr);

        if (newPrice < 0) {
            throw new IllegalArgumentException("Price must be >= 0.00");
        }

        this.price = newPrice;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setSupplierIdStr(String supplierIdStr) {
        int newSupplierId = Integer.parseInt(supplierIdStr);

        if (newSupplierId < 0) {
            throw new IllegalArgumentException("Supplier ID must be an int >= 0");
        }

        this.id = newSupplierId;
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
