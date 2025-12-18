package com.example;

import com.example.VUE.Dashboard;
import com.example.VUE.LoginUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        primaryStage.setTitle("BANKTERM - Système Bancaire");
        primaryStage.setWidth(1080);
        primaryStage.setHeight(720);
        primaryStage.setResizable(true);

        showLoginScreen();
        primaryStage.show();
    }

    private void showLoginScreen() {
        LoginUI loginUI = new LoginUI();
        Scene scene = new Scene(loginUI, 1080, 720);
        primaryStage.setScene(scene);
    }

    private void showDashboard() {
        Dashboard dashboard = new Dashboard();
        Scene scene = new Scene(dashboard, 1080, 720);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}