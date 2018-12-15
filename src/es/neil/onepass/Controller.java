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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Button btn2,btn1;
    @FXML
    TextField tf1;
    @FXML
    PasswordField pf1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn1.setOnAction(this::handleButtonShow);
        btn2.setOnAction(this::handleButtonCopy);
    }

    private void handleButtonShow(ActionEvent event)  {
        try {
            String newPass = PasswordGenerator.computePass(pf1.getText(), tf1.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password!");
            alert.setHeaderText("Look, here's your pass! Don't memorise it.");
            alert.setContentText("Just know your masterpass & service name, your pass is: "+newPass);

            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleButtonCopy(ActionEvent event) {
        try {
            String newPass = PasswordGenerator.computePass(pf1.getText(), tf1.getText());
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
