package com.example.mindfulgamer.controller;

import com.example.mindfulgamer.HelloApplication;
import com.example.mindfulgamer.model.User;
import com.example.mindfulgamer.model.IUserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import com.example.mindfulgamer.model.SqliteUserDAO;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainController {
    @FXML
    private ListView<User> usersListView;
    private IUserDAO userDAO;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private VBox userContainer;
    public MainController() {
        userDAO = new SqliteUserDAO();
    }

    /**
     * Programmatically selects a user in the list view and
     * updates the text fields with the user's information.
     * @param user The user to select.
     */
    private void selectUser(User user) {
        usersListView.getSelectionModel().select(user);
        firstNameTextField.setText(user.getFirstName());
        lastNameTextField.setText(user.getLastName());
        phoneTextField.setText(user.getPhone());
        emailTextField.setText(user.getEmail());
    }

    /**
     * Renders a cell in the users list view by setting the text to the user's full name.
     * @param userListView The list view to render the cell for.
     * @return The rendered cell.
     */
    private ListCell<User> renderCell(ListView<User> userListView) {
        return new ListCell<>() {
            /**
             * Handles the event when a user is selected in the list view.
             * @param mouseEvent The event to handle.
             */
            private void onUserSelected(MouseEvent mouseEvent) {
                ListCell<User> clickedCell = (ListCell<User>) mouseEvent.getSource();
                // Get the selected user from the list view
                User selectedUser = clickedCell.getItem();
                if (selectedUser != null) selectUser(selectedUser);
            }

            /**
             * Updates the item in the cell by setting the text to the user's full name.
             * @param user The user to update the cell with.
             * @param empty Whether the cell is empty.
             */
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                // If the cell is empty, set the text to null, otherwise set it to the user's full name
                if (empty || user == null || user.getFullName() == null) {
                    setText(null);
                    super.setOnMouseClicked(this::onUserSelected);
                } else {
                    setText(user.getFullName());
                }
            }
        };
    }


    /**
     * Synchronizes the user list view with the user in the database.
     */
    private void syncUsers() {
        usersListView.getItems().clear();
        List<User> users = userDAO.getAllUsers();
        boolean hasUser = !users.isEmpty();
        if (hasUser) {
            usersListView.getItems().addAll(users);
        }
        // Show / hide based on whether there are users
        userContainer.setVisible(hasUser);
    }

    @FXML
    public void initialize() {
        usersListView.setCellFactory(this::renderCell);
        syncUsers();
        // Select the first user and display its information
        usersListView.getSelectionModel().selectFirst();
        User firstUser = usersListView.getSelectionModel().getSelectedItem();
        if (firstUser != null) {
            selectUser(firstUser);
        }
    }

    @FXML
    private void onEditConfirm() {
        // Get the selected user from the list view
        User selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            selectedUser.setFirstName(firstNameTextField.getText());
            selectedUser.setLastName(lastNameTextField.getText());
            selectedUser.setPhone(phoneTextField.getText());
            selectedUser.setEmail(emailTextField.getText());
            userDAO.updateUser(selectedUser);
            syncUsers();
        }
    }

    @FXML
    private void onDelete() {
        // Get the selected user from the list view
        User selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userDAO.deleteUser(selectedUser);
            syncUsers();
        }
    }

    @FXML
    private void onAdd() {
        // Default values for a new user
        final String DEFAULT_FIRST_NAME = "New";
        final String DEFAULT_LAST_NAME = "User";
        final String DEFAULT_PHONE = "";
        final String DEFAULT_EMAIL = "";
        final String DEFAULT_PASSWORD = "";
        User newUser = new User(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PHONE, DEFAULT_PASSWORD);
        // Add the new user to the database
        userDAO.addUser(newUser);
        syncUsers();
        // Select the new user in the list view
        // and focus the first name text field
        selectUser(newUser);
        firstNameTextField.requestFocus();
    }

    @FXML
    private void onCancel() {
        // Find the selected user
        User selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Since the user hasn't been modified,
            // we can just re-select it to refresh the text fields
            selectUser(selectedUser);
        }
    }

}