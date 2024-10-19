package edu.badpals.magictg.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class LoginController {

    @FXML
    private TextField textID;

    @FXML
    private TextField textPassword;

    @FXML
    private Button btnLogIn;

    // Ruta al archivo JSON
    private static final String USERS_FILE = "users.json";

    // Clase para representar a los usuarios
    public static class User {
        public String id;
        public String password;

        // Getters y setters opcionales si los necesitas
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @FXML
    private void tryToLogIN() {
        String inputID = textID.getText();
        String inputPassword = textPassword.getText();

        try {
            // Leer el archivo JSON desde el classpath
            ObjectMapper objectMapper = new ObjectMapper();

            // Utilizar getResourceAsStream para cargar el archivo JSON
            List<User> users = objectMapper.readValue(
                    getClass().getResourceAsStream("/users.json"),
                    new TypeReference<List<User>>() {}
            );

            // Verificar si el ID y contraseña coinciden con algún usuario en la lista
            boolean loginSuccess = users.stream()
                    .anyMatch(user -> user.getId().equals(inputID) && user.getPassword().equals(inputPassword));

            if (loginSuccess) {
                showAlert(Alert.AlertType.INFORMATION, "Login Exitoso", "Bienvenido, " + inputID + "!");
                // Cargar la nueva ventana (mainView)
                loadMainView();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Fallido", "Usuario o contraseña incorrectos.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo leer el archivo de usuarios.");
        }
    }

    // Método para mostrar alertas
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método para cargar la nueva ventana
    private void loadMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) textID.getScene().getWindow();
            stage.close();

            Stage newStage = new Stage();
            newStage.setTitle("Vista Principal");
            newStage.setScene(new Scene(root));
            newStage.centerOnScreen();
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista principal.");
        }
    }

}
