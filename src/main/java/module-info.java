module com.cgvsu.rasterizationfxapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cgvsu.cgtask2.controller to javafx.fxml;
    exports com.cgvsu.cgtask2;
}