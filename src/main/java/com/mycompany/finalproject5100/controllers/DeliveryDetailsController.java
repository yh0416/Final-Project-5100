/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject5100.controllers;

import com.mycompany.finalproject5100.App;
import com.mycompany.finalproject5100.models.DeliveryModel;
import com.mycompany.finalproject5100.models.DeliveryService;
import com.mycompany.finalproject5100.models.DeliveryTimelineModel;
import com.mycompany.finalproject5100.models.OrderItemModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    
//    timeline fields
    @FXML private ComboBox<String> statusDropdown;
    @FXML private TextArea statusNotes;
    @FXML TableView<DeliveryTimelineModel> timelineTable;
    @FXML TableColumn<DeliveryTimelineModel, String> statusColumn;
    @FXML TableColumn<DeliveryTimelineModel, String> timestampColumn;
    @FXML TableColumn<DeliveryTimelineModel, String> notesColumn;

    private DeliveryModel delivery;

    //delivery details logic
    
    public void setDelivery(DeliveryModel delivery){
        this.delivery = delivery;
        populateDeliveryDetails();
        populateOderItems();
        populateTimeline(delivery.getId());
    }
    
     @FXML 
    public void initialize(){
        statusDropdown.getItems().addAll("Dispatched","Delivered","Declined");
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
                    showSuccess("Delivery Accepted","Delivery has been accepted successfully");
                   
                }catch(NumberFormatException e){
                    showError("Invalid Input","Expected delivery time must be a number");
                }catch(Exception e){
                    showError("Error","An error occured while accepting the deliver");
                }
                refreshPage();
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
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
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
                    showSuccess("Delivery Declined","Delivery has been declined successfully");
                       
                }catch(Exception e){
                    showError("Error","An error occured while declining the deliver");
                }
                refreshPage();
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
    
    private void showSuccess(String title, String message){
         //show success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle(title);
        successAlert.setHeaderText(null);
        successAlert.setContentText(message);
        successAlert.showAndWait();
    }
    @FXML
    private void refreshPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/finalproject5100/delivery-details.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) deliveryIdLabel.getScene().getWindow();
            stage.setScene(new Scene(root));

            // Pass the updated delivery data to the controller
            DeliveryDetailsController controller = loader.getController();
            controller.setDelivery(delivery); // Assuming 'delivery' is the updated model
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//    timeline logic
    private void populateTimeline(int deliveryId){
        try{
            DeliveryService ds = new DeliveryService();
            List<DeliveryTimelineModel> timelineEntries = ds.getTimelineEntries(deliveryId);
            //bind data to columns
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
            notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
            
            timelineTable.setItems(FXCollections.observableArrayList(timelineEntries));

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    @FXML 
    private void updateDeliveryStatus() throws SQLException{
        String selectedStatus = statusDropdown.getValue();
        String notes = statusNotes.getText();
        
        if(selectedStatus == null || selectedStatus.isEmpty()){
            showError("Error", "Please select a status to update");
            return;
        }
        try{
            
            DeliveryService ds = new DeliveryService();
            ds.addTimelineEntry(delivery.getId(), selectedStatus, notes);
            //refresh the timeline table
            populateTimeline(delivery.getId());
            statusNotes.clear();
            showSuccess("Success","Delivery status updated successfully");
            
        }catch(SQLException e){
            showError("Error", "Failed to update status");
            e.printStackTrace();
        }
    }


}
