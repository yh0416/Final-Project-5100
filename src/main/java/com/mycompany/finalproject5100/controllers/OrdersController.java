/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject5100.controllers;

import com.mycompany.finalproject5100.App;
import com.mycompany.finalproject5100.models.OrderItemModel;
import com.mycompany.finalproject5100.models.OrderModel;
import com.mycompany.finalproject5100.models.OrderService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 *
 * @author kiara
 */
public class OrdersController {
      @FXML
    private TextField customerName;

    @FXML
    private TextField customerPhone;

    @FXML
    private TextField customerAddress;

    @FXML
    private TextField deliveryFee;

    @FXML
    private TextField deliveryNotes;

    @FXML
    private TextField expectedDeliveryTime;

    @FXML
    private TextField storeAddress;

    @FXML
    private TextField createdBy;

    @FXML
    private TableView<OrderItemModel> productsTable;

    @FXML
    private TableColumn<OrderItemModel, String> productNameColumn;

    @FXML
    private TableColumn<OrderItemModel, Integer> quantityColumn;

    private ObservableList<OrderModel> orders = FXCollections.observableArrayList();
    private ObservableList<OrderItemModel> orderItems = FXCollections.observableArrayList();
    private final OrderService orderService;

    public OrdersController() throws SQLException {
        this.orderService = new OrderService();
    }
    
    
    @FXML
    public void initialize() {
       productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    // Bind the orderItems list to the productsTable
    productsTable.setItems(orderItems);

        populateOrders();
    }

    private void populateOrders() {
        try {
            orders.setAll(orderService.getAllOrders());
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
private void handleAddOrderProduct() {
    // Show dialog to add a product
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Add Product");
    dialog.setHeaderText("Enter product details");

    // Create fields for product name and quantity
    TextField productNameField = new TextField();
    productNameField.setPromptText("Product Name");

    TextField quantityField = new TextField();
    quantityField.setPromptText("Quantity");

    VBox content = new VBox(10, new Label("Product Name:"), productNameField,
            new Label("Quantity:"), quantityField);
    dialog.getDialogPane().setContent(content);

    // Show the dialog and process input
    dialog.showAndWait().ifPresent(response -> {
        String productName = productNameField.getText();
        String quantityText = quantityField.getText();
        try {
            int quantity = Integer.parseInt(quantityText);
            OrderItemModel item = new OrderItemModel(productName, quantity);
            orderItems.add(item); // Add to the ObservableList
        } catch (NumberFormatException e) {
            showError("Invalid Input", "Quantity must be a number.");
        }
    });
}


    @FXML
private void handleSubmitOrder() {
    try {
        // Create the OrderModel
        OrderModel order = new OrderModel(
            0, // Order ID is auto-generated
            customerName.getText(),
            customerPhone.getText(),
            customerAddress.getText(),
            Double.parseDouble(deliveryFee.getText()),
            deliveryNotes.getText(),
            expectedDeliveryTime.getText(),
            storeAddress.getText(),
            createdBy.getText(),
            null
           
        );

        // Attach the list of order items
        order.setOrderItems(new ArrayList<>(orderItems));

        // Save the order and get the generated order ID
        int orderId = orderService.saveOrder(order);

        // Log the saved order ID
        System.out.println("Order saved with ID: " + orderId);

        // Reset the form and reload orders
        resetForm();
        populateOrders();

        // Show success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Order Created");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Order has been created successfully!");
        successAlert.showAndWait();

    } catch (Exception e) {
        e.printStackTrace();
        showError("Error", "Failed to create the order. Please check your input.");
    }
}


    private void resetForm() {
        customerName.clear();
        customerPhone.clear();
        customerAddress.clear();
        deliveryFee.clear();
        deliveryNotes.clear();
        expectedDeliveryTime.clear();
        storeAddress.clear();
        createdBy.clear();
        orderItems.clear();
    }
      
    private void showError(String title, String message){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
    
    
       @FXML
    private void handleViewProducts() throws Exception {
        try {
            App.setRoot("products");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void handleDashboard() throws Exception {
        try {
            App.setRoot("store-dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
