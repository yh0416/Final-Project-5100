/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject5100.models;

import java.sql.Timestamp;

/**
 *
 * @author kiara
 */
public class DeliveryTimelineModel {
    private String status;
    private Timestamp timestamp;
    private String notes;

    public DeliveryTimelineModel(String status, Timestamp timestamp, String notes) {
        this.status = status;
        this.timestamp = timestamp;
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    
}
