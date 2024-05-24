package com.example.mindfulgamer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    // Constants defining the window title and size
    public static final String TITLE = "Mindful Gamer";
    public static final int LOGIN_W = 333;
    public static final int LOGIN_H = 400;

    /**
     * Method use to start the program
     * @param stage
     * @throws IOException throws the error in the terminal in case of unexpected errors
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), LOGIN_W, LOGIN_H);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}