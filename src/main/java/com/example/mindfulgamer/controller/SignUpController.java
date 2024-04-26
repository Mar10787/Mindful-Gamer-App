package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    @FXML
    public Hyperlink login;
    @FXML
    public Button signup;
    @FXML
    public TextField fname, lname, email, password;
    @FXML
    public Label signup_title;
    @FXML
    public void initialize(){
        // initialise
        signup_title.setText("Sign Up!");

    }
}