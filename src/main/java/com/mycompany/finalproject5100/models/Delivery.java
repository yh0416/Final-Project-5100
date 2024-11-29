/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject5100.models;

import java.util.Date;

/**
 *
 * @author kiara
 */
public class Delivery {
    private int id;
    private String customerName;
    private String customerAddress;
    private double deliveryFee;
    private String assignedTo;
    private Date createdAt;
    private String deliveryStatus;
    private String pickupLocation;

    public Delivery(int id, String customerName, String customerAddress, double deliveryFee, String deliveryStatus, String assignedTo, Date createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.deliveryFee = deliveryFee;
        this.deliveryStatus = deliveryStatus;
        this.assignedTo = assignedTo;
        this.createdAt = createdAt;
    }
    
     public Delivery(int id, String pickupLocation,String customerAddress, String deliveryStatus, double deliveryFee, Date createdAt) {
        this.id = id;
        this.pickupLocation = pickupLocation;
        this.customerAddress = customerAddress;
        this.deliveryStatus = deliveryStatus;
        this.deliveryFee = deliveryFee;
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return deliveryStatus;
    }

    public void setStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
     

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public double getFeeEarned() {
        return deliveryFee;
    }

    public void setFeeEarned(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

   
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

   
    
}
