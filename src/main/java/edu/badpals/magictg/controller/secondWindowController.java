package edu.badpals.magictg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class secondWindowController implements Initializable {
    @FXML
    private Button btnVolver;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void toPeopleSearch(ActionEvent event) {

        try {
            // Cargar la nueva vista desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
            Parent root = loader.load();

            // Obtener la ventana actual (stage) y cambiar la escena
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
