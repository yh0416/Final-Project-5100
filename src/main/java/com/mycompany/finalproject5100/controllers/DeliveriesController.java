/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject5100.controllers;

import com.mycompany.finalproject5100.App;
import com.mycompany.finalproject5100.models.DeliveryModel;
import com.mycompany.finalproject5100.models.DeliveryService;
import com.mycompany.finalproject5100.models.dbUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author kiara
 */
public class DeliveriesController {
    
    @FXML private TableView<DeliveryModel> deliveriesTable;
    @FXML private TableColumn<DeliveryModel, Integer> orderIdColumn;
    @FXML private TableColumn<DeliveryModel, String> storeAddressColumn;
    @FXML private TableColumn<DeliveryModel, String> deliveryAddressColumn;
    @FXML private TableColumn<DeliveryModel, String> deliveryStatusColumn;
    @FXML private TableColumn<DeliveryModel, Double> deliveryFeeColumn;
    @FXML private TableColumn<DeliveryModel, Double> createdAtColumn;
    @FXML private ComboBox<String> filterByStatusDropdown;
    @FXML private TextField searchTextField;

    private ObservableList<DeliveryModel> deliveries = FXCollections.observableArrayList();
   
    @FXML 
    public void initialize(){
        try{
            //enable sorting for columns- javafx does this automatically
            deliveryAddressColumn.setSortable(true);
            storeAddressColumn.setSortable(true);
            deliveryStatusColumn.setSortable(true);
            
            //populate table data
            populateDeliveries();
            
            //when user clicks on a row, navigate to the delivery details page
            deliveriesTable.setRowFactory(tv->{
                TableRow<DeliveryModel> row = new TableRow();
                row.setOnMouseClicked(event->{
                    if(event.getClickCount()== 1 && (!row.isEmpty())){
                        DeliveryModel selectedDelivery = row.getItem();
                        try {
                            showDeliveryDetails(selectedDelivery);
                        } catch (SQLException ex) {
                            Logger.getLogger(DeliveriesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                return row;
            });

        }catch(SQLException e){
            e.printStackTrace();
        }
        
        //search, sort, filter, download
        filterByStatusDropdown.getItems().addAll("All","Pending","Accepted","Delivered","Dispatched");
        filterByStatusDropdown.setOnAction(e->applyFilters());
        searchTextField.textProperty().addListener((observable,oldValue,newValue)->applyFilters());
    }
    //customerAddress, String pickupLocation, String deliveryStatus, double feeEarned, Date createdAt
    private void populateDeliveries() throws SQLException{
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        storeAddressColumn.setCellValueFactory(new PropertyValueFactory<>("storeAddress"));
        deliveryAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        deliveryStatusColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));
        deliveryFeeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryFee"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        
        List<DeliveryModel> allDeliveries = getDeliveries();
        //populate the observable list
        deliveries.setAll(allDeliveries);
        //set the table items
        deliveriesTable.getItems().setAll(allDeliveries);
    }

    //get all deliveries
    public List<DeliveryModel> getDeliveries() throws SQLException{
       dbUtils db = dbUtils.getInstance(); 
       String query = "SELECT * FROM orders";
       ResultSet rs = db.fetch(query);
       
       List<DeliveryModel> deliveries = new ArrayList<>();
       while(rs.next()){
           deliveries.add(new DeliveryModel(
               rs.getInt("id"),
               rs.getString("storeAddress"),
               rs.getString("customerAddress"),
               rs.getString("deliveryStatus"),
               rs.getDouble("deliveryFee"),
               rs.getTimestamp("createdAt")
           ));
       }
       return deliveries;
    }
    
   private void applyFilters(){
       String selectedStatus = filterByStatusDropdown.getValue();
       String searchQuery = searchTextField.getText().toLowerCase();
      
        ObservableList<DeliveryModel> filteredDeliveries = FXCollections.observableArrayList();
            for(DeliveryModel delivery: deliveries){
                boolean matchesStatus = (selectedStatus== null || selectedStatus.equals("All")
                || delivery.getDeliveryStatus().equals(selectedStatus));
                boolean matchesSearch =(searchQuery.isEmpty() ||
                    delivery.getCustomerAddress().toLowerCase().contains(searchQuery.toLowerCase())||
                    delivery.getStoreAddress().toLowerCase().contains(searchQuery.toLowerCase())||
                    delivery.getDeliveryStatus().toLowerCase().contains(searchQuery.toLowerCase()));
                if(matchesStatus && matchesSearch){
                    filteredDeliveries.add(delivery);
                }
            } 
            deliveriesTable.setItems(filteredDeliveries);
       }
  
   

   
//   download report
   @FXML
   public void downloadDeliveries(){
       if(deliveries.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Data");
            alert.setHeaderText(null);
            alert.setContentText("No deliveries to download.");
            alert.showAndWait();
            return;
       }
       //open dialog to let user pick where to save file
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Save Deliveries Report");
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*xlsx"));
       File file = fileChooser.showSaveDialog(null);

       if(file != null){
           try(Workbook wb = new XSSFWorkbook()){
               Sheet sheet = wb.createSheet("Deliveries");
               //create header row at row 0
               Row headerRow = sheet.createRow(0);
               String[] headers = {"Delivery ID", "Customer Name", "Phone Number", "Delivery Address","Store Address",  "Status", 
                   "Delivery Fee", "Assigned To", "Delivery Response", "Delivery Notes", "Expected Delivery Time" ,"Date Requested"};
               for(int i=0;i<headers.length;i++){
                   Cell cell = headerRow.createCell(i);
                   cell.setCellValue(headers[i]);
               }
               //write data rows- start at row 1 after header row
               int rowIndex = 1;
               for (DeliveryModel delivery : deliveries){
                   Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(delivery.getId());
                    row.createCell(1).setCellValue(delivery.getCustomerName());
                    row.createCell(2).setCellValue(delivery.getPhoneNumber());
                    row.createCell(3).setCellValue(delivery.getCustomerAddress());
                    row.createCell(4).setCellValue(delivery.getStoreAddress());
                    row.createCell(5).setCellValue(delivery.getDeliveryStatus());
                    row.createCell(6).setCellValue(delivery.getDeliveryFee());
                    row.createCell(7).setCellValue(delivery.getAssignedTo());
                    row.createCell(8).setCellValue(delivery.getDeliveryResponse());
                    row.createCell(9).setCellValue(delivery.getDeliveryNotes());
                    row.createCell(10).setCellValue(delivery.getExpectedDeliveryTime());
                    row.createCell(11).setCellValue(delivery.getCreatedAt().toString());
               }
               for(int i=0;i<headers.length;i++){
                   sheet.autoSizeColumn(i);
               }
               
               //write to file
               try(FileOutputStream fos = new FileOutputStream(file)){
                   wb.write(fos);
               }
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Download Complete");
                alert.setHeaderText(null);
                alert.setContentText("The deliveries report has been downloaded successfully!");
                alert.showAndWait();
           
             
           }catch(IOException e){
              e.printStackTrace();
              Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to download the report.");
                alert.showAndWait();
                e.printStackTrace();
           }
       }
   }
   
   private void showDeliveryDetails(DeliveryModel delivery) throws SQLException{
       try{
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/finalproject5100/delivery-details.fxml"));
           Parent root = loader.load();
           //fetch full delivery details from the db
           DeliveryService ds = new DeliveryService();
           DeliveryModel fullDeliveryDetails  = ds.getFullDeliveryDetails(delivery.getId());
           System.out.println("Full Delivery Details: " + fullDeliveryDetails);

           //pass delivery data to delivery details controller
           DeliveryDetailsController controller = loader.getController();
           controller.setDelivery(fullDeliveryDetails);
           
           //navigate to delivery details page
           Stage stage = (Stage) deliveriesTable.getScene().getWindow();
           stage.setScene(new Scene(root));
           
       }catch(IOException e){
           e.printStackTrace();
       }
       
   }

}
