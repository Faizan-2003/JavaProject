package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryController {
  @FXML private TableView<Product> tableProductsInventory;

  @FXML private TableColumn stockColumn;
  @FXML private TableColumn nameColumn;
  @FXML private TableColumn categoryColumn;
  @FXML private TableColumn priceColumn;
  @FXML private TableColumn descriptionColumn;
  @FXML private Label labelProductNotice;
  private Database database;

  public void initialize() {
    database = Database.getInstance();
    database.loadDataFromFile();

    stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    Map<String, Product> productMap = Database.getInstance().getProducts();
    List<Product> products = new ArrayList<>(productMap.values());
    tableProductsInventory.getItems().setAll(products);
  }

  public void openAddProductWindow(ActionEvent actionEvent) {
    try {
      FXMLLoader loader =
          new FXMLLoader(
              getClass().getResource("/com/example/javaendassignment/AddProductWindow.fxml"));
      Parent root = loader.load();

      Stage addProductStage = new Stage();
      addProductStage.setTitle("Add Product");
      addProductStage.setScene(new Scene(root));

      AddProductWindowController addProductController = loader.getController();
      addProductController.setStage(addProductStage);
      addProductController.setInventoryController(this);

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
      labelProductNotice.setText("Product Deleted.");
    } else {
      labelProductNotice.setText("Please select a product to delete..");
    }
    database.saveDataToFile();
  }

  public void editProduct(ActionEvent actionEvent) {
    Product selectedProduct = tableProductsInventory.getSelectionModel().getSelectedItem();

    if (selectedProduct != null) {
      openEditProductWindow(selectedProduct);
    } else {
      labelProductNotice.setText("Please select a product to edit..");
    }
  }

  private void openEditProductWindow(Product selectedProduct) {
    try {
      FXMLLoader loader =
          new FXMLLoader(
              getClass().getResource("/com/example/javaendassignment/EditProduct-Window.fxml"));
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
  public void importCSVfile(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select CSV File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

    List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

    if (selectedFiles != null && !selectedFiles.isEmpty()) {
      for (File file : selectedFiles) {
        importProductsFromCSV(file);
      }
    } else {
      System.out.println("No file selected..");
    }

  }
  public void importProductsFromCSV(File csvFile) {
    try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
      String line;
      reader.readLine();

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(";");
        if (data.length == 5) {
          String name = data[0];
          String category = data[1];
          double price = Double.parseDouble(data[2]);
          String description = data[3];
          int stock = Integer.parseInt(data[4]);

          Product product = new Product(stock, name, category, price, description);
          database.addProduct(product);

          updateProductInTable(product);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void updateProductInTable(Product updatedProduct) {
    int index = tableProductsInventory.getItems().indexOf(updatedProduct);
    if (index >= 0) {
      tableProductsInventory.getItems().set(index, updatedProduct);
    } else {
      tableProductsInventory.getItems().add(updatedProduct);
    }
  }
}
