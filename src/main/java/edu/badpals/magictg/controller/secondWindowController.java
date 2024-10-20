package edu.badpals.magictg.controller;

import edu.badpals.magictg.services.DataExporter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveAction() {
        String fileName = fileNameField.getText();
        String format = formatBox.getValue();

        if (fileName != null && !fileName.isEmpty() && format != null && apiData != null) {
            // Llamar a FileExporter para guardar los datos en el formato seleccionado
            DataExporter.exportData(fileName, format, apiData);
            exportAlert(Alert.AlertType.INFORMATION, "Exportación exitosa", "Se ha exportado correctamente en el formato seleccionado");

        } else {
            exportAlert(Alert.AlertType.ERROR, "Exportación fallida", "Faltan datos por cubrir o no seleccionaste una carta");
        }
    }

    private void exportAlert(Alert.AlertType alertType, String title, String mesasge) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(mesasge);
        alert.getButtonTypes().setAll(new ButtonType("Cerrar"));
        alert.showAndWait();
    }


    public static void setApiData(String data) {
        apiData = data;
    }


}
