package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
    private Label labelError;
    @FXML
    private TextField textSearchProduct;

    private CreateOrderController createOrderController;
    private Database database;

    public void setCreateOrderController(CreateOrderController createOrderController) {
        this.createOrderController = createOrderController;
    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        database = Database.getInstance();
        database.loadDataFromFile();

        setupTableColumns();
        setupSearchFilter();
    }

    private void setupTableColumns() {
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        ObservableList<Product> products = FXCollections.observableArrayList(Database.getInstance().getProducts().values());
        FilteredList<Product> filteredProducts = new FilteredList<>(products);

        productTableView.setItems(filteredProducts);
    }

    private void setupSearchFilter() {
        textSearchProduct.textProperty().addListener((observable, oldValue, newValue) -> {
            FilteredList<Product> filteredProducts = (FilteredList<Product>) productTableView.getItems();
            filteredProducts.setPredicate(product -> {
                String searchProduct = newValue.toLowerCase();
                if (searchProduct.length() < 3) {
                    return true;
                } else {
                    return product.getName().toLowerCase().contains(searchProduct);
                }
            });
        });
    }

    public void addItemToOrder(ActionEvent actionEvent) {
        try {
            int quantity = Integer.parseInt(QuantityTextField.getText());
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

            if (selectedProduct != null) {
                if (quantity <= selectedProduct.getStock()) {

                    selectedProduct.setQuantity(quantity);
                    createOrderController.addOrderItem(selectedProduct);
                    QuantityTextField.clear();
                    labelError.setText("");
                } else {
                    labelError.setText("Not enough stock available!");
                }
            }else {
                labelError.setText("Please select a product..");
            }
        } catch (NumberFormatException e) {
            labelError.setText("Please enter a valid quantity..");
        }
    }

    public void goBackToCreateOrder(ActionEvent actionEvent) {
        stage.close();
    }
}
