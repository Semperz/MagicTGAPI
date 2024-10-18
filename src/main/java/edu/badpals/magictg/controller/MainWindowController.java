package edu.badpals.magictg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.badpals.magictg.model.Cards;
import edu.badpals.magictg.model.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private final String characterURL = "https://api.magicthegathering.io/v1/cards?name=";

    @FXML
    private Button btnBuscar;

    @FXML
    private TextField search;

    @FXML
    private Label manaCost;

    @FXML
    private Label colors;

    @FXML
    private Label type;

    @FXML
    private Label power;

    @FXML
    private Label toughtness;

    @FXML
    private Button btnPeople;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void setNameCard(ActionEvent event) {
        try {
            String nameInput = URLEncoder.encode(search.getText(), StandardCharsets.UTF_8.toString());
            URL jsonURL = new URL(characterURL + nameInput);
            HttpURLConnection connection = (HttpURLConnection) jsonURL.openConnection();
            connection.setRequestMethod("GET");
                ObjectMapper objectMapper = new ObjectMapper();
                Response response = objectMapper.readValue(connection.getInputStream(), Response.class);
                Cards card =  response.getCards().get(0);
                manaCost.setText(String.valueOf(card.getManaCost()));
                colors.setText(String.valueOf(card.getColors()));
                type.setText(String.valueOf(card.getType()));
                power.setText(String.valueOf(card.getPower()));
                toughtness.setText(String.valueOf(card.getToughness()));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void toPeopleList(ActionEvent event) {

        try {
            // Cargar la nueva vista desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listPeople.fxml"));
            Parent root = loader.load();

            // Obtener la ventana actual (stage) y cambiar la escena
            Stage stage = (Stage) btnPeople.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
