package com.example.mindfulgamer.model;

/**
 * Represents a reminder set by user. Attributes include:
 * reminder title
 * description
 * frequency
 * time of day
 * Methods include handling tasks such as creating a new reminder, edit an existing one, and sending notifications to user when reminder due
 */
public class Reminder {
    /**
     * Initialising string variables
     */
    private String message;
    private String type;
    private String priority;

    /**
     * Method used to retrieve the class values
     * @param message String, string of message
     * @param type String the type of message
     * @param priority String the type of priority
     */
    public Reminder(String message, String type, String priority) {
        this.message = message;
        this.type = type;
        this.priority = priority;
    }

    /**
     * Used to get message
     * @return String message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Used to get type
     * @return String type
     */
    public String getType() {
        return type;
    }

    /**
     * USed to get priority
     * @return String priority
     */
    public String getPriority() {
        return priority;
    }


}
