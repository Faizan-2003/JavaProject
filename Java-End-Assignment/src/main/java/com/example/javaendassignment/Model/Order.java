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
    private transient ObservableList<Product> orderItems;

    public Order(String customerFirstName, String customerLastName, String customerPhone, String customerEmail) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.orderDateTime = LocalDateTime.now();
        this.orderItems = FXCollections.observableArrayList();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(orderDateTime.toString());
        List<Product> productList = new ArrayList<>(orderItems);
        out.writeObject(productList);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        orderDateTime = LocalDateTime.parse((String) in.readObject());
        List<Product> productList = (List<Product>) in.readObject();
        orderItems = FXCollections.observableArrayList(productList);
    }

    public Order() {
        this.orderItems = FXCollections.observableArrayList();
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
    public void setTotalPrice(double totalAmount) {

        this.totalPrice = totalAmount;
    }
    public void addOrderItem(Product product, int quantity) {
        double subtotal = product.getPrice() * quantity;
        orderItems.add(product);
        totalPrice += subtotal;
    }

}
