module com.example.automatedtextprocessing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.automatedtextprocessing to javafx.fxml;
    exports com.example.automatedtextprocessing;
}