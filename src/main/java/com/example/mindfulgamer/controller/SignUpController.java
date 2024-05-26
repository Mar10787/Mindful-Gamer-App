package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.HelloApplication;
import com.example.mindfulgamer.model.User;
import com.example.mindfulgamer.util.HashUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.mindfulgamer.model.SqliteUserDAO;
import com.example.mindfulgamer.util.PasswordStrengthMeter;
import com.example.mindfulgamer.util.Observer;
import com.example.mindfulgamer.util.Subject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to sigh up for application, has validation, reading database etc.
 */
public class SignUpController implements Subject{
    /**
     * Setting up ID for FXML and initialising variables
     */
    private List<Observer> observers = new ArrayList<>();
    private SqliteUserDAO userDAO;
    @FXML
    public Hyperlink login;
    @FXML
    public Button signup;
    @FXML
    public TextField fname, lname, email, password;
    @FXML
    public Label passwordStrengthLabel;
    /**
     * Initialises the page such that it can detect what is written inside the text boxes
     */
    @FXML
    public void initialize(){
        userDAO = new SqliteUserDAO();
        PasswordStrengthMeter passwordStrengthMeter = new PasswordStrengthMeter(passwordStrengthLabel);
        registerObserver(passwordStrengthMeter); // Registering the passwordStrengthMeter
        // Listen for changes in fname, lname, email and password
        fname.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        lname.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        email.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            validateForm();
            notifyObservers();
        });

        // Initial Form Validation
        validateForm();
    }
    /**
     * Validates the entire form and updates the sign-up button state.
     */
    public void validateForm(){
        boolean isFormValid = !fname.getText().isEmpty() &&
                !lname.getText().isEmpty() &&
                isValidEmail(email.getText()) &&
                isValidPassword(password.getText());
        signup.setDisable(!isFormValid);
    }


    /**
     * Directs the user to the loging page only if certain sign up conditions are met
     * @throws IOException throws the error in the terminal in case of unexpected errors
     */
    @FXML
    protected void onSignUpButtonClick() throws IOException {
        String first_name = fname.getText();
        String last_name = lname.getText();
        String email_rec = email.getText();
        String password_rec = password.getText();
        String phone = "";

        String first_name_lower = first_name.toLowerCase();
        String last_name_lower = last_name.toLowerCase();
        String email_lower = email_rec.toLowerCase();

        // Check for email existing in the database
        if (userDAO.isEmailExist(email_lower)){
            showAlert("Email In Use", "This email is already being used.");
            return;
        }
        String hashedPassword = HashUtil.hash(password_rec);
        User newUser = new User(first_name_lower, last_name_lower, phone, email_lower, hashedPassword);
        userDAO.addUser(newUser);
        showSuccess("Sign Up Successful", "Welcome to Mindful Gamer!");
        // TESTING PURPOSES //
        // Deleting Database
        // userDAO.ClearData("users");
        //userDAO.ClearData("gameTracking");
        //userDAO.insertSampleData();
        // TESTING PURPOSES //

        // Redirect to the login page after signup
        Stage stage = (Stage) signup.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.LOGIN_W, HelloApplication.LOGIN_H);
        stage.setScene(scene);
    }

    /**
     * Method used to check if password creation is valid
     * @param password String, the input of the string for password
     * @return bool, True if the string is allowed, false if otherwise
     */
    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$"; //Contains 1 digit, one uppercase letter, and at least 8 characters long
        return password.matches(regex);
    }

    /**
     * Method used to check if email creation is valid
     * @param email String, the input of the email
     * @return bool, True if string is allowed, false otherwise
     */
    public boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$"; // Contains characters before @ symbol, the @ symbol, letters after @ symbol, "." and letters after ".".
        return email.matches(regex);
    }

    /**
     * Method used to create an Alert pop up
     * @param title String, title of pop up
     * @param message String, message of popup
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Method used to create a success pop up
     * @param title String, title of pop up
     * @param message String, message of popup
     */
    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Method used to direct user back to log in page when sign up hyperlink is clicked
     * @throws IOException throws the error in the terminal in case of unexpected errors
     */
    @FXML
    public void onLogInButtonClick() throws IOException{
        Stage stage = (Stage) signup.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.LOGIN_W, HelloApplication.LOGIN_H);
        stage.setScene(scene);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        String password = this.password.getText();
        for (Observer observer : observers){
            observer.update(password);
        }
    }
}