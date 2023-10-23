package com.example.javaendassignment.Controllers;
import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.User;
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
import java.util.Date;
import java.text.SimpleDateFormat;

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
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
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

        for (char c : password.toCharArray()) {
            if ("!@#$%^&*()".contains(String.valueOf(c))) {
                hasSpecialChar = true;
            } else if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            }

            if (hasSpecialChar && hasUpperCase && hasNumber && password.length() >= 8) {
                return true; // Password is valid
            }
        }

        return false; // Password does not meet all criteria
    }

    @FXML
    private void onLoginButtonClick() {
        String username = UsernameField.getText();
        String password = passwordField.getText();

        if (database.isValidUser(username, password)) {
            // User is authenticated, open the main window
            openMainWindow(username);
        } else {
            // Invalid credentials, display an error message in red color
            ErrorMessage.setText("Invalid username or password");
        }
    }

    private void openMainWindow(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaendassignment/MainWindow.fxml"));
            Parent root = loader.load();

            MainWindowController mainWindowController = loader.getController();

            // Pass the database object to the MainWindowController
            mainWindowController.initialize(database);

            // Set the user's name, role, and date/time
            String userRole = String.valueOf(database.getUserRole(username));
            String userName = database.getUserFullName(username);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date now = new Date();
            String dateTime = dateFormat.format(now);

            // Pass the values to MainWindowController
            mainWindowController.setUserData(userName, userRole, dateTime);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
