package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GeneralController {
    // This class is used to create the methods for clicking the buttons found in each window
    // used to code redundancy

    // Constructors
    @FXML
    public Button dashboard, gaming_time, reminders, goals, achievements, healthy_habits, logout, search_button;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> searchResults;
    private ObservableList<String> games = FXCollections.observableArrayList(
            "Game 1","Game 2","Game 3"
    );
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
    // Edit this such that 1. it uses the database, 2. It feeds the information from the database and changes the table
    private void search(){
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
            // Update the search results
        }
        searchResults.setItems(filteredList);
    }
}
