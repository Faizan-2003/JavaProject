package com.example.javaendassignment.Database;

import com.example.javaendassignment.Model.Product;
import com.example.javaendassignment.Model.Order;
import com.example.javaendassignment.Model.User;
import com.example.javaendassignment.Model.UserRole;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class Database {
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Product> products = new HashMap<>();
    private static Map<String, Order> orders = new HashMap<>();
    private final String dataFileName = "applicationData.dat";
    private static Database instance;

    public Map<String, Product> getProducts() {
        return products;
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Database() {

        // Initialize the database with sample data
        users.put("faizan", new User("Muhammad Faizan", UserRole.manager, "Faizan@321"));
        users.put("tommy", new User("Tommy Shelby", UserRole.salesperson, "Tommy@123"));
    }

    public UserRole getUserRole(String username) {
        User user = users.get(username);
        if (user != null) {
            return user.getRole();
        } else {
            return null; // Return null or a default role when the user is not found
        }
    }

    public String getUserFullName(String username) {
        User user = users.get(username);
        if (user != null) {
            return user.getFullName();
        }
        return null; // Return null
    }

    public boolean isValidUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public void saveDataToFile() {
        ApplicationData applicationData = new ApplicationData(users, products, orders);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFileName))) {
            oos.writeObject(applicationData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadDataFromFile() {
        File file = new File(dataFileName);

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ApplicationData applicationData = (ApplicationData) ois.readObject();
            users.clear();
            products.clear();
            orders.clear();
            users.putAll(applicationData.getUsers());
            products.putAll(applicationData.getProducts());
            orders.putAll(applicationData.getOrders());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Print the exception for debugging
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }
    public void addProduct(Product product) {
        // Add the product to your products map
        products.put(product.getName(), product);

        // Optionally, you can call your saveDataToFile method to save the updated data to the .dat file
        saveDataToFile();
    }

    public void updateProduct(Product updatedProduct) {
        // Check if the product exists in the database
        if (products.containsKey(updatedProduct.getName())) {
            // Update the product in the database
            products.put(updatedProduct.getName(), updatedProduct);

            // Optionally, you can call your saveDataToFile method to save the updated data to the .dat file
            saveDataToFile();
        }
    }

    public void deleteProduct(Product product) {
        // Remove the product from the database
        products.remove(product.getName());

        // Optionally, you can call your saveDataToFile method to save the updated data to the .dat file
        saveDataToFile();
    }

    public void addOrder(Order order) {
        // Add the order to your orders map
        orders.put(order.getCustomerEmail(), order);

        // Optionally, you can call your saveDataToFile method to save the updated data to the.dat file
        saveDataToFile();
    }

    public Map<String, Order> getOrders() {
        return orders;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public Order getOrder(String customerEmail) {
        return orders.get(customerEmail);
    }

    public List<Product> getOrderProducts(Order order) {
        if (order != null) {
            List<Product> orderProducts = new ArrayList<>();
            for (Product product : products.values()) {
                if (order.getOrderItems().contains(product.getName())) {
                    orderProducts.add(product);
                }
            }
            return orderProducts;
        }
        return Collections.emptyList(); // Return an empty list if the order is null
    }
}
