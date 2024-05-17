package com.example.mindfulgamer.model;

import java.sql.Date;
import java.util.List;

/**
 * Interface for the user Data Access Object that handles
 * the CRUD operations for the user class with the database.
 */
public interface IUserDAO {
    /**
     * Adds a new user to the database.
     * @param user The user to add.
     */
    public void addUser(User user);


    /**
     * Updates an existing user in the dgitatabase.
     * @param user The user to update.
     */
    public void updateUser(User user);


    /**
     * Deletes a user from the database.
     * @param user The user to delete.
     */
    public void deleteUser(User user);


    /**
     * Adds game data to the gameTracking table
     * @param gameName the name of the game
     * @param startGame the date the game session started
     * @param endGame the date the game session ended
     * @param gamingTime the total time of the session
     */
    public void addGameTime(String gameName, String startGame, String endGame, String gamingTime);

    /**
     * Check database if email already exists
     * @param email The email entered for the sign up
     * @return if the email is found
     */
    public boolean isEmailExist(String email);

    /**
     * Clears all data from a given table
     * @param tablename the table chosen to delete all data
     */
    public void ClearData(String tablename);

    /**
     * Gets a single user
     * @param userId the ID of the user wanted to be retrieved
     * @return the user class
     */
    public User getUser(int userId);
    /**
     * Retrieves all users from the database.
     * @return A list of all users in the database.
     */
    public List<User> getAllUsers();
    User getUserByEmail(String email);
}