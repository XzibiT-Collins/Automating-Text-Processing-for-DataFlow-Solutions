module com.example.automatedtextprocessing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;


    opens com.example.automatedtextprocessing to javafx.fxml;
    exports com.example.automatedtextprocessing;
}