package com.mycompany.finalproject5100.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderModel {
    private int id;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private double deliveryFee;
    private String deliveryNotes;
    private String expectedDeliveryTime;
    private String storeAddress;
    private String createdBy;
    private Date createdAt;
    private String deliveryStatus;
    private List<OrderItemModel> orderItems;

     public OrderModel(int id,String customerName, String customerPhone, String customerAddress, 
                      double deliveryFee, String deliveryNotes, String expectedDeliveryTime, 
                      String storeAddress, String createdBy, String deliveryStatus, Date createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.deliveryFee = deliveryFee;
        this.deliveryNotes = deliveryNotes;
        this.expectedDeliveryTime = expectedDeliveryTime;
        this.storeAddress = storeAddress;
        this.createdBy = createdBy;
        this.deliveryStatus = deliveryStatus;
        this.createdAt = createdAt;
        this.orderItems = new ArrayList<>();
    }
     
     
    // Constructor for creating a new order
    public OrderModel(int id, String customerName, String customerPhone, String customerAddress,
                      double deliveryFee, String deliveryNotes, String expectedDeliveryTime,
                      String storeAddress, String createdBy, String deliveryStatus) {
        this.id = id;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.deliveryFee = deliveryFee;
        this.deliveryNotes = deliveryNotes;
        this.expectedDeliveryTime = expectedDeliveryTime;
        this.storeAddress = storeAddress;
        this.createdBy = createdBy;
        this.deliveryStatus = deliveryStatus;
        this.orderItems = new ArrayList<>();
    }
    
     public OrderModel(int id, String customerName, String deliveryStatus, Date createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.deliveryStatus = deliveryStatus;
        this.createdAt = createdAt;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
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

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getDeliveryNotes() {
        return deliveryNotes;
    }

    public void setDeliveryNotes(String deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
    }

    public String getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(String expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemModel> orderItems) {
        this.orderItems = orderItems;
    }

   
}

