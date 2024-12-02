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
public class DeliveryModel {
    private int id;
    private String customerName;
    private String customerAddress;
    private double deliveryFee;
    private String assignedTo;
    private Date createdAt;
    private String deliveryStatus;
    private String storeAddress;
    
    //additional fields for the report
    private String phoneNumber;
    private String deliveryResponse;
    private String deliveryNotes;
    private String expectedDeliveryTime;

    //constructor for the main dashboard
    public DeliveryModel(int id, String customerName, String customerAddress, double deliveryFee, String deliveryStatus, String assignedTo, Date createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.deliveryFee = deliveryFee;
        this.deliveryStatus = deliveryStatus;
        this.assignedTo = assignedTo;
        this.createdAt = createdAt;
    }
    
    //constructor for the deliveries dashboard
     public DeliveryModel(int id, String storeAddress,String customerAddress, String deliveryStatus, double deliveryFee, Date createdAt) {
        this.id = id;
        this.storeAddress = storeAddress;
        this.customerAddress = customerAddress;
        this.deliveryStatus = deliveryStatus;
        this.deliveryFee = deliveryFee;
        this.createdAt = createdAt;
    }
      //constructor for report
    public DeliveryModel(int id, String customerAddress, String storeAddress, String deliveryStatus, double deliveryFee, Date createdAt, 
            String customerName, String phoneNumber, String assignedTo, String deliveryResponse, String deliveryNotes,String expectedDeliveryTime) {
        this(id, storeAddress, customerAddress, deliveryStatus, deliveryFee, createdAt); 
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.assignedTo = assignedTo;
        this.deliveryResponse = deliveryResponse;
        this.deliveryNotes = deliveryNotes;
        this.expectedDeliveryTime = expectedDeliveryTime;
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

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeliveryResponse() {
        return deliveryResponse;
    }

    public void setDeliveryResponse(String deliveryResponse) {
        this.deliveryResponse = deliveryResponse;
    }

    public String getDeliveryNotes() {
        return deliveryNotes;
    }

    public void setDeliveryNotes(String deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(String expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

   
    
}
