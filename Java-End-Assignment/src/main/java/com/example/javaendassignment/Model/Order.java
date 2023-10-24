package com.example.javaendassignment.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String customerEmail;
    private List<OrderedProduct> orderedProducts; // A list of products in the order

    public Order(String customerFirstName, String customerLastName, String customerPhone, String customerEmail) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.orderedProducts = new ArrayList<>();
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void addProductToOrder(Product product, int quantity) {
        OrderedProduct orderedProduct = new OrderedProduct(product, quantity);
        orderedProducts.add(orderedProduct);
    }

    // A class to represent products within the order
    public static class OrderedProduct {
        private Product product;
        private int quantity;

        public OrderedProduct(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
