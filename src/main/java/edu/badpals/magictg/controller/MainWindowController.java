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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    private ImageView cardView;

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

            // Filtrar cartas que tienen imagen
            List<Cards> cardsWithImage = response.getCards().stream()
                    .filter(card -> card.getImageUrl() != null && !card.getImageUrl().isEmpty())
                    .collect(Collectors.toList());

            // Verifica si hay cartas disponibles con imagen
            if (!cardsWithImage.isEmpty()) {
                Cards card = cardsWithImage.get(0); // Usar la primera carta que tiene imagen

                // Actualizamos los valores de los campos
                manaCost.setText(String.valueOf(card.getManaCost()));
                colors.setText(String.valueOf(card.getColors()));
                type.setText(String.valueOf(card.getType()));
                power.setText(String.valueOf(card.getPower()));
                toughtness.setText(String.valueOf(card.getToughness()));

                // Aquí obtienes la URL de la imagen desde la API o el objeto `Cards`
                String imageUrl = card.getImageUrl();

                // Verifica si la URL usa http, y cámbiala a https
                if (imageUrl.startsWith("http://")) {
                    imageUrl = imageUrl.replace("http://", "https://");
                }

                // Carga la imagen en el ImageView
                Image image = new Image(imageUrl);
                cardView.setImage(image);
            } else {
                // Manejo del caso en que no haya cartas con imagen
                System.out.println("No hay cartas con imagen disponible.");
            }

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
