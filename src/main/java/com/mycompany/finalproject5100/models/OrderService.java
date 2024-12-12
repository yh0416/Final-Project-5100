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
public class OrderService {
    private final dbUtils db;

    public OrderService() throws SQLException {
        this.db = dbUtils.getInstance();
    }

    // Save order and return generated order ID
    public int saveOrder(OrderModel order) throws SQLException {
        String query = "INSERT INTO orders (customerName, customerPhone, customerAddress, deliveryFee, storeAddress, " +
                       "deliveryStatus, createdBy, deliveryNotes, expectedDeliveryTime) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        return db.saveAndGetId(query, 
            order.getCustomerName(),
            order.getCustomerPhone(),
            order.getCustomerAddress(),
            order.getDeliveryFee(),
            order.getStoreAddress(),
            "Pending", // Default delivery status
            order.getCreatedBy(),
            order.getDeliveryNotes(),
            order.getExpectedDeliveryTime()
        );
    }

    // Save an order item
    public void saveOrderItem(int orderId, String productName, int quantity) throws SQLException {
        String query = "INSERT INTO order_items (orderId, productName, quantity) VALUES (?, ?, ?)";
        db.save(query, orderId, productName, quantity);
    }

    // Fetch all orders for display
    public List<OrderModel> getAllOrders() throws SQLException {
        String query = "SELECT * FROM orders";
        ResultSet rs = db.fetch(query);

        List<OrderModel> orders = new ArrayList<>();
        while (rs.next()) {
            OrderModel order = new OrderModel(
                rs.getInt("id"),
                rs.getString("customerName"),
                rs.getString("customerPhone"),
                rs.getString("customerAddress"),
                rs.getDouble("deliveryFee"),
                rs.getString("deliveryNotes"),
                rs.getString("expectedDeliveryTime"),
                rs.getString("storeAddress"),
                rs.getString("createdBy"),
                rs.getString("deliveryStatus"),
                rs.getDate("createdAt")
            );
            orders.add(order);
        }
        return orders;
    }
}
