package com.example.mindfulgamer.model;

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
     * Updates an existing user in the database.
     * @param user The user to update.
     */
    public void updateUser(User user);
    /**
     * Deletes a user from the database.
     * @param user The user to delete.
     */
    public void deleteUser(User user);
    /**
     * Retrieves a user from the database.
     * @param userId The id of the user to retrieve.
     * @return The user with the given id, or null if not found.
     */
    public User getUser(int userId);
    /**
     * Retrieves all users from the database.
     * @return A list of all users in the database.
     */
    public List<User> getAllUsers();
    User getUserByEmail(String email);
}