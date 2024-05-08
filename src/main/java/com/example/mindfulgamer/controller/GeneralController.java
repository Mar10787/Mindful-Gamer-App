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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
        } else{
            List<Integer> gamingTime = userDAO.getGamingTimes(filteredList.get(0));
            List<String> startDate = userDAO.getStartDate(filteredList.get(0));
            updateChart(startDate,gamingTime);
            // Update the search results
        }
        searchResults.setItems(filteredList);
    }

    private void updateChart(List<String> startDates, List<Integer> gamingTimes) {
        // Perform your data update logic here based on the search data
        // Dummy example: Update chart with dummy data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Gaming Time");

        // Add some dummy data (replace with your actual data retrieval logic)

        for (int i = 0; i < startDates.size() && i < gamingTimes.size(); i++){
            String startDate = startDates.get(i);
            int gamingTime = gamingTimes.get(i);

            int xValue = i + 1;
            series.getData().add(new XYChart.Data<>(startDate,gamingTime));
        }
        // Set up a NumberAxis for the x-axis
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Date"); // Set x-axis label
        xAxis.setTickLabelRotation(90); // Rotate tick labels if needed

        // Clear existing data and set new axis
        barChart.getData().clear();
        barChart.setData(FXCollections.observableArrayList(series));
        barChart.setCategoryGap(0); // Optional: Set category gap to 0 for better alignment
        barChart.setBarGap(0); // Optional: Set bar gap to 0 for better alignment
        barChart.setHorizontalGridLinesVisible(false); // Optional: Hide horizontal grid lines
        barChart.setVerticalGridLinesVisible(false); // Optional: Hide vertical grid lines
        barChart.setLegendVisible(true); // Optional: Show legend
        barChart.setTitle("Gaming Time"); // Optional: Set chart title
        barChart.setAnimated(false); // Optional: Disable animation
        barChart.setLegendVisible(true); // Optional: Show legend
    }



}
