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
    private Label UserRoleLable;

    @FXML
    private Label DateTimeLable;

    public void initialize(String userName, String userRole) {
        // Set user's name and role
        WelcomeLabel.setText("Welcome " + userName + "!");
        UserRoleLable.setText(userRole);

        // Set current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date now = new Date();
        DateTimeLable.setText(dateFormat.format(now));
    }

}
