package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditProductWindowController {
    @FXML
    private TextField textStockAP;
    @FXML
    private TextField textNameAP;
    @FXML
    private TextField textCategoryAP;
    @FXML
    private TextField textPriceAP;
    @FXML
    private TextField textDescriptionAP;

    private Stage stage; // Reference to the stage of the Edit Product window
    private InventoryController inventoryController;
    private Product productToEdit; // The product to be edited

    public void setProductToEdit(Product product) {
        this.productToEdit = product;
        populateFields();
    }

    private void populateFields() {
        if (productToEdit != null) {
            textStockAP.setText(String.valueOf(productToEdit.getStock()));
            textNameAP.setText(productToEdit.getName());
            textCategoryAP.setText(productToEdit.getCategory());
            textPriceAP.setText(String.valueOf(productToEdit.getPrice()));
            textDescriptionAP.setText(productToEdit.getDescription());
        }
    }

    public void editTheProduct(ActionEvent actionEvent) {
        if (productToEdit != null) {
            // Get data from text fields
            int stock = Integer.parseInt(textStockAP.getText());
            String name = textNameAP.getText();
            String category = textCategoryAP.getText();
            double price = Double.parseDouble(textPriceAP.getText());
            String description = textDescriptionAP.getText();

            // Update the selected product with the edited data
            productToEdit.setStock(stock);
            productToEdit.setName(name);
            productToEdit.setCategory(category);
            productToEdit.setPrice(price);
            productToEdit.setDescription(description);

            // Update the table view in InventoryController
            inventoryController.updateProductInTable(productToEdit);

            // Update the product in the database
            Database.getInstance().updateProduct(productToEdit);

            // Save the data to a file
            Database.getInstance().saveDataToFile();

            // Close the Edit Product window
            stage.close();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setInventoryController(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
    }

    public void goBackToInventory(ActionEvent actionEvent) {
        stage.close();
    }
}
