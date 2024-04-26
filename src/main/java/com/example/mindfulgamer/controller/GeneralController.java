package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GeneralController {
    // This class is used to create the methods for clicking the buttons found in each window
    // used to code redundancy

    // Constructors
    @FXML
    public Button reminder_tab;
    @FXML
    public Button logout_tab;

    @FXML
    public void Dashboard(){

    }
    @FXML
    public void Gaming_Time(){

    }
    @FXML
    public void Reminders() throws IOException {
        Stage stage = (Stage) reminder_tab.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reminders.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @FXML
    public void Goals(){

    }

    @FXML
    public void Achievements(){

    }
    @FXML
    public void Healthy_Habits(){

    }

    @FXML
    public void Logout() throws IOException{
        Stage stage = (Stage) logout_tab.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
    @FXML
    public void Settings(){

    }
    @FXML
    public void Profile(){

    }
}
