package com.example.mindfulgamer.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class TimerController {
    @FXML
    private Label timerLabel;

    private Timeline timeline;
    private long startTime = 0;
    private boolean isRunning = false;

    public void startTimer(ActionEvent event) {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;

            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    public void stopTimer(ActionEvent event) {
        if (isRunning) {
            timeline.stop();
            isRunning = false;
        }
    }

    public void resetTimer(ActionEvent event) {
        if (isRunning) {
            timeline.stop();
            isRunning = false;
        }
        timerLabel.setText("00:00:00");
    }

    private void updateTimer() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        int hours = (int) (elapsedTime / 3600000);
        int minutes = (int) ((elapsedTime / 60000) % 60);
        int seconds = (int) ((elapsedTime / 1000) % 60);

        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }
}
