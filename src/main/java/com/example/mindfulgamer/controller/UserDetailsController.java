package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class UserDetailsController {
    @FXML
    private TextArea detailsTextArea;

    public void setUserDetails(User user) {
        String details = "user ID: " + user.getId() +
                "\nFirst Name: " + user.getFirstName() +
                "\nLast Name: " + user.getLastName() +
                "\nPhone: " + user.getPhone() +
                "\nEmail: " + user.getEmail();
        detailsTextArea.setText(details);
    }
}