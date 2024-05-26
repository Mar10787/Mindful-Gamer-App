package com.example.mindfulgamer.util;

public interface Subject {
    /**
     * Registers the observer
     * @param observer Observer used for detecting password
     */
    void registerObserver(Observer observer);

    /**
     * Notifies all registered observers byu calling their update method with the current password
     * This method is called when password field's text changes
     */
    void notifyObservers();
}
