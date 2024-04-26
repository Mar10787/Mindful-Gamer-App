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
    public Button dashboard, gaming_time, reminders, goals, achievements, healthy_habits, logout;
    @FXML
    public void Dashboard(){

    }
    @FXML
    public void Gaming_Time() throws IOException{
        Stage stage = (Stage) gaming_time.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-time.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
    @FXML
    public void Reminders() throws IOException {
        Stage stage = (Stage) reminders.getScene().getWindow();
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
        Stage stage = (Stage) logout.getScene().getWindow();
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
