package edu.badpals.magictg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1056, 716);
        stage.setTitle("¡¡Magic The Gathering Api!!");
        stage.setScene(scene);
        stage.setMinWidth(1056);
        stage.setMinHeight(716);
        stage.centerOnScreen();
        stage.setFullScreenExitHint(""); // Elimina el mensaje de salida
        stage.setFullScreen(true); // Cambiar a pantalla completa automáticamente
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
