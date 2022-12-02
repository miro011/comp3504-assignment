package com.example.comp3504inventorysystem;

import java.util.Locale;

public class Item {
    private Integer id;
    private String name;
    private Integer quantity;
    private Float price;
    private Integer supplierId;
    Locale locale = new Locale("en", "CA");

    public Item() {
        this.id = null;
        this.name = null;
        this.quantity = null;
        this.price = null;
        this.supplierId = null;
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
        return this.supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setSupplierIdStr(String supplierIdStr) {
        int newSupplierId = Integer.parseInt(supplierIdStr);

        if (newSupplierId < 0) {
            throw new IllegalArgumentException("Supplier ID must be an int >= 0");
        }

        this.supplierId = newSupplierId;
    }

    public String toJsonString() {
        String output = "";
        if (this.id != null) output += "\"id\":" + "\"" + Integer.toString(this.id) + "\",";
        if (this.name != null) output += "\"name\":" + "\"" + this.name + "\",";
        if (this.quantity != null) output += "\"qty\":" + "\"" + Integer.toString(this.quantity) + "\",";
        if (this.price != null) output += "\"price\":" + "\"" + Float.toString(this.price) + "\",";
        if (this.supplierId != null) output += "\"sid\":" + "\"" + Integer.toString(this.supplierId) + "\",";

        // remove the last coma
        if (output.length() > 0 ) output = output.substring(0, output.length() - 1);
        output = "{" + output + "}";
        return output;
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
