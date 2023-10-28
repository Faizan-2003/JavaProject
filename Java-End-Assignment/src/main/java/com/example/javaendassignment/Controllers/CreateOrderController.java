package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Model.Order;
import com.example.javaendassignment.Model.OrderItem;
import com.example.javaendassignment.Model.Product;
import com.example.javaendassignment.Database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDateTime;

public class CreateOrderController {

    @FXML
    private TableColumn<OrderItem, String> nameColumn;
    @FXML
    private TableColumn<OrderItem, String> categoryColumn;
    @FXML
    private TableColumn<OrderItem, Double> priceColumn;
    @FXML
    private TableColumn<OrderItem, Integer> quantityColumn;

    @FXML
    private Button buttonDeleteProduct;
    @FXML
    private TextField textFirstName;
    @FXML
    private TextField textPhoneNumber;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textLastName;
    @FXML
    private Label labelErrorInformation;
    @FXML
    private Label labelOrderCreated;
    @FXML
    private TableView<Order> tableOrderHistory;
    @FXML
    private TableView<OrderItem> tableProductsCO;

    private ObservableList<OrderItem> orderItemsList = FXCollections.observableArrayList();
    private ObservableList<Order> orderHistory = FXCollections.observableArrayList();

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tableProductsCO.setItems(orderItemsList);

        tableProductsCO.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            buttonDeleteProduct.setDisable(newSelection == null);
        });
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItemsList.add(orderItem);
    }

    public void createOrder(ActionEvent actionEvent) {
        String firstName = textFirstName.getText();
        String lastName = textLastName.getText();
        String phoneNumber = textPhoneNumber.getText();
        String email = textEmail.getText();

        double totalAmount = calculateTotalAmount(new Order());

        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
            labelErrorInformation.setText("Please fill in all the fields.");
        } else {
            labelErrorInformation.setText("");
            Order order = new Order();
            order.setFirstName(firstName);
            order.setLastName(lastName);
            order.setPhoneNumber(phoneNumber);
            order.setEmail(email);
            order.setOrderItems(orderItemsList);
            order.setTotalPrice(totalAmount); // Set the total price

            order.setOrderDateTime(LocalDateTime.now());
            processOrderAndUpdateDatabase(order);
            clearForm();
        }
    }

    private void clearForm() {
        textFirstName.clear();
        textLastName.clear();
        textPhoneNumber.clear();
        textEmail.clear();
        orderItemsList.clear();
    }

    private double calculateTotalAmount(Order order) {
        double totalAmount = 0.0;

        for (OrderItem item : order.getOrderItems()) {
            totalAmount += item.getPrice() * item.getQuantity();
        }

        return totalAmount;
    }

    public void processOrderAndUpdateDatabase(Order order) {
        Database database = Database.getInstance();
        database.addOrder(order);

        for (OrderItem orderItem : orderItemsList) {
            Product product = orderItem.getProduct();
            if (product != null) {
                int currentStock = product.getStock();
                int quantitySold = orderItem.getQuantity();
                if (currentStock >= quantitySold) {
                    product.setStock(currentStock - quantitySold);
                    database.updateProduct(product);
                } else {
                    labelErrorInformation.setText("Insufficient stock for product: " + orderItem.getName());
                    return;
                }
            }
        }
    }

    public void deleteOrder(ActionEvent actionEvent) {
        OrderItem selectedItem = tableProductsCO.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            orderItemsList.remove(selectedItem);
        }
    }

    public void addProducts(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/Add-Product.fxml"));
            Parent root = loader.load();
            Stage addProductToOrderStage = new Stage();
            addProductToOrderStage.setTitle("Add Product to Order");
            addProductToOrderStage.setScene(new Scene(root));
            AddProductToOrderController addProductToOrderController = loader.getController();
            addProductToOrderController.setStage(addProductToOrderStage);
            addProductToOrderController.setCreateOrderController(this);
            addProductToOrderStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
