package com.example.javaendassignment;

import com.example.javaendassignment.Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.javaendassignment.Database.Database;

public class MusicApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Database database = new Database();

        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent loginRoot = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        loginController.initialize(database);

        Scene loginScene = new Scene(loginRoot);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
}