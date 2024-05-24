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
     * Gets a user by email
     * @param email String, email trying to find
     * @return User, The user
     */
    User getUserByEmail(String email);
}