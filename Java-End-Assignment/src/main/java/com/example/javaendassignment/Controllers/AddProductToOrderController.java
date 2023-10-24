package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.OrderItem;
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

import java.util.ArrayList;
import java.util.List;

public class AddProductToOrderController {
    @FXML
    private TextField QuantityTextField;
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
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private Label labelErrorQuantity;
    @FXML
    private TableView<OrderItem> orderTable;
    private List<OrderItem> orderItems = new ArrayList<>();
    private CreateOrderController createOrderController;

    public void setCreateOrderController(CreateOrderController createOrderController) {
        this.createOrderController = createOrderController;
    }
    private Stage stage; // Reference to the stage of the Add Product window
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void initialize() {
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
                    // Create an OrderItem and add it to the CreateOrderController's list
                    OrderItem orderItem = new OrderItem(selectedProduct, quantity);
                    createOrderController.addOrderItem(orderItem);

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
