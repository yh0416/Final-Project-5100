package com.mycompany.finalproject5100.controllers;

import com.mycompany.finalproject5100.App;
import com.mycompany.finalproject5100.models.Product;
import com.mycompany.finalproject5100.models.dbUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsController {

    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private TableColumn<Product, Integer> productStockColumn;
    @FXML private Button productsButton; // Button for dynamic styling

    private ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private void handleBack() {
        try {
            App.setRoot("store-dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error navigating back to the dashboard.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAddProduct() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Add Product functionality not implemented yet!");
        alert.showAndWait();
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                dbUtils db = dbUtils.getInstance();
                String query = "DELETE FROM products WHERE id = ?";
                db.delete(query, selectedProduct.getId());

                products.remove(selectedProduct);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error deleting product!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a product to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleExportProducts() {
        if (products.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No products to export.");
            alert.showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Products Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Products");

                Row headerRow = sheet.createRow(0);
                String[] headers = {"Product ID", "Name", "Price", "Stock"};
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }

                int rowIndex = 1;
                for (Product product : products) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(product.getId());
                    row.createCell(1).setCellValue(product.getName());
                    row.createCell(2).setCellValue(product.getPrice());
                    row.createCell(3).setCellValue(product.getStock());
                }

                try (FileOutputStream fos = new FileOutputStream(file)) {
                    workbook.write(fos);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Products exported successfully!");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error exporting products!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void initialize() {
        // Bind columns to Product model properties
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        productStockColumn.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());

        // Load products into the table
        loadProducts();

        // Set active button color
        if (productsButton != null) {
            productsButton.getStyleClass().add("button-active");
        }
    }

    private void loadProducts() {
        try {
            dbUtils db = dbUtils.getInstance();
            String query = "SELECT * FROM products";
            ResultSet rs = db.fetch(query);

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("productName"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }

            productsTable.setItems(products);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
