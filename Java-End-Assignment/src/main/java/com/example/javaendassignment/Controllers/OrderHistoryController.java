package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.Order;
import com.example.javaendassignment.Model.OrderItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderHistoryController {
    @FXML
    private TableView<Order> tableOrderHistory;
    @FXML
    private TableColumn<Order, LocalDateTime> orderDateTimeColumn;
    @FXML
    private TableColumn<Order, String> customerFirstNameColumn;
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;
    @FXML
    private TableView<OrderItem> tableOrderProducts;
    @FXML
    private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML
    private TableColumn<OrderItem, String> nameColumn;
    @FXML
    private TableColumn<OrderItem, String> categoryColumn;
    @FXML
    private TableColumn<OrderItem, Double> priceColumn;

    private Database database; // Declare a member variable for the database
    private ObservableList<Order> orderHistory;
    private ObservableList<OrderItem> orderProducts;

    public void initialize() {
        database = Database.getInstance(); // Initialize the database here

        // Initialize your table columns for order history
        orderDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("orderDateTime"));
        customerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerFirstName"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Initialize your table columns for order products
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set the cell factory for formatting the date
        orderDateTimeColumn.setCellFactory(column -> {
            return new TableCell<Order, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        // Format the LocalDateTime as a string
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        setText(item.format(formatter));
                    }
                }
            };
        });
        loadOrderHistory();
    }
    private void loadOrderHistory() {
        List<Order> allOrders = database.getAllOrders();

        if (allOrders != null) {
            orderHistory = FXCollections.observableArrayList(allOrders);

            // Set the data in the order history table and calculate total price
            tableOrderHistory.setItems(orderHistory);

            for (Order order : orderHistory) {
                double totalAmount = calculateTotalAmount(order);
                order.setTotalPrice(totalAmount); // Set the total price for the order
            }
        }
    }
    private double calculateTotalAmount(Order order) {
        if (order == null || order.getOrderItems() == null) {
            return 0.0; // Handle the case where the order or order items are null
        }

        double totalAmount = 0.0;

        for (OrderItem item : order.getOrderItems()) {
            totalAmount += item.getPrice() * item.getQuantity();
        }

        return totalAmount;
    }

}
