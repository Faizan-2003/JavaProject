package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Model.OrderItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderController {
    @FXML
    private TableColumn quantityColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn categoryColumn;
    @FXML
    private TableColumn priceColumn;

    @FXML
    private Button buttonDeleteProduct;
    @FXML
    private TextField textFirstName;
    @FXML
    private TextField textPhoneNumber;
    @FXML
    private TextField textEmailAddress;

    @FXML
    private TextField textLastName;

    @FXML
    private TableView<OrderItem> tableProductsCO;
    private List<OrderItem> orderItems = new ArrayList<>();
    private ObservableList<OrderItem> orderItemsList = FXCollections.observableArrayList();

    public void setOrderTable(TableView<OrderItem> tableProductsCO) {
        this.tableProductsCO = tableProductsCO;
    }

    public void initialize() {
        tableProductsCO.setItems(orderItemsList);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        tableProductsCO.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                buttonDeleteProduct.setDisable(false);
            } else {
                buttonDeleteProduct.setDisable(true);
            }
        });
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItemsList.add(orderItem);
    }

    public void createOrder(ActionEvent actionEvent) {
        // Here, you can access the orderItems list to create the order.
        // You can also access other text fields like textFirstName, textLastName, etc.
    }

    public void deleteOrder(ActionEvent actionEvent) {
        // Get the selected item from the TableView
        OrderItem selectedItem = tableProductsCO.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Remove the selected item from the orderItemsList
            orderItemsList.remove(selectedItem);
        }
    }

    public void addProducts(ActionEvent actionEvent) {
        try {
            // Load the AddProductToOrderWindow.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/Add-Product.fxml"));
            Parent root = loader.load();

            // Create a new stage for the "Add Product to Order" window
            Stage addProductToOrderStage = new Stage();
            addProductToOrderStage.setTitle("Add Product to Order");
            addProductToOrderStage.setScene(new Scene(root));

            // Set the controller for the "Add Product to Order" window
            AddProductToOrderController addProductToOrderController = loader.getController();
            addProductToOrderController.setStage(addProductToOrderStage);
            addProductToOrderController.setCreateOrderController(this); // Pass a reference to this controller

            // Show the "Add Product to Order" window
            addProductToOrderStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
