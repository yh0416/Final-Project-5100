package com.mycompany.finalproject5100.controllers;

import com.mycompany.finalproject5100.App;
import com.mycompany.finalproject5100.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class SignUpController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private CheckBox termsCheckBox;
    @FXML
    private TextField addressField;
    @FXML
    private Label addressError;

    @FXML
    private Label emailError;
    @FXML
    private Label passwordError;
    @FXML
    private Label confirmPasswordError;
    @FXML
    private Label roleError;
    @FXML
    private Label termsError;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll(
                "Store Owner",
                "Store Staff",
                "Delivery Admin",
                "Delivery Staff"
        );

        // Clear error messages when user types
        emailField.textProperty().addListener((obs, old, newVal) -> emailError.setVisible(false));
        passwordField.textProperty().addListener((obs, old, newVal) -> passwordError.setVisible(false));
        confirmPasswordField.textProperty().addListener((obs, old, newVal) -> confirmPasswordError.setVisible(false));
        roleComboBox.valueProperty().addListener((obs, old, newVal) -> roleError.setVisible(false));
        termsCheckBox.selectedProperty().addListener((obs, old, newVal) -> termsError.setVisible(false));
        addressField.textProperty().addListener((obs, old, newVal) -> addressError.setVisible(false));
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        boolean isValid = validateFields();

        if (isValid) {
            // Create new user
            User newUser = new User(emailField.getText(), passwordField.getText(), roleComboBox.getValue(), addressField.getText());

            // TODO: Save user to database
            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Registration successful!");
            alert.showAndWait();

            // Switch to login screen
            switchToLogin(event);
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        // Email validation
        if (!emailField.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            emailError.setText("Please enter a valid email address");
            emailError.setVisible(true);
            isValid = false;
        }

        // Password validation
        if (passwordField.getText().length() < 6) {
            passwordError.setText("Password must be at least 6 characters");
            passwordError.setVisible(true);
            isValid = false;
        }

        // Confirm password
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            confirmPasswordError.setText("Passwords do not match");
            confirmPasswordError.setVisible(true);
            isValid = false;
        }

        // Role validation
        if (roleComboBox.getValue() == null) {
            roleError.setText("Please select a role");
            roleError.setVisible(true);
            isValid = false;
        }

        // Terms agreement
        if (!termsCheckBox.isSelected()) {
            termsError.setText("Please agree to the terms and conditions");
            termsError.setVisible(true);
            isValid = false;
        }
        if (addressField.getText().trim().isEmpty()) {
            addressError.setText("Please enter your address");
            addressError.setVisible(true);
            isValid = false;
        }

        return isValid;
    }

    @FXML
    private void switchToLogin(ActionEvent event) {
        try {
            App.setRoot("login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
