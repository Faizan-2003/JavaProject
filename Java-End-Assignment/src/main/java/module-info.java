module com.example.javaendassignment {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.example.javaendassignment.Controllers;
    opens com.example.javaendassignment to javafx.fxml;
    exports com.example.javaendassignment;
    opens com.example.javaendassignment.Controllers;
    opens com.example.javaendassignment.Model to javafx.base;
    // Other module declarations
}