package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Model.Order;
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

  @FXML private TableColumn<Product, String> nameColumn;
  @FXML private TableColumn<Product, String> categoryColumn;
  @FXML private TableColumn<Product, Double> priceColumn;
  @FXML private TableColumn<Product, Integer> quantityColumn;

  @FXML private Button buttonDeleteProduct;
  @FXML private TextField textFirstName;
  @FXML private TextField textPhoneNumber;
  @FXML private TextField textEmail;
  @FXML private TextField textLastName;
  @FXML private Label labelErrorInformation;
  @FXML private Label LabelOrderNotice;
  @FXML private TableView<Product> tableProductsCO;

  private ObservableList<Product> orderItemsList = FXCollections.observableArrayList();
  private Database database;

  public void initialize() {

    database = Database.getInstance();
    database.loadDataFromFile();

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    tableProductsCO.setItems(orderItemsList);
    tableProductsCO
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              buttonDeleteProduct.setDisable(newSelection == null);
            });
  }

  public void addOrderItem(Product orderItem) {
    orderItemsList.add(orderItem);
  }

  public void createOrder(ActionEvent actionEvent) {
    String firstName = textFirstName.getText();
    String lastName = textLastName.getText();
    String phoneNumber = (textPhoneNumber.getText());
    String email = textEmail.getText();
    double totalAmount = calculateTotalAmount();

    if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
      labelErrorInformation.setText("Please fill in all the fields!");
    }
    if (tableProductsCO.getItems().isEmpty()) {
      labelErrorInformation.setText("Please add a product!.");
    } else {
      labelErrorInformation.setText("");
      Order order = new Order();
      order.setFirstName(firstName);
      order.setLastName(lastName);
      order.setPhoneNumber(phoneNumber);
      order.setEmail(email);
      order.setOrderItems(orderItemsList);
      order.setTotalPrice(totalAmount);
      order.setOrderDateTime(LocalDateTime.now());
      processOrderAndUpdateDatabase(order);
      LabelOrderNotice.setText("Order created successfully!");
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

  private double calculateTotalAmount() {
    double totalAmount = 0.0;

    for (Product item : orderItemsList) {
      totalAmount += item.getPrice() * item.getQuantity();
    }
    return totalAmount;
  }

  public void processOrderAndUpdateDatabase(Order order) {
    database = Database.getInstance();
    database.addOrder(order);

    for (Product orderItem : orderItemsList) {
      if (orderItem != null) {
        int currentStock = orderItem.getStock();
        int quantitySold = orderItem.getQuantity();
        if (currentStock >= quantitySold) {
          orderItem.setStock(currentStock - quantitySold);
          database.updateProduct(orderItem);
        } else {
          labelErrorInformation.setText("Insufficient stock for product: " + orderItem.getName());
          return;
        }
      }
    }
  }

  public void deleteOrder(ActionEvent actionEvent) {
    Product selectedItem = tableProductsCO.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
      orderItemsList.remove(selectedItem);
    } else {

      LabelOrderNotice.setStyle("-fx-text-fill: red;");
      LabelOrderNotice.setText("Please select an order item to delete!");
    }
  }

  public void addProducts(ActionEvent actionEvent) {
    try {
      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/com/example/javaendassignment/Add-Product.fxml"));
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
