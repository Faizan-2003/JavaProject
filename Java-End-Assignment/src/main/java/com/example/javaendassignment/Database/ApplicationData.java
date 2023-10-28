package com.example.javaendassignment.Database;

import com.example.javaendassignment.Model.Order;
import com.example.javaendassignment.Model.Product;
import com.example.javaendassignment.Model.User;


import java.util.Map;
import java.io.Serializable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ApplicationData implements Serializable {
    private Map<String, User> users;
    private Map<String, Product> products;
    private Map<String, Order> orders;

    public ApplicationData(Map<String, User> users, Map<String, Product> products, Map<String, Order> orders) {
        this.users = users;
        this.products = products;
        this.orders = orders;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public Map<String, Order> getOrders() {
        return orders;
    }
}
