package com.example.javaendassignment.Model;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
   private static final long serialVersionUID = 357439316648153333L;

    private LocalDateTime orderDateTime;
    private double totalPrice;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String customerEmail;
    private List<OrderItem> orderItems;
    private transient ObservableList<OrderItem> orderItemsList;

    public Order(String customerFirstName, String customerLastName, String customerPhone, String customerEmail) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.orderDateTime = LocalDateTime.now();
        this.orderItems = new ArrayList<>();
    }
    public Order() {
        // Initialize any default values if needed
        this.orderItems = new ArrayList<>();
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setOrderItems(ObservableList<OrderItem> orderItemsList) {
        this.orderItemsList = orderItemsList;
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

    public ObservableList<OrderItem> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(ObservableList<OrderItem> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }
    public void setTotalPrice(double totalAmount) {
        this.totalPrice = totalAmount;
    }
}
