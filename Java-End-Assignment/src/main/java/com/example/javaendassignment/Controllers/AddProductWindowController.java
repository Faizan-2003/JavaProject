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

    private Stage stage;
    private InventoryController inventoryController;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setInventoryController(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
    }

    public void goBackToInventory(ActionEvent actionEvent) {
        stage.close();
    }

  public void saveTheProduct(ActionEvent actionEvent) {

    int stock = Integer.parseInt(textStockAP.getText());
    String name = textNameAP.getText();
    String category = textCategoryAP.getText();
    double price = Double.parseDouble(textPriceAP.getText());
    String description = textDescriptionAP.getText();

    Product newProduct = new Product(stock, name, category, price, description);

    inventoryController.addProductToTable(newProduct);

    Database.getInstance().addProduct(newProduct);
    Database.getInstance().saveDataToFile();
    stage.close();
    }
}
