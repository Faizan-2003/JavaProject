package com.example.javaendassignment.Controllers;
import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class InventoryController {
    @FXML
    private TableView<Product> tableProductsInventory;

    @FXML
    private Button buttonAddProduct;
    @FXML
    private Button buttonDeleteProduct;
    @FXML
    private Button buttonCreateOrder;
    @FXML
    private TableColumn stockColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn categoryColumn;
    @FXML
    private TableColumn priceCategory;
    @FXML
    private TableColumn priceColumn;
    @FXML
    private TableColumn descriptionColumn;

    public void initialize() {
        // Initialize the table columns
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Access the products from the Database class
        Map<String, Product> productMap = Database.getInstance().getProducts();

        // Convert the product map to a list of products
        List<Product> products = new ArrayList<>(productMap.values());

        // Set the data to the table
        tableProductsInventory.getItems().setAll(products);
    }



    public void openAddProductWindow(ActionEvent actionEvent) {
        try {
            // Load the AddProductWindow.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/AddProductWindow.fxml"));
            Parent root = loader.load();

            // Create a new stage for the "Add Product" window
            Stage addProductStage = new Stage();
            addProductStage.setTitle("Add Product");
            addProductStage.setScene(new Scene(root));

            // Set the controller for the "Add Product" window
            AddProductWindowController addProductController = loader.getController();
            addProductController.setStage(addProductStage);
            addProductController.setInventoryController(this); // Pass the InventoryController reference

            // Show the "Add Product" window
            addProductStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProductToTable(Product product) {
      tableProductsInventory.getItems().add(product);
    }

    public void createOrderWindow(ActionEvent actionEvent) {

    }

    public void deleteProduct(ActionEvent actionEvent) {

    }
}
