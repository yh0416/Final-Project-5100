package com.mycompany.finalproject5100.models;

public class Order {
    private String orderId;
    private String customer;
    private String status;
    private double total;

    public Order(String orderId, String customer, String status, double total) {
        this.orderId = orderId;
        this.customer = customer;
        this.status = status;
        this.total = total;
    }

    // Getters and setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}