package com.example.mindfulgamer.controller;
import com.example.mindfulgamer.HelloApplication;
import com.example.mindfulgamer.model.SqliteUserDAO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class GeneralController {
    /**
     * Class used for the general controller of the application. Ranges from page directions, bar updates etc
     */

    // Utilizing other classes
    SqliteUserDAO userDAO = new SqliteUserDAO();
    LoginController loginController = new LoginController();

    @FXML
    public Button dashboard, gaming_time, reminders, goals, achievements, logout, plus, cancel, done, search_button, openAddGamingTime;
    @FXML
    private TextField searchField, gameTitle, hours;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private ListView<String> searchResults, gamesPlayedLastWeek;
    @FXML
    private MenuButton timerinterval;
    @FXML
    private Label timerLabel;

    @FXML
    private Label totalHoursLabel;

    @FXML
    public void Dashboard() throws IOException {
        Stage stage = (Stage) dashboard.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        // After setting the scene, initialize the dashboard data
        GeneralController controller = fxmlLoader.getController();
        controller.loadDashboardData();
    }

    public void loadDashboardData() {
        int totalHours = calculateTotalHours();
        Platform.runLater(() -> totalHoursLabel.setText(String.valueOf(totalHours)));
    }

    private int calculateTotalHours() {
        int totalHours = 0;
        ObservableList<String> allGames = userDAO.fetchAllGameNames();
        for (String game : allGames) {
            List<Integer> gamingTimes = userDAO.getGamingTimes(game);
            for (int gamingTime : gamingTimes) {
                totalHours += gamingTime;
            }
        }
        return totalHours;
    }

    /**
     * Takes the user to the game_time page
     * @throws IOException
     */
    @FXML
    public void Gaming_Time() throws IOException {
        Stage stage = (Stage) gaming_time.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-time.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        GeneralController controller = fxmlLoader.getController();
        controller.loadInitialData();
    }
    /**
     * Takes the user to the game_time page when clicking cancel button on manual time page
     * @throws IOException
     */
    @FXML
    public void cancel() throws IOException{
        Stage stage = (Stage) cancel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-time.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        GeneralController controller = fxmlLoader.getController();
        controller.loadInitialData();
    }
    /**
     * Takes the user to the add manual game time page for users to manually store in data
     * @throws IOException
     */
    @FXML
    public void OpenAddGamingTime() throws IOException {
        Stage stage = (Stage) openAddGamingTime.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-gamingtime-manually.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
    /**
     * Takes the user to the reminders page
     * @throws IOException
     */
    @FXML
    public void Reminders() throws IOException {
        Stage stage = (Stage) reminders.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reminders.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
    /**
     * Takes the user to the goals page
     * @throws IOException
     */
    @FXML
    public void Goals() throws IOException {
        Stage stage = (Stage) goals.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("goals.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
    /**
     * Takes the user to the achievements page
     * @throws IOException
     */
    @FXML
    public void Achievements(){

    }
    /**
     * Takes the user to the Timer page
     * @throws IOException
     */
    @FXML
    public void Timer() throws IOException {
        Stage stage = (Stage) reminders.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("timer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
    /**
     * Takes the user to the log in page
     * @throws IOException
     */
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

    // Variables
    private Duration intervalDuration;
    private Timeline timeline;
    private long startTime = 0, pausedTime = 0;
    private boolean isRunning = false;
    private String startDate, gameName, gameTime;


    /**
     * Search Method used to search for games in the gaming_time page, vital for extracting data and displaying the
     * bar graph accordingly
     * Returns nothing, mainly used to set variables
     */
    @FXML
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
            Alert alert = new Alert(INFORMATION);
            alert.setTitle("Game Not Found");
            alert.setHeaderText(null);
            alert.setContentText("The game \"" + query + "\" does not exist.");
            alert.showAndWait();
        }

        // If user searches nothing in search bar, default to all games
        else if (query.isEmpty()){
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

    /**
     * Used to update the bar graph with the wanted game all from the database
     * @param gameName The name of the game wanted to select data from
     * @param dates list of all the dates associated to the gameName
     * @param times list of all the game time associated to the gameName
     */
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


    /**
     * initialising the Data for the bar graph such that each time the page load the bar graph is not empty
     * It will display x-axis with previous 7 dates and y-axis is hours
     */
    public void loadInitialData() {
        ObservableList<String> allGames = userDAO.fetchAllGameNames();
        Map<Date, Integer> gamingTimeByDate = new TreeMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

    /**
     * Here is used to update the drop-down list of all games played within the past 7 days
     * @param event A FXML mouseEvent object
     */
    @FXML
    private void handleGameSelection(MouseEvent event) {
        String selectedGame = gamesPlayedLastWeek.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            updateGameChart(selectedGame);
        }
    }

    /**
     * Similiar to updateChart however, this makes the x-axis the game names and displays hours for each game
     * @param gameName String of the game's Name
     */
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
        barChart.setAnimated(false); // Optional: Disable animation
        barChart.setLegendVisible(true); // Optional: Show legend

        barChart.getXAxis().setTickLabelRotation(0);
        // Explicitly set CSS style to ensure horizontal display of tick labels and adjust font size
        barChart.getXAxis().setStyle("-fx-tick-label-rotation: 0; -fx-font-size: 10px;");
    }


    /**
     * Method used to start the timer, here it also tracks time which is vital for saving into database
     */
    public void startTimer() {
        LocalDate startGame = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        startDate = startGame.format(formatter);

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

    /**
     * Method used to stop the timer, here it also assigns a value to the gameTime variable declared at the start
     */
    public void stopTimer() {
        // Here it should save into database
        if (isRunning) {
            timeline.stop();
            isRunning = false;
            pausedTime = System.currentTimeMillis() - startTime;
            // Converting mm to hh:mm:ss
            gameTime = convertMillisecondssToTimeString(pausedTime);
        }
        // Ensure the timeline is stopped to prevent intervals from triggering when the timer is paused
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Method to reset the timer, so starts back from zero
     */
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
            String title = "Time Recorded";
            String desc = "Your time has been recorded. You have completed a session time of " + gameTime;
            loginController.showAlert(title, desc, INFORMATION);
        });
    }

    /**
     * Method used to display an interval pop for every interval that the user has selected
     */
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
                    stopTimer();
                    gameTime = convertMillisecondssToTimeString(pausedTime);
                    InsertData();
                    resetTimer(); // Reset the timer
                    displayTimeRecordedPopup(); // Call the new method to display time recorded reminder
                }
            });
        });

    }

    /**
     * Method used to update the timer label
     */
    private void updateTimer() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        int hours = (int) (elapsedTime / 3600000);
        int minutes = (int) ((elapsedTime / 60000) % 60);
        int seconds = (int) ((elapsedTime / 1000) % 60);

        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    /**
     * Method to set interval to 10 seconds, mainly used for testing
     */
    @FXML
    public void setIntervalTest() {
        timerinterval.setText("Test");
        intervalDuration = Duration.seconds(10); // Set interval duration to 10 seconds
    }

    /**
     * Method to set interval to 15 minutes
     */
    @FXML
    public void setInterval15() {
        timerinterval.setText("15m");
        intervalDuration = Duration.minutes(15); // Set interval duration to 15 minutes
    }

    /**
     * Method to set interval to 30 minutes
     */
    @FXML
    public void setInterval30() {
        timerinterval.setText("30m");
        intervalDuration = Duration.minutes(30); // Set interval duration to 30 minutes
    }

    /**
     * Method to set interval to 1 hour
     */
    @FXML
    public void setInterval1h() {
        timerinterval.setText("1h");
        intervalDuration = Duration.hours(1); // Set interval duration to 1 hour
    }

    /**
     * Method to set interval to 2 hours
     */
    @FXML
    public void setInterval2h() {
        timerinterval.setText("2h");
        intervalDuration = Duration.hours(2); // Set interval duration to 2 hours
    }

    /**
     * Method to have an interval of infinite, essentially creating no reminders
     */
    @FXML
    public void noReminder() {
        timerinterval.setText("No Reminder");
        intervalDuration = Duration.hours(999);
    }

    /**
     * Method used to assign the gameTitle(From texfield) to the string gameName initialised
     */
    public void addGameTitle() {
        gameName = gameTitle.getText();
    }

    /**
     * Method used to assign the gameHours from manual page
     */
    public void addGameHours() {
        gameTime = hours.getText();
    }

    /**
     * Converts milliseconds to standard format
     * @param milliseconds the time from the stopwatch in milliseconds
     * @return a string in the format "hh:mm:ss", vital format for database
     */
    public static String convertMillisecondssToTimeString(long milliseconds){
        // Calculate hours, minutes and seconds
        long seconds = milliseconds / 1000;
        long hours = seconds /3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    /**
     * Method used to insert data from UI to the database to be later used for bargraphs
     */
    public void InsertData(){
        // Testing, presentation may need to hard code gameTime for hours
        // Convert String to sql Data type
        if(gameName != null) {
            if (!gameName.isEmpty()) {
                userDAO.addGameTime(gameName, startDate, startDate, gameTime);
            }
            stopTimer();
            resetTimer();
        }
    }
    public void InsertDataManual() {
        // Testing, presentation may need to hard code gameTime for hours

        if (gameName != null) {
            if (!gameName.isEmpty()) {
                userDAO.addGameTime(gameName, startDate, startDate, gameTime);
            }
        }
    }
    /**
     * stores data when user clicks done on the manual game time page and creates alert pop u
     */
    public void AddManualTime() {
        LocalDate startGame = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        startDate = startGame.format(formatter);
        addGameHours();
        InsertDataManual();
        String title = "Manual Data Stored";
        String message = "Congrats! You have succesfully stored your session. Feel free to add more, if not " +
                "just press cancel to be redirected to the Gaming Time Page";

        loginController.showAlert(title, message, INFORMATION);
    }
}