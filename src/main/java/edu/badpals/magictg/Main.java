package edu.badpals.magictg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/logIn.fxml"));
        VBox root = fxmlLoader.load(); // Cargar el VBox desde FXML
        Scene scene = new Scene(root, 369.6, 370.4);
        stage.setTitle("¡¡Magic The Gathering Api!!");
        stage.setScene(scene);
        stage.setMinWidth(369.6);
        stage.setMinHeight(370.4);
        stage.show();
    }


}
