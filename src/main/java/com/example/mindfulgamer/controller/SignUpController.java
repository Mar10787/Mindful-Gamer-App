package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.HelloApplication;
import com.example.mindfulgamer.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.mindfulgamer.model.IUserDAO;
import com.example.mindfulgamer.model.SqliteUserDAO;


import java.io.IOException;

public class SignUpController {
    private SqliteUserDAO userDAO;
    @FXML
    public Hyperlink login;
    @FXML
    public Button signup;
    @FXML
    public TextField fname, lname, email, password;


    @FXML
    public void initialize(){
        userDAO = new SqliteUserDAO();
        // Listen for changes in fname, lname, email and password
        fname.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        lname.textProperty().addListener((observable, oldValue, newValue) -> checkFields());

        email.textProperty().addListener((observable, oldValue, newValue) -> checkFields());

        password.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
    }

    public void checkFields(){
        String first_name = fname.getText();
        String last_name = lname.getText();
        String email_rec = email.getText();
        String password_rec = password.getText();

        // Enable sign up button if all parameters are not null
        signup.setDisable(first_name.isEmpty() || last_name.isEmpty() || email_rec.isEmpty() || password_rec.isEmpty());

        // Modify the method and create other methods here to check each field for allowable characters, might
        // need to alter signup-page.fxml to reflect changes such as password char or digit requirements, let users know
        // what requirements are for each field if there is any
    }

    @FXML
    protected void onSignUpButtonClick() throws IOException {
        String first_name = fname.getText();
        String last_name = lname.getText();
        String email_rec = email.getText();
        String password_rec = this.password.getText();

        // Checking password meets requirements
        if (!isValidPassword(password_rec)) {
            showAlert("Invalid Password", "Password must be at least 8 characters long, include one number and one uppercase letter.");
            return;
        }
        if (!isValidEmail(email_rec)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }

        User newUser = new User(first_name, last_name, email_rec, password_rec);
        userDAO.addUser(newUser);
        showSuccess("Sign Up Successful", "Welcome to Mindful Gamer!");

        // Redirect to the login page after signup
        Stage stage = (Stage) signup.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.LOGIN_W, HelloApplication.LOGIN_H);
        stage.setScene(scene);
    }
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$"; //Contains 1 digit, one uppercase letter, and at least 8 characters long
        return password.matches(regex);
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$"; // Contains characters before @ symbol, the @ symbol, letters after @ symbol, "." and letters after ".".
        return email.matches(regex);
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onLogInButtonClick() throws IOException{
        Stage stage = (Stage) signup.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.LOGIN_W, HelloApplication.LOGIN_H);
        stage.setScene(scene);
    }


    // Have a method where it check database and sees if there is already an existing user

    // MODIFY the UI to show the requirements for the password

    // What if user enters both incorrect email and password?
    // When its both incorrect, it displays password error indicating that it brushes pass the email, create a new window tab where it shows that email
    // and password are both incorrect whilst stating their requirements

}