package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardController {

    @FXML
    private Label WelcomeLabel;

    @FXML
    private Label UserRoleLabel;

    @FXML
    private Label DateTimeLabel;

    public void initialize(String userName, String userRole) {
        // Set user's name and role
        WelcomeLabel.setText("Welcome " + userName + "!");
        UserRoleLabel.setText(userRole);

        // Set current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date now = new Date();
        DateTimeLabel.setText(dateFormat.format(now));
    }

}
