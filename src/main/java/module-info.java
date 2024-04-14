module com.example.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.mindfulgamer to javafx.fxml;
    exports com.example.mindfulgamer;
    exports com.example.mindfulgamer.controller;
    opens com.example.mindfulgamer.controller to javafx.fxml;
    exports com.example.mindfulgamer.model;
    opens com.example.mindfulgamer.model to javafx.fxml;
}