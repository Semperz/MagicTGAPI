package edu.badpals.starwarsapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.badpals.starwarsapi.model.people.People;
import edu.badpals.starwarsapi.model.people.PeopleResponse;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class StarWarsController implements Initializable {

    private final String characterURL = "https://swapi.dev/api/people/?search=";

    @FXML
    private Button btnBuscar;

    @FXML
    private TextField name;

    @FXML
    private Label gender;

    @FXML
    private Label mass;

    @FXML
    private Label height;

    @FXML
    private Label hairColor;

    @FXML
    private Label eyeColor;

    @FXML
    private Button btnPeople;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void setName(ActionEvent event) {
        try {
            String nameInput = URLEncoder.encode(name.getText(), StandardCharsets.UTF_8.toString());
            URL jsonURL = new URL(characterURL + nameInput);
            /*URL jsonURL = new URL("https://swapi.dev/api/people/?search=Luke%20Skywalker");*/
            HttpURLConnection connection = (HttpURLConnection) jsonURL.openConnection();
            connection.setRequestMethod("GET");
                ObjectMapper objectMapper = new ObjectMapper();
                People response = objectMapper.readValue(connection.getInputStream(), People.class);
                response.getResults().stream().forEach(System.out::println);
                PeopleResponse fisrstPeople = (PeopleResponse) response.getResults().get(0);
                gender.setText(String.valueOf(fisrstPeople.getGender()));
                height.setText(String.valueOf(fisrstPeople.getHeight()));
                mass.setText(String.valueOf(fisrstPeople.getMass()));
                hairColor.setText(String.valueOf(fisrstPeople.getHairColor()));
                eyeColor.setText(String.valueOf(fisrstPeople.getEyeColor()));


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
