package com.example.automatedtextprocessing;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.example.automatedtextprocessing.FileProcessing.ReadAndWrite;
import com.example.automatedtextprocessing.RegexProcessing.RegexTextProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label frequency;

    @FXML
    private Button match;

    @FXML
    private TextField regexPattern;

    @FXML
    private Button replace;

    @FXML
    private TextField replacementText;

    @FXML
    private Button reset;

    @FXML
    private Button search;

    @FXML
    private Label statusLabel;

    @FXML
    private TextArea updatedFileContent;

    @FXML
    private Button uploadFile;

    @FXML
    private TextArea uploadedFileContent;

    //Declare File
    private File selectedFile;

    //logger
    private static final Logger logger = Logger.getLogger(HelloController.class.getName());


    @FXML
    void HandleFileContentReset(ActionEvent event) {
        //clear textarea
        uploadedFileContent.setText("");
        updatedFileContent.setText("");
    }

    @FXML
    void handleFileUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();//new file chooser
        fileChooser.setTitle("Select a file to process."); //Sets title of window
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text files","*.txt","*.docx")
        );

        // Get the current window (Stage) from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);//accept file

        selectedFile = file; //set selected variable to current file

        if(file != null){
            //Read file
            try{
                List<String> lines = ReadAndWrite.readFile(file.getPath()); //Read file

                String content = String.join("\n",lines);
                uploadedFileContent.setText(content);
            }catch (IOException e){
                logger.info("File Error: "+ e.getMessage());
                showAlert("File Error","Selected file is corrupt.", Alert.AlertType.ERROR);
            }

        }

    }

    @FXML
    void handleFindMatch(ActionEvent event) {
        if(selectedFile == null){
            showAlert("Error","Please select a file to proceed.", Alert.AlertType.ERROR);
            return;
        }

        String regex = regexPattern.getText();
        if(!regex.isBlank()){

        }
    }

    @FXML
    void handleReplace(ActionEvent event) {
        String pattern = regexPattern.getText(); // Regex pattern
        String replacement = replacementText.getText();  //replacement text

        if(pattern.isBlank()){
            showAlert("Error","Pattern cannot be empty.",Alert.AlertType.WARNING);
            return;
        }

        //replace pattern with replacement text

    }

    @FXML
    void handleSearch(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert frequency != null : "fx:id=\"frequency\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert match != null : "fx:id=\"match\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert regexPattern != null : "fx:id=\"regexPattern\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert replace != null : "fx:id=\"replace\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert replacementText != null : "fx:id=\"replacementText\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert reset != null : "fx:id=\"reset\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert updatedFileContent != null : "fx:id=\"updatedFileContent\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert uploadFile != null : "fx:id=\"uploadFile\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert uploadedFileContent != null : "fx:id=\"uploadedFileContent\" was not injected: check your FXML file 'hello-view.fxml'.";

    }

    //Alert
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
