package com.example.javaendassignment.Controllers;
import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class InventoryController {
    @FXML
    private TableView<Product> tableProductsInventory;

    @FXML
    private TableColumn stockColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn categoryColumn;
    @FXML
    private TableColumn priceColumn;
    @FXML
    private TableColumn descriptionColumn;
    private Database database;

    public void initialize() {
        database = Database.getInstance();
        database.loadDataFromFile();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/AddProductWindow.fxml"));
            Parent root = loader.load();

            Stage addProductStage = new Stage();
            addProductStage.setTitle("Add Product");
            addProductStage.setScene(new Scene(root));

            AddProductWindowController addProductController = loader.getController();
            addProductController.setStage(addProductStage);
            addProductController.setInventoryController(this); // Pass the InventoryController reference

            addProductStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProductToTable(Product product) {

        tableProductsInventory.getItems().add(product);
        database.saveDataToFile();

    }

    public void deleteProduct(ActionEvent actionEvent) {

        Product selectedProduct = tableProductsInventory.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            tableProductsInventory.getItems().remove(selectedProduct);

            Database.getInstance().deleteProduct(selectedProduct);
        }
        database.saveDataToFile();
    }

    public void editProduct(ActionEvent actionEvent) {

        Product selectedProduct = tableProductsInventory.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            openEditProductWindow(selectedProduct);
        }
    }
    private void openEditProductWindow(Product selectedProduct) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/EditProduct-Window.fxml"));
            Parent root = loader.load();

            Stage editProductStage = new Stage();
            editProductStage.setTitle("Edit Product");
            editProductStage.setScene(new Scene(root));

            EditProductWindowController editProductController = loader.getController();
            editProductController.setStage(editProductStage);
            editProductController.setInventoryController(this);
            editProductController.setProductToEdit(selectedProduct);

            editProductStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateProductInTable(Product updatedProduct) {

        int index = tableProductsInventory.getItems().indexOf(updatedProduct);
        if (index >= 0) {
            tableProductsInventory.getItems().set(index, updatedProduct);
        }
    }
}
