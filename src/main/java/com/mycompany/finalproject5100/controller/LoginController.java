package com.mycompany.finalproject5100.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.mycompany.finalproject5100.App;
import com.mycompany.finalproject5100.model.User;
import com.mycompany.finalproject5100.model.SessionManager;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label errorLabel;  // Add this if not already present

    @FXML
    public void initialize() {
        // Initialize role options
        roleComboBox.getItems().addAll(
            "Store Owner",
            "Store Staff",
            "Delivery Admin",
            "Delivery Staff"
        );
    }
    
    @FXML
    private void handleLogin() {
        try {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleComboBox.getValue();
            
            // Basic validation
            if (email.isEmpty() || password.isEmpty() || role == null) {
                if (errorLabel != null) {
                    errorLabel.setText("Please fill in all fields");
                }
                return;
            }
            
            // Create user and set in session
            User user = new User(email, password, role);
            SessionManager.getInstance().setCurrentUser(user);
            
            // Debug print
            System.out.println("Attempting to navigate to dashboard for role: " + role);
            
            // Navigate to appropriate dashboard
            String dashboard = role.toLowerCase().contains("store") ? "store_dashboard" : "delivery_dashboard";
            App.setRoot(dashboard);
            
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            e.printStackTrace();
            if (errorLabel != null) {
                errorLabel.setText("Login failed: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void switchToSignUp() {
        try {
            App.setRoot("signup");
        } catch (Exception e) {
            System.out.println("Error switching to signup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}