package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Model.UserRole;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import javax.security.auth.login.AccountLockedException;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class LoginController {

  @FXML private TextField UsernameField;

  @FXML private PasswordField passwordField;

  @FXML private Label ErrorMessage;

  @FXML private Button loginButton;

  private Database database;
  private int loginAttempts = 0;

  public void initialize(Database database) {
    this.database = database;

    loginButton.setDisable(true);

    passwordField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              boolean isValidPassword = isPasswordValid(newValue);
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
        return true;
      }
    }
    return false;
  }

  @FXML
  private void onLoginButtonClick() {
    String username = UsernameField.getText();
    String password = passwordField.getText();

    if (database.isValidUser(username, password)) {
      openMainWindow(username);
    } else {
      loginAttempts++;

      if (loginAttempts >= 3) {
          try {
          throw new AccountLockedException("Account Locked");
        } catch (AccountLockedException e) {
          handleAccountLockedException();
          return;
        }
      } else {
        ErrorMessage.setText("Invalid username or password");
      }
    }
  }
  private void handleAccountLockedException() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Exception");
    alert.setHeaderText("Account Locked");
    alert.setContentText("Your account has been locked");

    ButtonType okButton = new ButtonType("OK");
    alert.getButtonTypes().setAll(okButton);

    alert
        .showAndWait()
        .ifPresent(
            response -> {
              if (response == okButton) {
                System.exit(0);
              }
            });
  }

  private void openMainWindow(String username) {
    try {
      FXMLLoader loader =
          new FXMLLoader(
              getClass().getResource("/com/example/javaendassignment/MainDashboard.fxml"));
      Parent root = loader.load();
      MainDashboardController mainWindowController = loader.getController();
      mainWindowController.initialize(database);

      UserRole userRole = UserRole.valueOf(String.valueOf(database.getUserRole(username)));
      String userName = database.getUserFullName(username);
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      Date now = new Date();
      String dateTime = dateFormat.format(now);

      mainWindowController.setUserData(userName, userRole, dateTime);

      Stage stage = (Stage) loginButton.getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setTitle("Faizan's Music Shop");
      stage.setScene(scene);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
