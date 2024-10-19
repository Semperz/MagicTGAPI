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
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {

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
    private Label toughness;

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
            String nameInput = URLEncoder.encode(search.getText(), StandardCharsets.UTF_8);
            String characterURL = "https://api.magicthegathering.io/v1/cards?name=";
            URL jsonURL = new URL(characterURL + nameInput);
            HttpURLConnection connection = (HttpURLConnection) jsonURL.openConnection();
            connection.setRequestMethod("GET");

            ObjectMapper objectMapper = new ObjectMapper();
            Response response = objectMapper.readValue(connection.getInputStream(), Response.class);

            // Filtrar cartas que tienen imagen
            List<Cards> cardsWithImage = response.getCards().stream()
                    .filter(card -> card.getImageUrl() != null && !card.getImageUrl().isEmpty())
                    .toList();

            // Verifica si hay cartas disponibles con imagen
            if (!cardsWithImage.isEmpty()) {
                Cards card = cardsWithImage.get(0); // Usar la primera carta que tiene imagen

                // Actualizamos los valores de los campos
                manaCost.setText(formatManaCost(card.getManaCost()));
                colors.setText(formatColors(card.getColors()));
                type.setText(String.valueOf(card.getType()));

                // Verifica si el poder es nulo
                power.setText(card.getPower() != null ? String.valueOf(card.getPower()) : "esta carta no tiene valor de ataque");

                // Verifica si la resistencia es nula
                toughness.setText(card.getToughness() != null ? String.valueOf(card.getToughness()) : "esta carta no tiene valor de defensa");

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


    // Método para formatear el costo de maná
    private String formatManaCost(String manaCost) {
        String noMana = "No mana cost";
        if (manaCost != null && !manaCost.isEmpty() && !manaCost.equals("{0}")) {
            StringBuilder formattedMana = new StringBuilder();
            // Extraer los símbolos de maná
            String[] parts = manaCost.split("\\{");
            // Obtener el coste de maná incoloro
            if (parts.length > 1 && !parts[1].trim().isEmpty()) {
                String colorlessPart = parts[1].trim().replace("}", ""); // Eliminar el carácter '}'

                // Comprobar si colorlessPart es un número
                if (colorlessPart.matches("\\d+")) { // \\d+ significa "uno o más dígitos"
                    formattedMana.append(colorlessPart).append(" generic");
                }
                // Si no es un número, no hacemos nada
            }
            // Usar un mapa para contar los colores
            Map<String, Integer> colorCounts = new HashMap<>();
            // Procesar los símbolos de maná
            for (int i = 1; i < parts.length; i++) {
                String colorPart = parts[i].replace("}", "");
                if (colorPart.isEmpty()) continue; // Evitar procesar entradas vacías
                // Asignar el número de maná por color (si hay un número)
                String colorSymbol = colorPart;
                int count = 1; // Por defecto, un símbolo de color
                // Comprobar si hay un número antes del símbolo de color
                if (Character.isDigit(colorSymbol.charAt(0))) {
                    count = Integer.parseInt(colorSymbol.substring(0, 1)); // Obtener el número antes del color
                    colorSymbol = colorSymbol.substring(1); // Remover el número para procesar solo el color
                }
                // Usar el mapa para contar la cantidad de cada color
                colorCounts.put(colorSymbol.toUpperCase(), colorCounts.getOrDefault(colorSymbol.toUpperCase(), 0) + count);
            }
            // Construir la cadena de salida con los colores contados
            boolean hasAddedColorless = false; // Variable para verificar si se ha añadido colorless
            boolean isFirstColor = true; // Variable para verificar si es el primer color
            // Supongamos que este bloque ya ha añadido un valor incoloro antes
            if (!formattedMana.isEmpty()) {
                hasAddedColorless = true; // Cambiamos a true ya que se ha añadido un valor incoloro
            }
            for (Map.Entry<String, Integer> entry : colorCounts.entrySet()) {
                String color = entry.getKey();
                int totalCount = entry.getValue();
                switch (color) {
                    case "W":
                        if (!isFirstColor || hasAddedColorless) { // Comprobar si ya se ha añadido un valor
                            formattedMana.append(", "); // Añadir coma solo si no es el primer color o ya hay un colorless
                        }
                        formattedMana.append(totalCount).append(" White");
                        isFirstColor = false; // Cambiar a false después de añadir el primer color
                        break;
                    case "U":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Blue");
                        isFirstColor = false;
                        break;
                    case "B":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Black");
                        isFirstColor = false;
                        break;
                    case "R":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Red");
                        isFirstColor = false;
                        break;
                    case "G":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Green");
                        isFirstColor = false;
                        break;
                    case "C":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Colorless");
                        isFirstColor = false;
                        break;
                }
            }
            return formattedMana.toString();
        }
        return noMana;
    }

    // Método para formatear los colores
    private String formatColors(List<String> colors) {
        if (colors == null || colors.isEmpty()) {
            return "Colorless";
        }
        return colors.stream()
                .map(color -> {
                    return switch (color) {
                        case "W" -> "White";
                        case "U" -> "Blue";
                        case "B" -> "Black";
                        case "R" -> "Red";
                        case "G" -> "Green";
                        default -> color; // En caso de un color no reconocido
                    };
                })
                .collect(Collectors.joining(", "));
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
