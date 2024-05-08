package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.HelloApplication;
import com.example.mindfulgamer.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
    }
    @FXML
    protected void onSignUpButtonClick() throws IOException {
        String first_name = fname.getText();
        String last_name = lname.getText();
        String email_rec = email.getText();
        String password_rec = password.getText();
        String phone = "";

        // Should check in database to see if the email is found, if yes then produce an error that says so otherwise

        // Checking both email and password first
        if (!isValidEmail(email_rec) && !isValidPassword(password_rec)){
            showAlert("Invalid Email and Password", "Please re-enter a valid email address and create a password that is 8 characters long, " +
                    "including one number and uppercase letter.");
            return;
        }
        // Checking email is valid
        else if (!isValidEmail(email_rec)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }
        // Checking password meets requirements
        else if (!isValidPassword(password_rec)) {

            showAlert("Invalid Password", "Password must be at least 8 characters long, include one number and one uppercase letter.");
            return;
        }

        String first_name_lower = first_name.toLowerCase();
        String last_name_lower = last_name.toLowerCase();
        String email_lower = email_rec.toLowerCase();

        // Check for email existing in the database
        if (userDAO.isEmailExist(email_lower)){
            showAlert("Email In Use", "This email is already being used.");
            return;
        }

        User newUser = new User(first_name_lower, last_name_lower, phone, email_lower, password_rec);
        userDAO.addUser(newUser);
        showSuccess("Sign Up Successful", "Welcome to Mindful Gamer!");

        // TESTING PURPOSES //
        // Deleting Database
        //userDAO.ClearData("users");
        // Reseting ID Count
        //userDAO.ResetID("users");
        //userDAO.insertSampleData();
        // TESTING PURPOSES //

        // Redirect to the login page after signup

        Stage stage = (Stage) signup.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.LOGIN_W, HelloApplication.LOGIN_H);
        stage.setScene(scene);
    }
    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$"; //Contains 1 digit, one uppercase letter, and at least 8 characters long
        return password.matches(regex);
    }

    public boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$"; // Contains characters before @ symbol, the @ symbol, letters after @ symbol, "." and letters after ".".
        return email.matches(regex);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccess(String title, String message) {
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
    // When i create a user, its not saving to the database
    // Also some of the users in the database shouldnt technically be allowed to be in there since the passwords are invalid, dont have to delete just fyi

    // MODIFY the UI to show the requirements for the password, ask alyssa for help, not really my strong suit here, or you could ask Ty

    // Im going to save first and last name as lowercase in user classes, so its easier in the long run

}