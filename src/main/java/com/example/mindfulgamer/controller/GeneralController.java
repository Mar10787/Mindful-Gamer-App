package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.HelloApplication;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    public void Goals() throws IOException {
        Stage stage = (Stage) goals.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("goals.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }


    @FXML
    public void Achievements(){

    }
    @FXML
    public void Timer() throws IOException {
        Stage stage = (Stage) reminders.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("timer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
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
    public void Profile() {
    }

    @FXML
    private Label timerLabel;

    @FXML
    private MenuButton timerinterval;

    private Timeline timeline;
    private long startTime = 0;
    private long pausedTime = 0;
    private boolean isRunning = false;

    // Method to start the timer
    public void startTimer(ActionEvent event) {
        if (!isRunning) {
            if (pausedTime == 0) {
                startTime = System.currentTimeMillis();
            } else {
                startTime = System.currentTimeMillis() - pausedTime;
                pausedTime = 0;
            }
            isRunning = true;

            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    // Method to stop/pause the timer
    public void stopTimer(ActionEvent event) {
        if (isRunning) {
            timeline.stop();
            isRunning = false;
            pausedTime = System.currentTimeMillis() - startTime;
        }
        // Ensure the timeline is stopped to prevent intervals from triggering when the timer is paused
        if (timeline != null) {
            timeline.stop();
        }
    }

    // Method to display the interval reminder popup
    public void displayIntervalPopup() {
        // Pause the timer
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reminder");
            alert.setHeaderText(null);
            alert.setContentText("You have reached your limit.");

            ButtonType stopPlayingButton = new ButtonType("Stop playing");
            ButtonType continuePlayingButton = new ButtonType("Continue playing");

            alert.getButtonTypes().setAll(stopPlayingButton, continuePlayingButton);

            // Show the alert and wait for user interaction
            alert.showAndWait().ifPresent(response -> {
                if (response == stopPlayingButton) {
                    resetTimer(null);
                    // METHOD TO ADD TIME TO TOTAL TIME
                }
            });
        });
    }

    // Method to reset the timer
    public void resetTimer(ActionEvent event) {
        timeline.stop();
        isRunning = false;
        startTime = 0;
        pausedTime = 0;
        // Reset timer label or any other necessary UI components
        timerLabel.setText("00:00:00");
    }

    // Method to handle no reminder option
    public void noReminder(ActionEvent event) {
        timerinterval.setText("No Reminder");
    }
    public void setInterval1(ActionEvent event) {
        timerinterval.setText("1m");
        setInterval(Duration.seconds(10));
    }
    // Method to set interval to 15 minutes
    public void setInterval15(ActionEvent event) {
        timerinterval.setText("15m");
        setInterval(Duration.minutes(15));
    }

    // Method to set interval to 30 minutes
    public void setInterval30(ActionEvent event) {
        timerinterval.setText("30m");
        setInterval(Duration.minutes(30));
    }

    // Method to set interval to 1 hour
    public void setInterval1h(ActionEvent event) {
        timerinterval.setText("1h");
        setInterval(Duration.hours(1));
    }

    // Method to set interval to 2 hours
    public void setInterval2h(ActionEvent event) {
        timerinterval.setText("2h");
        setInterval(Duration.hours(2));
    }

    // Method to set the timer interval
    private void setInterval(Duration interval) {
        // Pause the timer if it's running
        if (isRunning) {
            stopTimer(null); // Call stopTimer method to pause the timer
        }

        // Clear existing key frames
        timeline.getKeyFrames().clear();

        // Add a key frame to trigger the display of the interval popup after the specified interval
        timeline.getKeyFrames().add(new KeyFrame(interval, e -> {
            displayIntervalPopup(); // Show the popup
        }));

        // Start the timeline if it's not already running
        if (!timeline.getStatus().equals(Animation.Status.RUNNING)) {
            timeline.play();
        }
    }

    // Method to update the timer label
    private void updateTimer() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        int hours = (int) (elapsedTime / 3600000);
        int minutes = (int) ((elapsedTime / 60000) % 60);
        int seconds = (int) ((elapsedTime / 1000) % 60);

        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }
}