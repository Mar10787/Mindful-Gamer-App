package com.example.mindfulgamer.util;
import javafx.scene.control.Label;
public class PasswordStrengthMeter implements Observer{
    private Label strengthLabel;

    /**
     * Method is used to create a label for the Password Strength Meter
     * @param strengthLabel Label used for the FXML in sign-up page
     */
    public PasswordStrengthMeter(Label strengthLabel){
        this.strengthLabel = strengthLabel;
    }

    /**
     * Updates the label to indicate whether the password is weak, medium or strong
     * @param password String the input of the password textfield
     */
    @Override
    public void update(String password) {
        String strength = calculateStrength(password);
        strengthLabel.setText("Password Strength: " + strength);
    }

    /**
     * Algorithm to calculate the passwords strength, just using character length
     * @param password, The string used in the password textfield
     * @return A string indicating the strength of the input
     */
    private String calculateStrength(String password) {
        if (password.length() < 6){
            return "Weak";
        }
        else if (password.length() < 8) {
            return "Medium";
        } else {
            return "Strong";
        }
    }
}
