package com.example.mindfulgamer.model;

/**
 * Represents a reminder set by user. Attributes include:
 * reminder title
 * description
 * frequency
 * time of day
 *
 * Methods include handling tasks such as creating a new reminder, edit an existing one, and sending notifications to user when reminder due
 */
public class Reminder {
    private String message;
    private String type;
    private String priority;

    public Reminder(String message, String type, String priority) {
        this.message = message;
        this.type = type;
        this.priority = priority;
    }
    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
    public String getPriority() {
        return priority;
    }


}
