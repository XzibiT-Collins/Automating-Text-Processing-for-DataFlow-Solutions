package com.example.automatedtextprocessing;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.example.automatedtextprocessing.FileAnalysis.StreamProcessing;
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
        clearUpload();
        clearOutput();

        //set selected file to null
        selectedFile = null;
    }

    @FXML
    void handleFileUpload(ActionEvent event) {
        //clear bot upload and output textarea
        clearOutput();
        clearUpload();

        //Accept file from GUI
        FileChooser fileChooser = new FileChooser();//new file chooser
        fileChooser.setTitle("Select a file to process."); //Sets title of window
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text files","*.txt","*.docx")
        );

        // Get the current window (Stage) from the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);//accept file

        selectedFile = file; //set selected variable to current file

        //Process file
        if(file != null){
            //Read file
            try{
                List<String> lines = ReadAndWrite.readFile(file.getPath()); //Read file

                String content = String.join("\n",lines);
                uploadedFileContent.setText(content);

                //upload summary of file to textarea
                List<Map.Entry<String,Long>> dict = StreamProcessing.wordSummary(selectedFile.getPath(),10);

                //Stringbuilder to hold summary
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("SUMMARY OF FILE TOP 10\n")
                        .append(String.format("%-15s %10s%n","WORD","COUNT"));

                if(!lines.isEmpty()){
                    for(Map.Entry<String , Long> entry:dict){
                        stringBuilder
                                .append(String.format("%-15s:%10s%n",entry.getKey(),entry.getValue()));

                    }

                    //display summary to user
                    updatedFileContent.setText(stringBuilder.toString());
                }


            }catch (IOException e){
                logger.info("File Error: "+ e.getMessage());
                showAlert("File Error","Selected file is corrupt.", Alert.AlertType.ERROR);
            }

        }

    }

    @FXML
    void handleFindMatch(ActionEvent event) {
        //clear previous text in updated textarea
        clearOutput();

        if(selectedFile == null){
            showAlert("Error","Please select a file to proceed.", Alert.AlertType.ERROR);
            return;
        }

        String regex = regexPattern.getText();
        if(!regex.isBlank()){
            try{
                logger.info("Started pattern matching.");
                List<String> lines = RegexTextProcessor.matchPattern(selectedFile.getPath(),regex);

                if(lines.isEmpty()){
                    showAlert("INFO","No match found.",Alert.AlertType.INFORMATION);
                    return;
                }
                for(String line: lines){
                    logger.info(""+line+"\n");
                }
                String content = String.join("\n", lines);

                //Display matches
                updatedFileContent.setText(content);

                //update match count
                try{
                    long count = StreamProcessing.countPatternOccurrence(selectedFile.getPath(),regex);
                    updateMatchCount(count); //update value
                }catch (IOException a){
                    logger.info("Error: " + a.getMessage());
                }
            }catch (IOException e){
                logger.info("Error matching File: "+ e.getMessage());
                showAlert("Error","Error matching pattern.", Alert.AlertType.ERROR);
            }
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

        if(selectedFile != null){
            try{
                RegexTextProcessor.replaceText(selectedFile.getPath(),replacement,pattern); //Replace text

                //Prompt user
                showAlert("INFO","File updated successfully.", Alert.AlertType.INFORMATION);

                //Display updated file to user
                List<String> lines = ReadAndWrite.readFile(selectedFile.getPath());

                String content = String.join("\n",lines);
                uploadedFileContent.setText(content);

                //clear previous output
                clearOutput();

            }catch (IOException e){
                showAlert("Error","Couldn't replace item.", Alert.AlertType.ERROR);
                logger.info("Error while writing to file: "+ e.getMessage());
            }
        }
    }

    @FXML
    void handleSearch(ActionEvent event) {
        //clear previous text in textarea
        clearOutput();

        String regex = regexPattern.getText();

        if(regex.isBlank()){
            showAlert("Warning", "Pattern cannot be blank.", Alert.AlertType.WARNING);
            return;
        }

        //Search pattern occurrence
        if(selectedFile != null){
            try{
                List<String> lines = RegexTextProcessor.search(selectedFile.getPath(),regex);
                showAlert("INFO", "Results found!", Alert.AlertType.INFORMATION);

                //display results to user
                String content = String.join("\n",lines);
                updatedFileContent.setText(content);

                //Update match count
                try{
                    long count = StreamProcessing.countPatternOccurrence(selectedFile.getPath(),regex);
                    updateMatchCount(count); //update value
                }catch (IOException a){
                    logger.info("Error: " + a.getMessage());
                }

            }catch (IOException e){
                logger.info("An error occurred while searching: "+ e.getMessage());
                showAlert("Error", "An error occurred while searching for pattern.", Alert.AlertType.ERROR);
            }
        }else{
            logger.info("Error: No file selected for search operation");
            showAlert("Error","Please select a file to proceed.", Alert.AlertType.ERROR);
        }
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

    //Update match count
    public void updateMatchCount(long count){
        //set count label
        frequency.setText(String.valueOf(count));
    }

    //Clear output
    public void clearOutput(){
        updatedFileContent.setText("");
    }

    //clear input
    public void clearUpload(){
        uploadedFileContent.setText("");
    }
}
