package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.HelloApplication;
import com.example.mindfulgamer.model.SqliteUserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class GeneralController {
    // This class is used to create the methods for clicking the buttons found in each window
    // used to code redundancy

    // Constructors
    SqliteUserDAO userDAO = new SqliteUserDAO();


    @FXML
    public Button dashboard, gaming_time, reminders, goals, achievements, healthy_habits, logout, search_button;
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




}
