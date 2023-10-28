package com.example.javaendassignment.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.javaendassignment.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Order implements Serializable {
    private static final long serialVersionUID = 357439316648153333L;

    private LocalDateTime orderDateTime;
    private double totalPrice;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String customerEmail;
    private transient ObservableList<Product> orderItems; // Mark as transient

    public Order(String customerFirstName, String customerLastName, String customerPhone, String customerEmail) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.orderDateTime = LocalDateTime.now();
        this.orderItems = FXCollections.observableArrayList(); // Initialize as an empty ObservableList
    }

    // Other constructors and methods...

    // Add these custom serialization methods
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(orderDateTime.toString()); // Serialize LocalDateTime as a String
        List<Product> productList = new ArrayList<>(orderItems);
        out.writeObject(productList); // Serialize the List of Products
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        orderDateTime = LocalDateTime.parse((String) in.readObject()); // Deserialize LocalDateTime from the String
        List<Product> productList = (List<Product>) in.readObject(); // Deserialize the List of Products
        orderItems = FXCollections.observableArrayList(productList); // Convert the List back to an ObservableList
    }

    public Order() {
        // Initialize any default values if needed
        this.orderItems = FXCollections.observableArrayList(); // Initialize as an empty ObservableList
    }

    public void setOrderItems(List<Product> orderItems) {
        this.orderItems = FXCollections.observableArrayList(orderItems);
    }

    public ObservableList<Product> getOrderItems() {
        return orderItems;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setFirstName(String firstName) {
        this.customerFirstName = firstName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.customerPhone = phoneNumber;
    }

    public void setEmail(String email) {
        this.customerEmail = email;
    }

    public void setLastName(String lastName) {
        this.customerLastName = lastName;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    // Assuming you want to set the total price
    public void setTotalPrice(double totalAmount) {
        this.totalPrice = totalAmount;
    }
}
