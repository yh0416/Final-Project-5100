module com.mycompany.finalproject5100 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires mysql.connector.j;


    opens com.mycompany.finalproject5100 to javafx.fxml;
    opens com.mycompany.finalproject5100.controllers to javafx.fxml;
    opens com.mycompany.finalproject5100.models to javafx.base;


    
    exports com.mycompany.finalproject5100;
    exports com.mycompany.finalproject5100.controllers;
    exports com.mycompany.finalproject5100.models;

}