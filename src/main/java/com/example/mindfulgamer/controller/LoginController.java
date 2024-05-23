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

import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.ERROR;

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

    /**
     * Initializes the controller. Sets up the user DAO and adds listeners to form fields.
     */
    @FXML
    public void initialize(){
        userDAO = new SqliteUserDAO();
        // Listen for changes in email and password fields
        input_email.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        input_password.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        // Initially disable the login button
        login_button.setDisable(true);
    }

    /**
     * Checks if the email and password fields are non-empty and enables/disables the login button accordingly.
     */
    private void checkFields(){
        String email = input_email.getText();
        String password = input_password.getText();
        login_button.setDisable(email.isEmpty() || password.isEmpty());
    }

    /**
     * Handles the login button click event. Verifies user credentials and loads the home page if successful.
     */
    @FXML
    protected void onLoginButtonClick() throws IOException {
        String email = input_email.getText().toLowerCase();
        String password = input_password.getText();
        User user = userDAO.getUserByEmail(email);


        // TODO FIX CODE TO MAKE LOGIN WORK PROPERLY      FIX CODE TO MAKE LOGIN WORK PROPERLY      FIX CODE TO MAKE LOGIN WORK PROPERLY
        // TODO FIX CODE TO MAKE LOGIN WORK PROPERLY      FIX CODE TO MAKE LOGIN WORK PROPERLY      FIX CODE TO MAKE LOGIN WORK PROPERLY
        // TODO FIX CODE TO MAKE LOGIN WORK PROPERLY      FIX CODE TO MAKE LOGIN WORK PROPERLY      FIX CODE TO MAKE LOGIN WORK PROPERLY
        // TODO FIX CODE TO MAKE LOGIN WORK PROPERLY      FIX CODE TO MAKE LOGIN WORK PROPERLY      FIX CODE TO MAKE LOGIN WORK PROPERLY
        // TODO FIX CODE TO MAKE LOGIN WORK PROPERLY      FIX CODE TO MAKE LOGIN WORK PROPERLY      FIX CODE TO MAKE LOGIN WORK PROPERLY


        if (user==null/*user != null && HashUtil.hash(password).equals(user.getPassword())*/) {
            Stage stage = (Stage) login_button.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

            // Load the dashboard data
            GeneralController controller = fxmlLoader.getController();
            controller.loadDashboardData();
        } else {
            showAlert("Login Failed", "Incorrect email or password.", ERROR);
        }
    }



    /**
     * Displays an alert dialog with the given title and message.
     *
     * @param title   the title of the alert dialog.
     * @param message the message to be displayed in the alert dialog.
     */
    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles sign-up hyperlink click event. Loads the sign-up page.
     */
    @FXML
    protected void onSignUpHyperlinkClick() throws IOException{
        Stage stage = (Stage) signup_hyperlink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.LOGIN_W, HelloApplication.LOGIN_H);
        stage.setScene(scene);
    }
}
