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
import java.util.Optional;

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
    private MenuButton timerinterval;
    // Other fields and methods remain the same...
    private Duration intervalDuration; // Interval duration field
    private Timeline timeline;
    private long startTime = 0;
    private long pausedTime = 0;
    private boolean isRunning = false;

    @FXML
    private Label timerLabel;

    // Method to start the timer
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

            long[] intervalCount = {1}; // Using array to make it effectively final

            if (intervalDuration != null) {
                timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    updateTimer();

                    if ((System.currentTimeMillis() - startTime) >= intervalCount[0] * intervalDuration.toMillis()) {
                        displayIntervalPopup();
                        intervalCount[0]++;
                    }
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            }
        }
    }

    // Method to stop/pause the timer
    public void stopTimer() {
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

    // Method to reset the timer
    public void resetTimer() {
        timeline.stop();
        isRunning = false;
        startTime = 0;
        pausedTime = 0;
        // Reset timer label or any other necessary UI components
        timerLabel.setText("00:00:00");
    }
    public void displayTimeRecordedPopup() {
        Platform.runLater(() -> {
            Alert timeRecordedAlert = new Alert(Alert.AlertType.INFORMATION);
            timeRecordedAlert.setTitle("Time Recorded");
            timeRecordedAlert.setHeaderText(null);
            timeRecordedAlert.setContentText("Your time has been recorded. You now currently have X hours."); // Replace X with actual hours
            timeRecordedAlert.showAndWait();
        });
    }

    // Method to display the interval reminder popup
    public void displayIntervalPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reminder");
            alert.setHeaderText(null);
            alert.setContentText("You have reached your limit.");

            ButtonType stopPlayingButton = new ButtonType("Stop playing");
            ButtonType continuePlayingButton = new ButtonType("Continue playing");

            alert.getButtonTypes().setAll(stopPlayingButton, continuePlayingButton);

            Optional<ButtonType> result = alert.showAndWait();
            result.ifPresent(buttonType -> {
                if (buttonType == stopPlayingButton) {
                    resetTimer(); // Reset the timer
                    displayTimeRecordedPopup(); // Call the new method to display time recorded reminder
                }
            });
        });
    }

    // Method to update the timer label
    private void updateTimer() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        int hours = (int) (elapsedTime / 3600000);
        int minutes = (int) ((elapsedTime / 60000) % 60);
        int seconds = (int) ((elapsedTime / 1000) % 60);

        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    // Method to set the timer interval to 10 seconds
    @FXML
    public void setIntervalTest() {
        timerinterval.setText("Test");
        intervalDuration = Duration.seconds(10); // Set interval duration to 10 seconds
    }

    // Method to set the timer interval to 15 minutes
    @FXML
    public void setInterval15() {
        timerinterval.setText("15m");
        intervalDuration = Duration.minutes(15); // Set interval duration to 15 minutes
    }

    // Method to set the timer interval to 30 minutes
    @FXML
    public void setInterval30() {
        timerinterval.setText("30m");
        intervalDuration = Duration.minutes(30); // Set interval duration to 30 minutes
    }

    // Method to set the timer interval to 1 hour
    @FXML
    public void setInterval1h() {
        timerinterval.setText("1h");
        intervalDuration = Duration.hours(1); // Set interval duration to 1 hour
    }

    // Method to set the timer interval to 2 hours
    @FXML
    public void setInterval2h() {
        timerinterval.setText("2h");
        intervalDuration = Duration.hours(2); // Set interval duration to 2 hours
    }

    // Method to set no reminder interval
    @FXML
    public void noReminder() {
        timerinterval.setText("No Reminder");
        intervalDuration = Duration.hours(999);
    }
}