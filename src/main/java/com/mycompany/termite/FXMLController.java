package com.mycompany.termite;

import com.mycompany.termite.model.SimpleSerialPort;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FXMLController implements Initializable {
    
    private SimpleSerialPort serialPort;
    
    @FXML
    private TextField inputTextField;

    @FXML
    private TextArea messageLog;
    
    @FXML
    private ComboBox<String> portSelector;
    
    @FXML
    private Button clearButton;
    
    @FXML
    private Button sendButton;
    
    @FXML
    void sendMessage(ActionEvent event) {
        try {
            String newMessage = inputTextField.getText() + "\n";
            inputTextField.setText("");
            serialPort.write(newMessage);
            appendToLog(newMessage);
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void clearMessageLog(ActionEvent event) {
        messageLog.setText("");
    }
    
    @FXML
    void onPortSelected(ActionEvent event) {
        try {
            String portName = portSelector.getValue();
            serialPort = new SimpleSerialPort(portName);
            serialPort.subscribe((Character data) -> {
                Platform.runLater(()->{
                    messageLog.appendText(data.toString());
                });     
            });
            addOneExitListener();
            setDisabled(false);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void addOneExitListener(){
        messageLog.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(() -> {
                    serialPort.close();
                });
            }
        });
    }

    void appendToLog(String newLine){
        messageLog.appendText(newLine);
    }
    
    void setDisabled(boolean state){
        inputTextField.setDisable(state);
        clearButton.setDisable(state);
        sendButton.setDisable(state);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] ports = SimpleSerialPort.getPorts();
        ObservableList<String> options = FXCollections.observableArrayList(ports);
        portSelector.setItems(options);
        setDisabled(true);
    }    
}
