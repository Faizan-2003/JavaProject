package com.example.javaendassignment.Controllers;
import com.example.javaendassignment.Database.Database;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainWindowController {
    @FXML
    private VBox emptyPanel;
    private Database database;
    @FXML
    private Label labelWelcome;
    @FXML
    private Label labelRole;
    @FXML
    private Label labelDateTime;
    @FXML
    private Button buttonDashboard;
    String userName;
    String userRole;
    String dateTime;

    public void initialize(Database database) {
        this.database = database;
    }

    // Other fields as before...

    public void setUserData(String userName, String userRole, String dateTime) {
        labelWelcome.setText("Welcome " + userName + "!");
        labelRole.setText("Your role is: " + userRole);
        labelDateTime.setText("It is now: " + dateTime);

        // Set the values in the fields so they can be used when needed
        this.userName = userName;
        this.userRole = userRole;
        this.dateTime = dateTime;
    }

    public void goToDashboard(ActionEvent actionEvent) {
        // Get the reference to the Dashboard button's Scene and Stage
        Stage stage = (Stage) buttonDashboard.getScene().getWindow();

        // Load the MainWindow view and set user data
        openMainWindow(userName, stage);
    }

    // The rest of your code...

    private void openMainWindow(String username, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/MainWindow.fxml"));
            Parent root = loader.load();

            MainWindowController mainWindowController = loader.getController();

            // Pass the values to MainWindowController
            mainWindowController.setUserData(userName, userRole, dateTime);

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void goToCreateOrder(ActionEvent actionEvent) throws IOException {
        SwitchToCreateOrder();
    }


    public void goToInventory(ActionEvent actionEvent) throws IOException {
        SwitchToInventory();
    }

    public void goToOrderHistory(ActionEvent actionEvent) {
        SwitchToOrderHistory();
    }


    public void SwitchToCreateOrder() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/Create-Order.fxml"));
            Parent root = loader.load();
            CreateOrderController controller = loader.getController();
            controller.initialize();
            emptyPanel.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Add more detailed logging or handle the exception as needed
        }
    }


    public void SwitchToInventory() throws IOException {
        try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/Inventory.fxml"));
            Parent root = loader.load();
            InventoryController controller = loader.getController();
            controller.initialize();
            emptyPanel.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SwitchToOrderHistory(){
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
