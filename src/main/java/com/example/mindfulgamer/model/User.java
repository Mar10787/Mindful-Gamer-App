package com.example.mindfulgamer.model;

/**
 * Class used for users
 */
public class User {
    /**
     * Initialising user attributes
     */
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;

    /**
     * Creates a user class
     * @param firstName String, the first name
     * @param lastName String, the last name
     * @param phone String, phone number
     * @param email String, email
     * @param password String, password
     */
    public User(String firstName, String lastName, String phone, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets first name of user
     * @return String firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets last name of user
     * @return String lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets email of user
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets phone number of user
     * @return String phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the password from user
     * @return String password
     */
    public String getPassword() {
        return password;
    }

}