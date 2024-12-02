/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject5100.controllers;

import com.mycompany.finalproject5100.models.DeliveryModel;
import com.mycompany.finalproject5100.models.DeliveryService;
import com.mycompany.finalproject5100.models.OrderItemModel;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

/**
 *
 * @author kiara
 */

public class DeliveryDetailsController {
    @FXML private Label deliveryIdLabel;
   
    @FXML private Label statusLabel;
    @FXML private Label deliveryFeeLabel;
    @FXML private Label deliveryNotesLabel;
    @FXML private TextArea deliveryDetailsTextArea;
    @FXML private TextArea storeAddressTextArea;
    @FXML private TextArea deliveryLocationTextArea;

    private DeliveryModel delivery;

    public void setDelivery(DeliveryModel delivery){
        this.delivery = delivery;
        populateDeliveryDetails();
        populateOderItems();
    }

    //populate labels, text field with delivery details from the database
    private void populateDeliveryDetails(){
        deliveryIdLabel.setText(String.valueOf(delivery.getId()));
        statusLabel.setText(delivery.getDeliveryStatus());
        deliveryFeeLabel.setText(String.valueOf(delivery.getDeliveryFee()));
        deliveryNotesLabel.setText(delivery.getDeliveryNotes());
        deliveryLocationTextArea.setText(
               "Customer Name: "+ delivery.getCustomerName()+ "\n"+
               "Customer Phone Number: " + delivery.getPhoneNumber()+ "\n"+ 
               "Delivery Address: " + delivery.getCustomerAddress());
        storeAddressTextArea.setText(delivery.getStoreAddress());
    }

    //populate order items
    private void populateOderItems(){
        DeliveryService ds = new DeliveryService();
        try{
            List<OrderItemModel> items = ds.getOrderItems(delivery.getId());
            StringBuilder orderDetails = new StringBuilder();
            for(OrderItemModel item : items){
                orderDetails.append(item.toString()).append("\n");
            }
            deliveryDetailsTextArea.setText(orderDetails.toString());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void acceptDelivery(){
        TextInputDialog deliveryResponseDialog = new TextInputDialog();
        deliveryResponseDialog.setTitle("Accept Delivery");
        deliveryResponseDialog.setHeaderText("Provide Expected Delivery Time and Notes");
        
        //create fields for accepting delivery
        TextField expectedTimeField = new TextField();
        expectedTimeField.setPromptText("Expected Delivery Time in days");
        TextArea notesField = new TextArea();
        notesField.setPromptText("Additional Delivery Notes");
        
        VBox content = new VBox(10, 
                new Label("Expected Delivery Time:"),expectedTimeField,
                new Label("Notes:"),notesField);
        
        deliveryResponseDialog.getDialogPane().setContent(content);
        
        //handle response
        deliveryResponseDialog.showAndWait().ifPresent(response->{
            String expectedTime = expectedTimeField.getText();
            String notes = notesField.getText();
            
            if(!expectedTime.isEmpty()){
                try {
                    DeliveryService ds = new DeliveryService();
                
                    ds.updateDeliveryDetails("Accepted",Integer.parseInt(expectedTime),notes,delivery.getId());
                    
                    //show success message
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Delivery Accepted");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Delivery has been accepted successfully");
                    successAlert.showAndWait();
                }catch(NumberFormatException e){
                    showError("Invalid Input","Expected delivery time must be a number");
                }catch(Exception e){
                    showError("Error","An error occured while accepting the deliver");
                }
            }else{
                 showError("Missing Input","Expected time is required");
            }
        });
    }
    
    @FXML
    private void declineDelivery(){
        Dialog<String> declineDialog = new Dialog<>();
        declineDialog.setTitle("Decline Delivery");
        declineDialog.setHeaderText("Provide a Reason for Declining the Delivery");
        
        //create fields for accepting delivery
        TextArea reasonField = new TextArea();
        reasonField.setPromptText("Reason for Declining");           
        
        declineDialog.getDialogPane().setContent(reasonField);
        
        //add cancel and OK buttons
        ButtonType okButtonType = new ButtonType("Decline", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Decline", ButtonBar.ButtonData.CANCEL_CLOSE);
        declineDialog.getDialogPane().getButtonTypes().addAll(okButtonType,cancelButtonType);
        
        //handle response
        declineDialog.setResultConverter(buttonType -> {
        if (buttonType == okButtonType) {
                return reasonField.getText(); 
            }
            return null;
        });
        declineDialog.showAndWait().ifPresent(reason->{
            
            if(reason !=null && !reason.isEmpty()){
                try {
                DeliveryService ds = new DeliveryService();
                
                    ds.updateDeclineDelivery("Declined",reason,delivery.getId());
                    
                    //show success message
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Delivery Declined");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Delivery has been declined successfully");
                    successAlert.showAndWait();
                }catch(Exception e){
                    showError("Error","An error occured while declining the deliver");
                }
            }else{
                 showError("Missing Input","Decline reason is required");
            }
        });
    }
    
    private void showError(String title, String message){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
 
}
