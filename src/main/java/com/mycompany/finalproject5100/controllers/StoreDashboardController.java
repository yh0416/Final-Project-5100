package com.mycompany.finalproject5100.controllers;

import com.mycompany.finalproject5100.App;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import com.mycompany.finalproject5100.models.Order;
import com.mycompany.finalproject5100.models.dbUtils;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> orderIdColumn;
    @FXML
    private TableColumn<Order, String> customerColumn;
    @FXML
    private TableColumn<Order, String> statusColumn;
    @FXML
    private TableColumn<Order, Double> totalColumn;

    @FXML
    public void initialize() {
        try {
            // Initialize table columns
            orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

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
        String pendingQuery = "SELECT COUNT(*) as total FROM orders WHERE status = 'Pending'";
        ResultSet pendingRs = db.fetch(pendingQuery);
        if (pendingRs.next()) {
            pendingOrdersLabel.setText(String.valueOf(pendingRs.getInt("total")));
        }

        // Get completed orders count
        String completedQuery = "SELECT COUNT(*) as total FROM orders WHERE status = 'Completed'";
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
        String query = "SELECT * FROM orders ORDER BY createdAt DESC LIMIT 10";
        ResultSet rs = db.fetch(query);

        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            Order order = new Order(
                    "ORD-" + rs.getString("id"),
                    rs.getString("customerName"),
                    rs.getString("status"),
                    rs.getDouble("deliveryFee")
            );
            orders.add(order);
        }

        ordersTable.getItems().setAll(orders);
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
            App.setRoot("create_order");
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
}
