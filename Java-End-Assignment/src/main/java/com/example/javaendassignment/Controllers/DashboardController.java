//package com.example.javaendassignment.Controllers;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class DashboardController {
//
//    @FXML
//    private Label welcomeLabel;
//
//    @FXML
//    private Label roleLabel;
//
//    @FXML
//    private Label dateTimetlabel;
//    public void initialize(String userName, String userRole) {
//        // Set user's name and role
//        welcomeLabel.setText("Welcome " + userName + "!");
//        roleLabel.setText("Your role is: " + userRole);
//
//        // Set current date and time
//        SimpleDateFormat dateFormat = new SimpleDateFormat("It is now: dd-MM-yyyy HH:mm:ss");
//        Date now = new Date();
//        dateTimetlabel.setText(dateFormat.format(now));
//    }
//}
