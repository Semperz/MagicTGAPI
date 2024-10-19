package edu.badpals.magictg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/mainView.fxml"));
        VBox root = fxmlLoader.load(); // Cargar el VBox desde FXML
        Scene scene = new Scene(root, 1056, 716);
        stage.setTitle("¡¡Magic The Gathering Api!!");
        stage.setScene(scene);
        stage.setMinWidth(1056);
        stage.setMinHeight(716);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}