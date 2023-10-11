module com.example.javaendassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javaendassignment to javafx.fxml;
    exports com.example.javaendassignment;
}