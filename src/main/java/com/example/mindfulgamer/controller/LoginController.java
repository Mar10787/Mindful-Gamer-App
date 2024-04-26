package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField input_email, input_password;
    @FXML
    private Button login_button;
    @FXML
    private Hyperlink signup_hyperlink;
    @FXML
    public void initialize(){
        /**
         * Initialises the login page, mainly used to listen to the text being written by user
         */
        // Listen for changes in email and password fields
        input_email.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        input_password.textProperty().addListener((observable, oldValue, newValue) -> checkFields());

        // Initially disable the login button
        login_button.setDisable(true);
    }

    // Method to check if both email and password fields are filled
    // *ALTER TO READ DATABASE*
    private void checkFields(){
        /**
         * This method is used to check that both fields are entered before the login button can be used
         */
        String email = input_email.getText();
        String password = input_password.getText();

        // Enable the login button only if both fields are not null
        login_button.setDisable(email.isEmpty() || password.isEmpty());
    }

    @FXML
    protected void onLoginButtonClick() throws IOException {
        /**
         * Method used to direct the user to the next window of the MindfulGamer Application
         */
        Stage stage = (Stage) login_button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    // Needs to have a way to hide password when being entered

    // HyperLink has also not been implemented, not sure whether we will direct the user to website or another page, depending on the priority
    // Get this done in a timely manner
}