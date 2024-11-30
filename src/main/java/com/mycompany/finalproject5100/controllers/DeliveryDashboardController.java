/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject5100.controllers;

import com.mycompany.finalproject5100.App;
import com.mycompany.finalproject5100.models.Delivery;
import com.mycompany.finalproject5100.models.dbUtils;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author kiara
 */
public class DeliveryDashboardController {
    @FXML private Label totalDeliveriesLabel;
    @FXML private Label deliveredOrdersLabel;
    @FXML private Label pendingDeliveriesLabel;
    @FXML private TableView<Delivery> latestDeliveriesTable;
    @FXML private TableColumn<Delivery, Integer> orderIdColumn;
    @FXML private TableColumn<Delivery, String> customerNameColumn;
    @FXML private TableColumn<Delivery, String> deliveryAddressColumn;
    @FXML private TableColumn<Delivery, String> statusColumn;
    @FXML private TableColumn<Delivery, Double> feeColumn;
    @FXML private TableColumn<Delivery, Double> assignedToColumn;
    @FXML private TableColumn<Delivery, Double> createdAtColumn;

    
    @FXML 
    public void initialize(){
        try{
            totalDeliveriesLabel.setText(String.valueOf(getTotalDeliveries()));
            deliveredOrdersLabel.setText(String.valueOf(getDeliveredCount()));
            pendingDeliveriesLabel.setText(String.valueOf(getPendingCount()));
            
            //populate table data
            populateLatestDeliveries();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    private void populateLatestDeliveries() throws SQLException{
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        deliveryAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryFee"));
        assignedToColumn.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        
        List<Delivery> latestDeliveries = getLatestDeliveries();
        latestDeliveriesTable.getItems().setAll(latestDeliveries);
    }

    //get latest deliveries
    public List<Delivery> getLatestDeliveries() throws SQLException{
       dbUtils db = dbUtils.getInstance(); 
       String query = "SELECT * FROM orders WHERE deliveryStatus IS NOT NULL ORDER BY createdAt DESC LIMIT 10";
       ResultSet rs = db.fetch(query);
       
       List<Delivery> deliveries = new ArrayList<>();
       while(rs.next()){
           deliveries.add(new Delivery(
               rs.getInt("id"),
               rs.getString("customerName"),
               rs.getString("customerAddress"),
               rs.getDouble("deliveryFee"),
               rs.getString("deliveryStatus"),
               rs.getString("assignedTo"),
               rs.getTimestamp("createdAt")
           ));
       }
       return deliveries;
    }
    
    //calculate card view data
    public int getTotalDeliveries() throws SQLException{
        dbUtils db = dbUtils.getInstance();
        String query = "SELECT COUNT(*) AS total FROM orders";
        ResultSet rs = db.fetch(query);
        return rs.next()?rs.getInt("total"):0;
    }
    
      
    public int getDeliveredCount() throws SQLException{
        dbUtils db = dbUtils.getInstance();
        String query = "SELECT COUNT(*) AS total FROM orders WHERE deliveryStatus ='Delivered'";
        ResultSet rs = db.fetch(query);
        return rs.next()?rs.getInt("total"):0;
    }
    
    public int getPendingCount() throws SQLException{
        dbUtils db = dbUtils.getInstance();
        String query = "SELECT COUNT(*) AS total FROM orders WHERE deliveryStatus ='Pending'";
        ResultSet rs = db.fetch(query);
        return rs.next()?rs.getInt("total"):0;
    }
    
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
            App.setRoot("delivery-dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
