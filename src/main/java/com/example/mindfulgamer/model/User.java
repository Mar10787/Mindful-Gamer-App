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
     * Gets ID of user
     * @return
     */
    public int getId() {return userId; }

    /**
     * Set the ID of
     * @param userId
     */
    public void setId(int userId) {this.userId = userId; }

    /**
     * Gets first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets last name
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets phone number
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets password
     * @return password
     */
    public String getPassword() {
        return password;
    }

}