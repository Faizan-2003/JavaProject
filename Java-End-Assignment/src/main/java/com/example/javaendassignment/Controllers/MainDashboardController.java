package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.UserRole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainDashboardController {
    @FXML
    private VBox emptyPanel;

    @FXML
    private Label labelWelcome;

    @FXML
    private Label labelRole;

    @FXML
    private Label labelDateTime;

    @FXML
    private Button buttonDashboard;

    private String userName;
    private UserRole userRole;
    private String dateTime;
    private Database database;

    public void initialize(Database database) {
        this.database = database;
    }

    public void setUserData(String userName, UserRole userRole, String dateTime) {
        labelWelcome.setText("Welcome " + userName + "!");
        labelRole.setText("Your role is: " + userRole);
        labelDateTime.setText("It is now: " + dateTime);

        this.userName = userName;
        this.userRole = userRole;
        this.dateTime = dateTime;

        configureAccessControl();
    }

    private void configureAccessControl() {
        if (userRole == UserRole.manager || userRole == UserRole.salesperson) {
            buttonDashboard.setDisable(false);
        } else {
            buttonDashboard.setDisable(true);
        }
    }

    public void goToDashboard(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonDashboard.getScene().getWindow();
        openMainWindow(userName, stage);
    }

    private void openMainWindow(String username, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/MainDashboard.fxml"));
            Parent root = loader.load();
            MainDashboardController mainWindowController = loader.getController();
            mainWindowController.setUserData(userName, userRole, dateTime);
            Scene scene = new Scene(root);
            stage.setTitle("Faizan's Music Shop");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToCreateOrder(ActionEvent actionEvent) throws IOException {
        if (userRole == UserRole.salesperson) {
            switchToCreateOrder(userRole);
        }
    }

    public void goToInventory(ActionEvent actionEvent) throws IOException {
        if (userRole == UserRole.manager) {
            switchToInventory(userRole);
        }
    }

    public void goToOrderHistory(ActionEvent actionEvent) {
        switchToOrderHistory(userRole);
    }

    private void switchToCreateOrder(UserRole userRole) throws IOException {
        if (userRole == UserRole.salesperson) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/Create-Order.fxml"));
                Parent root = loader.load();
                CreateOrderController controller = loader.getController();
                controller.initialize();
                emptyPanel.getChildren().setAll(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void switchToInventory(UserRole userRole) throws IOException {
        if (userRole == UserRole.manager) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/Inventory.fxml"));
                Parent root = loader.load();
                InventoryController controller = loader.getController();
                controller.initialize();
                emptyPanel.getChildren().setAll(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void switchToOrderHistory(UserRole userRole) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/Order-History.fxml"));
            Parent root = loader.load();
            OrderHistoryController controller = loader.getController();
            controller.initialize();
            emptyPanel.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
