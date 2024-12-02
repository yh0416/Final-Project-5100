/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject5100.controllers;

import com.mycompany.finalproject5100.App;
import java.io.IOException;
import javafx.fxml.FXML;

/**
 *
 * @author kiara
 */
public class DeliverySidebarController {
    
    @FXML
    private void handleLogout() throws Exception {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
    
     @FXML
    private void navigateToDeliveries() throws Exception {
        try {
            App.setRoot("deliveries");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void navigateToDashboard() throws Exception {
        try {
            System.out.println("Navigating to Dashboard");
            App.setRoot("delivery-dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
