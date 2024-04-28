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

public class LoginController {
    private SqliteUserDAO userDAO;
    @FXML
    private TextField input_email;
    @FXML
    private PasswordField input_password;
    @FXML
    private Button login_button;
    @FXML
    private Hyperlink signup_hyperlink;

    @FXML
    public void initialize(){
        userDAO = new SqliteUserDAO();
        // Listen for changes in email and password fields
        input_email.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        input_password.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        // Initially disable the login button
        login_button.setDisable(true);
    }

    private void checkFields(){
        String email = input_email.getText();
        String password = input_password.getText();
        login_button.setDisable(email.isEmpty() || password.isEmpty());
    }

    @FXML
    protected void onLoginButtonClick() throws IOException {
        String email = input_email.getText().toLowerCase();
        String password = input_password.getText();
        User user = userDAO.getUserByEmail(email);

        if (user != null && password.equals(user.getPassword())) {
            Stage stage = (Stage) login_button.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-time.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        } else {
            showAlert("Login Failed", "Incorrect email or password.");
        }
    }

    // Incorrect details displayed
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void onSignUpHyperlinkClick() throws IOException{
        Stage stage = (Stage) signup_hyperlink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.LOGIN_W, HelloApplication.LOGIN_H);
        stage.setScene(scene);
    }
}
