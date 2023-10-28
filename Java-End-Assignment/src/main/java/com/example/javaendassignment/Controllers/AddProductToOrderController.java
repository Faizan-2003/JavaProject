package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AddProductToOrderController {
    @FXML
    private TextField QuantityTextField;
    @FXML
    private TableColumn<Product, Integer> stockColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private Label labelErrorQuantity;

    private CreateOrderController createOrderController;
    private Database database;

    public void setCreateOrderController(CreateOrderController createOrderController) {
        this.createOrderController = createOrderController;
    }

    private Stage stage; // Reference to the stage of the Add Product window

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {

        database = Database.getInstance(); // Initialize the database here
        database.loadDataFromFile();

        // Initialize your controller, set up the table view
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Retrieve products from the database
        ObservableList<Product> products = FXCollections.observableArrayList(Database.getInstance().getProducts().values());

        // Set the products in the TableView
        productTableView.setItems(products);
    }

    public void addItemToOrder(ActionEvent actionEvent) {
        try {
            int quantity = Integer.parseInt(QuantityTextField.getText());
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

            if (selectedProduct != null) {
                if (quantity <= selectedProduct.getStock()) {
                    // Set the quantity and calculate the total price for the selected product
                    selectedProduct.setQuantity(quantity);
                    createOrderController.addOrderItem(selectedProduct); // Send the selected product to CreateOrderController

                    // Clear the QuantityTextField
                    QuantityTextField.clear();
                    labelErrorQuantity.setText(""); // Clear any previous error message
                } else {
                    labelErrorQuantity.setText("Not enough stock available.");
                }
            }
        } catch (NumberFormatException e) {
            labelErrorQuantity.setText("Please enter a valid quantity.");
        }
    }

    public void goBackToCreateOrder(ActionEvent actionEvent) {
        stage.close();
    }
}
