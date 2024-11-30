/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject5100.controllers;

import com.mycompany.finalproject5100.models.Delivery;
import com.mycompany.finalproject5100.models.dbUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

/**
 *
 * @author kiara
 */
public class DeliveriesController {
    
    @FXML private TableView<Delivery> deliveriesTable;
    @FXML private TableColumn<Delivery, Integer> orderIdColumn;
    @FXML private TableColumn<Delivery, String> pickupLocationColumn;
    @FXML private TableColumn<Delivery, String> deliveryAddressColumn;
    @FXML private TableColumn<Delivery, String> deliveryStatusColumn;
    @FXML private TableColumn<Delivery, Double> deliveryFeeColumn;
    @FXML private TableColumn<Delivery, Double> createdAtColumn;
    @FXML private ComboBox<String> filterByStatusDropdown;
    @FXML private TextField searchTextField;

    private ObservableList<Delivery> deliveries = FXCollections.observableArrayList();
    
    @FXML 
    public void initialize(){
        try{
            //enable sorting for columns- javafx does this automatically
            deliveryAddressColumn.setSortable(true);
            pickupLocationColumn.setSortable(true);
            deliveryStatusColumn.setSortable(true);
            
            //populate table data
            populateDeliveries();

        }catch(SQLException e){
            e.printStackTrace();
        }
        
        //search, sort, filter, download
        filterByStatusDropdown.getItems().addAll("Pending","Accepted","Delivered","Dispatched");
        filterByStatusDropdown.setOnAction(e->applyFilter());
        searchTextField.textProperty().addListener((observable,oldValue,newValue)->applySearch(newValue));
    }
    //customerAddress, String pickupLocation, String deliveryStatus, double feeEarned, Date createdAt
    private void populateDeliveries() throws SQLException{
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        pickupLocationColumn.setCellValueFactory(new PropertyValueFactory<>("pickupLocation"));
        deliveryAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        deliveryStatusColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));
        deliveryFeeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryFee"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        
        List<Delivery> latestDeliveries = getDeliveries();
        deliveriesTable.getItems().setAll(latestDeliveries);
    }

    //get latest deliveries
    public List<Delivery> getDeliveries() throws SQLException{
       dbUtils db = dbUtils.getInstance(); 
       String query = "SELECT * FROM orders";
       ResultSet rs = db.fetch(query);
       
       List<Delivery> deliveries = new ArrayList<>();
       while(rs.next()){
           deliveries.add(new Delivery(
               rs.getInt("id"),
               rs.getString("pickupLocation"),
               rs.getString("customerAddress"),
               rs.getString("deliveryStatus"),
               rs.getDouble("deliveryFee"),
               rs.getTimestamp("createdAt")
           ));
       }
       return deliveries;
    }
    
   private void applyFilter(){
       String selectedStatus = filterByStatusDropdown.getValue();
       if(selectedStatus !=null){
            ObservableList<Delivery> filteredDeliveries = FXCollections.observableArrayList();
            for(Delivery delivery: deliveries){
                if(delivery.getStatus().equals(delivery)){
                    filteredDeliveries.add(delivery);
                }
            } 
            deliveriesTable.setItems(filteredDeliveries);
       }else{
           deliveriesTable.setItems(deliveries);
       }
  
   }
   
   private void applySearch(String query){
       ObservableList<Delivery> filteredDeliveries = FXCollections.observableArrayList();
            for(Delivery delivery: deliveries){
                if(delivery.getCustomerAddress().toLowerCase().contains(query.toLowerCase())||
                    delivery.getPickupLocation().toLowerCase().contains(query.toLowerCase())||
                    delivery.getStatus().toLowerCase().contains(query.toLowerCase())){
                    filteredDeliveries.add(delivery);
                }
            } 
            deliveriesTable.setItems(filteredDeliveries);
   }
   

   
//   download report
   @FXML
   public void downloadDeliveries(){
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Save Deliveries Report");
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*excel"));
       File file = fileChooser.showSaveDialog(null);
       
       if(file != null){
           try(BufferedWriter   writer = new BufferedWriter(new FileWriter(file))){
               // Write excel headers
            writer.write("Delivery ID,Customer Name, Customer Phone Number,Pickup Location,Delivery Address,Status,Fee,Assigned To,Delivery Response,Date Requested");
            writer.newLine();  
            
            //write data rows
            for(Delivery delivery: deliveries){
                writer.write(
                    delivery.getId() + "," +
                    delivery.getPickupLocation() + "," +
                    delivery.getCustomerAddress()+ "," +
                    delivery.getStatus() + "," +
                    delivery.getFeeEarned() + "," +
                    delivery.getCreatedAt()
                );
                writer.newLine();
            }
               System.out.println("Deliveries downloaded successfully");
             
           }catch(IOException e){
              e.printStackTrace();
               System.out.println("Failed to download report");
           }
       }
   }
}
