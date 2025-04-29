module com.example.automatedtextprocessing {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.automatedtextprocessing to javafx.fxml;
    exports com.example.automatedtextprocessing;
}