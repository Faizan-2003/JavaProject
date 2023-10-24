package com.example.javaendassignment.Model;

public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public String getName() {
        return product.getName();
    }

    public String getCategory() {
        return product.getCategory();
    }

    public double getPrice() {
        return product.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }
}
