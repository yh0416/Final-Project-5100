package com.mycompany.finalproject5100.controllers;

import com.mycompany.finalproject5100.App;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import com.mycompany.finalproject5100.models.OrderModel;
import com.mycompany.finalproject5100.models.dbUtils;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StoreDashboardController {

    @FXML
    private Label totalSalesLabel;
    @FXML
    private Label productsCountLabel;
    @FXML
    private Label pendingOrdersLabel;
    @FXML
    private Label completedOrdersLabel;

    @FXML
    private TableView<OrderModel> ordersTable;
    @FXML
    private TableColumn<OrderModel, String> orderIdColumn;
    @FXML
    private TableColumn<OrderModel, String> customerNameColumn;
    @FXML
    private TableColumn<OrderModel, String> deliveryStatusColumn;
    @FXML
    private TableColumn<OrderModel, Double> createdAtColumn;

    @FXML
    public void initialize() {
        try {
            // Initialize table columns
            orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            deliveryStatusColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));
            createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

            // Load data from database
            populateLatestOrders();
            updateDashboardMetrics();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateDashboardMetrics() throws SQLException {
        dbUtils db = dbUtils.getInstance();

        // Get total products count
        String productQuery = "SELECT COUNT(*) as total FROM products";
        ResultSet productRs = db.fetch(productQuery);
        if (productRs.next()) {
            productsCountLabel.setText(String.valueOf(productRs.getInt("total")));
        }

        // Get pending orders count
        String pendingQuery = "SELECT COUNT(*) as total FROM orders WHERE deliveryStatus = 'Pending'";
        ResultSet pendingRs = db.fetch(pendingQuery);
        if (pendingRs.next()) {
            pendingOrdersLabel.setText(String.valueOf(pendingRs.getInt("total")));
        }

        // Get completed orders count
        String completedQuery = "SELECT COUNT(*) as total FROM orders WHERE deliveryStatus = 'Completed'";
        ResultSet completedRs = db.fetch(completedQuery);
        if (completedRs.next()) {
            completedOrdersLabel.setText(String.valueOf(completedRs.getInt("total")));
        }

        // Calculate total sales
        String salesQuery = "SELECT SUM(deliveryFee) as total FROM orders";
        ResultSet salesRs = db.fetch(salesQuery);
        if (salesRs.next()) {
            double totalSales = salesRs.getDouble("total");
        }
    }

    private void populateLatestOrders() throws SQLException {
        dbUtils db = dbUtils.getInstance();

        String query = "SELECT id, customerName, deliveryStatus, createdAt FROM orders ORDER BY createdAt DESC LIMIT 10";
        ResultSet rs = db.fetch(query);

        ObservableList<OrderModel> orders = FXCollections.observableArrayList();
        while (rs.next()) {
            orders.add(new OrderModel(
                rs.getInt("id"),
                rs.getString("customerName"),
                rs.getString("deliveryStatus"),
                rs.getDate("createdAt")
            ));
        }

        ordersTable.setItems(orders);
    }

    @FXML
    private void handleAddProduct() throws Exception {
        try {
            App.setRoot("add_product");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   @FXML
    private void handleCreateOrder() throws Exception {
        try {
            App.setRoot("create-order");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
