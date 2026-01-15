package com.logistics.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class InventoryItem {
    @Id
    private String productId;
    private String name;
    private String description;
    private double price;
    private String category;
    private int quantity;

    public InventoryItem() {}

    public InventoryItem(String productId, String name, String description, double price, String category, int quantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
