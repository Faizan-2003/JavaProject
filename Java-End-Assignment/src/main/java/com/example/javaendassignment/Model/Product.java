package com.example.javaendassignment.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private int quantity;
    private String name;
    private String description;
    private double price;
    private String category;
    private int stock;
    private double totalPrice;

    public Product(int stock, String name, String category, double price, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.description = description;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
        }
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
