module com.mycompany.finalproject5100 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;

    opens com.mycompany.finalproject5100 to javafx.fxml;
    opens com.mycompany.finalproject5100.controller to javafx.fxml;
    opens com.mycompany.finalproject5100.model to javafx.base;
    
    exports com.mycompany.finalproject5100;
    exports com.mycompany.finalproject5100.controller;
    exports com.mycompany.finalproject5100.model;
}