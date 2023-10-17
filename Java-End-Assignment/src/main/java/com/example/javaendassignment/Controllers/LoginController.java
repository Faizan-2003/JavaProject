package com.example.javaendassignment.Controllers;
import com.example.javaendassignment.Model.User;
import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.MusicApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

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
    // Disable the login button initially
    loginButton.setDisable(true);

    // Listen for changes in the passwordField text
    passwordField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              // Check if the password meets the criteria
              boolean isValidPassword = isPasswordValid(newValue);
              // Enable the login button if the password is valid
              loginButton.setDisable(!isValidPassword);
            });
    }
    public boolean isPasswordValid(String password) {
        boolean hasSpecialChar = false;
        boolean hasUpperCase = false;
        boolean hasNumber = false;

        if (password.length() >= 8) {
            for (char c : password.toCharArray()) {
                if (!hasSpecialChar && "!@#$%^&*()".contains(String.valueOf(c))) {
                    hasSpecialChar = true;
                } else if (!hasUpperCase && Character.isUpperCase(c)) {
                    hasUpperCase = true;
                } else if (!hasNumber && Character.isDigit(c)) {
                    hasNumber = true;
                }

                if (hasSpecialChar && hasUpperCase && hasNumber) {
                    return true; // All criteria are met, so the password is valid
                }
            }
        }

        return false; // Password doesn't meet all the criteria
    }

    @FXML
    private void onLoginButtonClick() {
        String username = UsernameField.getText();
        String password = passwordField.getText();

        if (database.isValidUser(username, password)) {
            // User is authenticated, get user information and switch to the dashboard screen
            String userRole = database.getUserRole(username); // Retrieve the user's role
            switchToDashboardScreen(username, userRole);
        } else {
            // Invalid credentials, display an error message in red color
            ErrorMessage.setText("Invalid username or password");
        }

    }

    // Switch to the dashboard screen
    // Switch to the dashboard screen
    private void switchToDashboardScreen(String userName, String userRole) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MusicApplication.class.getResource("Dashboard.fxml"));
            Parent root = fxmlLoader.load();

            // Get the DashboardController instance
            DashboardController dashboardController = fxmlLoader.getController();

            // Retrieve the full name from the database based on the username
            String fullName = database.getUserFullName(userName);

            // Initialize the DashboardController with user information
            dashboardController.initialize(fullName, userRole);

            Stage stage = (Stage) UsernameField.getScene().getWindow();
            Scene scene = new Scene(root, 914, 515);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
