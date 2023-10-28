package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.Order;
import com.example.javaendassignment.Model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableView<Product> tableOrderProducts;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;

    private Database database; // Declare a member variable for the database
    private ObservableList<Order> orderHistory;
    private ObservableList<Product> orderProducts;

    public void initialize() {
        database = Database.getInstance(); // Initialize the database here
        database.loadDataFromFile(); // Load the data from the.dat file

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
                List<Product> orderProductsList = order.getOrderItems();

                if (orderProductsList != null) {
                    orderProducts = FXCollections.observableArrayList(orderProductsList);

                    for (Product product : orderProducts) {
                        double totalAmount = calculateTotalAmount(product);
                        product.setTotalPrice(totalAmount); // Set the total price for the product
                    }

                    // Set the order products data
                    tableOrderProducts.setItems(orderProducts);
                }
            }
        }
        //database.saveDataToFile();
    }

    private double calculateTotalAmount(Product product) {
        if (product == null) {
            return 0.0; // Handle the case where the product is null
        }

        return product.getTotalPrice();
    }
}
