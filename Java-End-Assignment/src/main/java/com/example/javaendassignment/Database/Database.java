package com.example.javaendassignment.Database;

import com.example.javaendassignment.Model.Product;
import com.example.javaendassignment.Model.Order;
import com.example.javaendassignment.Model.User;
import com.example.javaendassignment.Model.UserRole;

import java.io.*;
import java.time.LocalDateTime;
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

        users.put("faizan", new User("Muhammad Faizan", UserRole.manager, "Faizan@321"));
        users.put("tommy", new User("Tommy Shelby", UserRole.salesperson, "Tommy@123"));
    }

    public UserRole getUserRole(String username) {
        User user = users.get(username);
        if (user != null) {
            return user.getRole();
        } else {
            return null;
        }
    }

    public String getUserFullName(String username) {
        User user = users.get(username);
        if (user != null) {
            return user.getFullName();
        }
        return null;
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
        if(!file.exists()) {
            useInstanceData();
            saveDataToFile();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ApplicationData applicationData = (ApplicationData) ois.readObject();
            users.clear();
            products.clear();
            orders.clear();
            users.putAll(applicationData.getUsers());
            products.putAll(applicationData.getProducts());
            orders.putAll(applicationData.getOrders());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void useInstanceData() {
        Map<String, Product> products = new HashMap<>();
        products.put("g40Guitar", new Product(4, "G40 Guitar", "Guitar", 290.99, "The rarest guitar in the world"));
        products.put("v10Violin", new Product(7, "V10 Violin", "Violin", 109.99, "Everyone wants this violin"));
        products.put("p00Piano", new Product(6, "P009 Piano", "Piano", 1099.99, "The piano everyone should have"));

        List<Order> orders = new ArrayList<>();

        LocalDateTime dateTime = LocalDateTime.now();

        Order firstOrder = new Order("John", "Doe", "1234567890", "john.doe@example.com");
        firstOrder.setOrderDateTime(dateTime);
        firstOrder.addOrderItem(products.get("g40Guitar"), 2);
        firstOrder.addOrderItem(products.get("v10Violin"), 1);
        orders.add(firstOrder);

        Order secondOrder = new Order("Jane", "Smith", "9876543210", "jane.smith@example.com");
        secondOrder.setOrderDateTime(dateTime);
        secondOrder.addOrderItem(products.get("p00Piano"), 1);
        orders.add(secondOrder);
    }

    public void addProduct(Product product) {
        products.put(product.getName(), product);
        saveDataToFile();
    }

    public void updateProduct(Product updatedProduct) {
        if (products.containsKey(updatedProduct.getName())) {
            products.put(updatedProduct.getName(), updatedProduct);
            saveDataToFile();
        }
    }

    public void deleteProduct(Product product) {
        products.remove(product.getName());
        saveDataToFile();
    }

    public void addOrder(Order order) {
        orders.put(order.getCustomerEmail(), order);
        saveDataToFile();
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public Order getOrder(String customerEmail) {
        return orders.get(customerEmail);
    }

}
