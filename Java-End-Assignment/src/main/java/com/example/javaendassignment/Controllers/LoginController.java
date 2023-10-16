package com.example.javaendassignment.Controllers;
import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

import javafx.scene.control.*;

public class LoginController {

    @FXML
    private TextField UsernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label ErrorMessage;

    @FXML
    private Button loginButton;
    private Database database;

    public void initialize(Database database) {
        this.database = database;
    }
    @FXML
    private void onLoginButtonClick() {
        String username = UsernameField.getText();
        String password = passwordField.getText();

        if (database.isValidUser(username, password)) {
            // User is authenticated, switch to the dashboard screen
            switchToDashboardScreen();
        } else {
            // Invalid credentials, display an error message
            ErrorMessage.setText("Invalid username or password");
        }
    }

    private void switchToDashboardScreen() {
        try {
            // Load the Dashboard.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dashboard.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new scene
            Scene scene = new Scene(root, 640, 480);

            // Get the stage
            Stage stage = (Stage) UsernameField.getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);
            stage.setTitle("Dashboard"); // You can set the title here if needed
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    public void onPasswordTextChange(){

    }
}


