package edu.badpals.magictg.controller;

import edu.badpals.magictg.services.DataExporter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class secondWindowController implements Initializable {

    private static String apiData;
    @FXML
    private Button btnVolver;
    @FXML
    private TextField fileNameField;
    @FXML
    private ComboBox<String> formatBox;


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

    @FXML
    private void handleSaveAction() {
        String fileName = fileNameField.getText();  // Obtener el nombre del archivo
        String format = formatBox.getValue();       // Obtener el formato seleccionado

        if (fileName != null && !fileName.isEmpty() && format != null && apiData != null) {
            // Llamar a FileExporter para guardar los datos en el formato seleccionado
            DataExporter.exportData(fileName, format, apiData);
        } else {
            throw new RuntimeException();
        }
    }

    public static void setApiData(String data) {
        apiData = data;
    }

}
