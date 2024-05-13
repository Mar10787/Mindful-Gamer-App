package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.HelloApplication;
import com.example.mindfulgamer.model.SqliteUserDAO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.*;

public class GeneralController {
    // This class is used to create the methods for clicking the buttons found in each window
    // used to code redundancy

    // Constructors
    SqliteUserDAO userDAO = new SqliteUserDAO();


    @FXML
    public Button dashboard, gaming_time, reminders, goals, achievements, logout, openAddGamingTimePage, cancel, search_button;

    @FXML
    private TextField searchField;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private ListView<String> searchResults;
    public ListView<String> gamesPlayedLastWeek;

    @FXML
    public void Dashboard(){

    }
    @FXML
    public void Gaming_Time() throws IOException {
        Stage stage = (Stage) gaming_time.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-time.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        GeneralController controller = fxmlLoader.getController();
        controller.loadInitialData();
    }
    @FXML
    private void openAddGamingTimePage() throws IOException {
        Stage stage = (Stage) openAddGamingTimePage.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-gamingtime-manually.fxml"));
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
    public void Profile(){

    }

    @FXML
    // 1. It feeds the information from the database and changes the table
    private void search(){
        ObservableList<String> games = FXCollections.observableArrayList(userDAO.fetchAllGameNames());
        String query = searchField.getText().toLowerCase();
        ObservableList<String> filteredList = FXCollections.observableArrayList();

        for (String game: games){
            if (game.toLowerCase().contains(query)){
                filteredList.add(game);
            }
        }

        if (filteredList.isEmpty()){
            // Display a pop-up message indicating that the game does not exist
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Not Found");
            alert.setHeaderText(null);
            alert.setContentText("The game \"" + query + "\" does not exist.");
            alert.showAndWait();
        }

        // If user searches nothing in search bar, default to all games
        else if (query.equals("")){
            Map<String, Integer> gamingTimeByGame = new TreeMap<>();
            // Should only be fetching games that are within past 7 days of being played
            ObservableList<String> allGames = userDAO.fetchAllGameNames();

            for (String game : allGames) {
                List<String> startDates = userDAO.getStartDate(game);
                List<Integer> gamingTimes = userDAO.getGamingTimes(game);

                int totalGamingTime = 0;
                for (int i = 0; i < startDates.size(); i++) {
                    totalGamingTime += gamingTimes.get(i);
                }
                gamingTimeByGame.put(game, totalGamingTime);
            }

            List<String> gameNames = new ArrayList<>();
            List<Integer> gamingTimes = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : gamingTimeByGame.entrySet()) {
                gameNames.add(entry.getKey());
                gamingTimes.add(entry.getValue());
            }
            updateChart("",gameNames,gamingTimes);
        }
        else{
            List<Integer> gamingTime = userDAO.getGamingTimes(filteredList.get(0));
            List<String> startDate = userDAO.getStartDate(filteredList.get(0));
            updateChart(filteredList.get(0), startDate,gamingTime);
            // Update the search results
        }
        searchResults.setItems(filteredList);
    }

    private void updateChart(String gameName, List<String> dates, List<Integer> times) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Gaming Time");

        for (int i = 0; i < dates.size(); i++) {
            series.getData().add(new XYChart.Data<>(dates.get(i), times.get(i)));
        }
        // Clear existing data and set new axis
        barChart.getData().clear();
        barChart.setData(FXCollections.observableArrayList(series));
        barChart.setCategoryGap(0); // Optional: Set category gap to 0 for better alignment
        barChart.setBarGap(0); // Optional: Set bar gap to 0 for better alignment
        barChart.setHorizontalGridLinesVisible(false); // Optional: Hide horizontal grid lines
        barChart.setVerticalGridLinesVisible(false); // Optional: Hide vertical grid lines
        barChart.setLegendVisible(true); // Optional: Show legend
        if (gameName != null){
            barChart.setTitle("Game Time From Past 7 Days For " + gameName);
        }
        else{
            barChart.setTitle("Game Time From Past 7 Days");
        }
        barChart.setAnimated(false); // Optional: Disable animation
        barChart.setLegendVisible(true); // Optional: Show legend

        barChart.getXAxis().setTickLabelRotation(0);
        // Explicitly set CSS style to ensure horizontal display of tick labels and adjust font size
        barChart.getXAxis().setStyle("-fx-tick-label-rotation: 0; -fx-font-size: 10px;");
    }



    public void loadInitialData() {
        ObservableList<String> allGames = userDAO.fetchAllGameNames();
        Map<Date, Integer> gamingTimeByDate = new TreeMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        for (String game : allGames) {
            List<String> startDates = userDAO.getStartDate(game);
            List<Integer> gamingTimes = userDAO.getGamingTimes(game);

            for (int i = 0; i < startDates.size(); i++) {
                try {
                    Date date = dateFormat.parse(startDates.get(i));
                    gamingTimeByDate.put(date, gamingTimeByDate.getOrDefault(date, 0) + gamingTimes.get(i));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        // Update chart
        List<String> sortedDates = new ArrayList<>();
        List<Integer> sortedTimes = new ArrayList<>();
        for (Map.Entry<Date, Integer> entry : gamingTimeByDate.entrySet()) {
            sortedDates.add(dateFormat.format(entry.getKey()));
            sortedTimes.add(entry.getValue());
        }
        String gameName = null;
        updateChart(gameName,sortedDates, sortedTimes);

        // Update list of games played last week
        List<String> gamesLastWeek = userDAO.getGamesPlayedLast7Days();
        gamesPlayedLastWeek.setItems(FXCollections.observableArrayList(gamesLastWeek));
    }

    @FXML
    private void handleGameSelection(MouseEvent event) {
        String selectedGame = gamesPlayedLastWeek.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            updateGameChart(selectedGame);
        }
    }

    private void updateGameChart(String gameName) {
        List<String> dates = userDAO.getStartDate(gameName);
        List<Integer> times = userDAO.getGamingTimes(gameName);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(gameName); // Setting the series name to the game name

        for (int i = 0; i < dates.size(); i++) {
            series.getData().add(new XYChart.Data<>(dates.get(i), times.get(i)));
        }

        barChart.getData().clear();
        barChart.getData().add(series);
        barChart.setTitle("Game Time From Past 7 Days For " + gameName); // Optionally set the chart title to reflect the selected game
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