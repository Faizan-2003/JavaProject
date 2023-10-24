package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductWindowController {
    @FXML
    private TextField textDescriptionAP;
    @FXML
    private TextField textPriceAP;
    @FXML
    private TextField textCategoryAP;
    @FXML
    private TextField textNameAP;
    @FXML
    private TextField textStockAP;

    private Stage stage; // Reference to the stage of the Add Product window
    private InventoryController inventoryController;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setInventoryController(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
    }

    public void goBackToInventory(ActionEvent actionEvent) {
        // Close the Add Product window without saving
        stage.close();
    }

  public void saveTheProduct(ActionEvent actionEvent) {
    // Get data from text fields
    int stock = Integer.parseInt(textStockAP.getText());
    String name = textNameAP.getText();
    String category = textCategoryAP.getText();
    double price = Double.parseDouble(textPriceAP.getText());
    String description = textDescriptionAP.getText();

    // Create a new Product instance with the entered data
    Product newProduct = new Product(stock, name, category, price, description);

    // Add the product to the table in the InventoryController
    inventoryController.addProductToTable(newProduct);

    // Add the product to the database
    Database.getInstance().addProduct(newProduct);

    // Save the data to a file (assuming you have a method for this in your Database class)
    Database.getInstance().saveDataToFile();

    // Close the Add Product window
    stage.close();
        }
}
