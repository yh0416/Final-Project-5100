/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject5100.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kiara
 */
public class DeliveryService {
    public List<OrderItemModel> getOrderItems(int orderId) throws SQLException{
        List<OrderItemModel> items = new ArrayList<>();
        dbUtils db = dbUtils.getInstance();
        String query = "SELECT productName,quantity FROM order_items WHERE orderId =?";
        ResultSet rs = db.fetch(query, orderId);
        
        while(rs.next()){
            items.add(new OrderItemModel(
                   rs.getString("productName"),
                   rs.getInt("quantity")
            ));
        }
        return items;
    }
    
    public void updateDeliveryDetails(String status, int expectedDeliveryTime, String deliveryResponse, int orderId)throws SQLException{
        String query = "UPDATE orders SET deliveryStatus=?, deliveryResponse =?, expectedDeliveryTime=? WHERE id=?";
        dbUtils db = dbUtils.getInstance();
        int rowsAffected = db.save(query, status,deliveryResponse,expectedDeliveryTime,orderId);
        if(rowsAffected == 0){
            throw new SQLException("failed to update delivery details");
        }
    }
    
    public void updateDeclineDelivery(String status,String deliveryResponse, int orderId)throws SQLException{
        String query = "UPDATE orders SET deliveryStatus=?, deliveryResponse =? WHERE id=?";
        dbUtils db = dbUtils.getInstance();
        int rowsAffected = db.save(query, status,deliveryResponse,orderId);
        if(rowsAffected == 0){
            throw new SQLException("failed to update delivery decline details");
        } 
    }
    
    public DeliveryModel getFullDeliveryDetails(int deliveryId) throws SQLException {
        String query = "SELECT * FROM orders WHERE id = ?";
        ResultSet rs = dbUtils.getInstance().fetch(query, deliveryId);

        if (rs.next()) {
            return new DeliveryModel(
                rs.getInt("id"),
                rs.getString("customerAddress"),
                rs.getString("storeAddress"),
                rs.getString("deliveryStatus"),
                rs.getDouble("deliveryFee"),
                rs.getTimestamp("createdAt"),
                rs.getString("customerName"),
                rs.getString("customerPhone"),
                rs.getString("assignedTo"),
                rs.getString("deliveryResponse"),
                rs.getString("deliveryNotes"),
                rs.getString("expectedDeliveryTime")
            );
        }

        return null;
    } 
    
//    timeline logic
    public List<DeliveryTimelineModel> getTimelineEntries(int deliveryId) throws SQLException{
        String query = "SELECT * FROM delivery_timeline WHERE deliveryId =? ORDER BY timestamp ASC";
        ResultSet rs = dbUtils.getInstance().fetch(query, deliveryId);
        List<DeliveryTimelineModel> timeline = new ArrayList<>();
        while(rs.next()){
            timeline.add(new DeliveryTimelineModel(
                    rs.getString("status"),
                    rs.getTimestamp("timestamp"),
                    rs.getString("notes")
            ));
        }
        return timeline;
    }
    
    public void addTimelineEntry(int deliveryId, String status, String notes) throws SQLException{
        String query = "INSERT INTO delivery_timeline (deliveryId, status, notes) VALUES (?,?,?)";
        dbUtils.getInstance().save(query, deliveryId, status, notes);
    }

}
