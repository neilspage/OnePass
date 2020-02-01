package es.neil.onepass;

import es.neil.onepass.util.PasswordGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Button btnCopy, btnShow;
    @FXML
    TextField textFieldServiceName;
    @FXML
    PasswordField passwordFieldMasterPass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnShow.setOnAction(this::handleButtonShow);
        btnCopy.setOnAction(this::handleButtonCopy);
    }

    private void handleButtonShow(ActionEvent event)  {
        try {
            String newPass = PasswordGenerator.generatePass(passwordFieldMasterPass.getText(), textFieldServiceName.getText(), 8);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password!");
            alert.setHeaderText("Look, here's your pass! Don't memorise it.");
            alert.setContentText("Just know your master pass & service name, your pass is: " + newPass);

            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleButtonCopy(ActionEvent event) {
        try {
            //TODO: Add feature to allow user to set desiredPasswordLength
            String newPass = PasswordGenerator.generatePass(passwordFieldMasterPass.getText(), textFieldServiceName.getText(), 8);
            StringSelection stringSelection = new StringSelection(newPass);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Copied!");
            alert.setHeaderText("Look, it's copied! Do Control/CMD + V..");
            alert.setContentText("Copied your pass, paste it when you need to!");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
