package edu.badpals.magictg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.badpals.magictg.model.Cards;
import edu.badpals.magictg.model.Response;
import edu.badpals.magictg.services.CacheManager;
import edu.badpals.magictg.services.saveLastSearch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {

    @FXML
    private Button btnBuscar;

    private String apiData;

    private final String CHARACTER_URL = "https://api.magicthegathering.io/v1/cards?name=";
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

    @FXML
    private Button returnToLogIn;

    private Map<String, Object> cache;

    public static String currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Cargar la caché al iniciar
        cache = CacheManager.cargarCache();  // Inicializamos la caché

        // Preguntar si el usuario quiere cargar su última búsqueda
        if (LoginController.currentUser != null) {
            preguntarCargaUltimaBusqueda(LoginController.currentUser);
        }
    }

    // Metodo para preguntar si cargar la última búsqueda
    private void preguntarCargaUltimaBusqueda(String nombreUsuario) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cargar última búsqueda");
        alert.setHeaderText("¿Desea cargar su última búsqueda?");
        alert.setContentText("Carga tu última búsqueda o empieza una nueva.");

        ButtonType buttonTypeSi = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeSi) {
            // Si el usuario elige "Sí", cargamos la última búsqueda
            cargarUltimaBusqueda(nombreUsuario);
        }
    }

    // Metodo que carga la última búsqueda del usuario
    private void cargarUltimaBusqueda(String nombreUsuario) {
        Map<String, Object> ultimaBusqueda = saveLastSearch.cargarUltimaBusqueda(nombreUsuario);
        if (ultimaBusqueda != null) {
            // Extraer la respuesta de la búsqueda
            Response response = new ObjectMapper().convertValue(ultimaBusqueda.get("response"), Response.class);

            // Mostrar los datos de la última búsqueda
            mostrarDatos(response, nombreUsuario);

            // Obtener el nombre de la primera carta de la respuesta
            if (response.getCards() != null && !response.getCards().isEmpty()) {
                String nombreCarta = response.getCards().get(0).getName();
                // Mostrar el nombre de la carta en el campo de búsqueda
                search.setText(nombreCarta);
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Sin última búsqueda");
            alerta.setHeaderText(null);
            alerta.setContentText("No se encontró una búsqueda guardada.");
            alerta.showAndWait();
        }
    }

    @FXML
    public void setNameCard(ActionEvent event) {
        try {
            String nameInput = URLEncoder.encode(search.getText(), StandardCharsets.UTF_8);

            // Verificar si la búsqueda ya está en la caché
            if (cache.containsKey(nameInput)) {
                System.out.println("Datos obtenidos desde la caché.");

                Object cachedData = cache.get(nameInput);
                Response response;

                if (cachedData instanceof LinkedHashMap) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    response = objectMapper.convertValue(cachedData, Response.class);
                } else {
                    response = (Response) cachedData;
                }

                mostrarDatos(response, nameInput);

                return;
            }

            // Hacer la solicitud a la API
            URL jsonURL = new URL(CHARACTER_URL + nameInput);
            HttpURLConnection connection = (HttpURLConnection) jsonURL.openConnection();
            connection.setRequestMethod("GET");

            ObjectMapper objectMapper = new ObjectMapper();
            Response response = objectMapper.readValue(connection.getInputStream(), Response.class);



            // Guardar los datos en la caché
            cache.put(nameInput, response);
            CacheManager.guardarCache(cache);


            mostrarDatos(response, nameInput); ;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarDatos(Response response, String nameInput) {
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
            power.setText(card.getPower() != null ? String.valueOf(card.getPower()) : "Esta carta no tiene valor de ataque");

            // Verifica si la resistencia es nula
            toughness.setText(card.getToughness() != null ? String.valueOf(card.getToughness()) : "Esta carta no tiene valor de resistencia");

            // Aquí obtienes la URL de la imagen desde la API o el objeto `Cards`
            String imageUrl = card.getImageUrl();

            // Verifica si la URL usa http, y cámbiala a https
            if (imageUrl.startsWith("http://")) {
                imageUrl = imageUrl.replace("http://", "https://");
            }

            // Carga la imagen en el ImageView
            Image image = new Image(imageUrl);
            cardView.setImage(image);
            saveLastSearch.guardarUltimaBusqueda(LoginController.currentUser, nameInput, response);
        } else {
            // Manejo del caso en que no haya cartas con imagen
            System.out.println("No hay cartas con imagen disponible.");
        }
    }





    // Metodo para formatear el costo de maná
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

                if (colorlessPart.equals("X")) {
                    formattedMana.append("X mana cost");
                }

            } else {
                    return noMana;
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
                    case "R/P":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Phyrexian Red");
                        isFirstColor = false;
                        break;
                    case "U/P":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Phyrexian Blue");
                        isFirstColor = false;
                        break;
                    case "W/P":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Phyrexian White");
                        isFirstColor = false;
                        break;
                    case "B/P":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Phyrexian Black");
                        isFirstColor = false;
                        break;
                    case "G/P":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Phyrexian Green");
                        isFirstColor = false;
                        break;

                    case "B/R":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Black/Red");
                        isFirstColor = false;
                        break;

                    case "B/G":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Black/Green");
                        isFirstColor = false;
                        break;

                    case "G/U":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Green/Blue");
                        isFirstColor = false;
                        break;

                    case "G/W":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Green/White");
                        isFirstColor = false;
                        break;

                    case "R/W":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Red/White");
                        isFirstColor = false;
                        break;

                    case "R/G":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Red/Green");
                        isFirstColor = false;
                        break;

                    case "U/B":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Blue/Black");
                        isFirstColor = false;
                        break;

                    case "U/R":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" Blue/Red");
                        isFirstColor = false;
                        break;

                    case "W/B":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" White/Black");
                        isFirstColor = false;
                        break;

                    case "W/U":
                        if (!isFirstColor || hasAddedColorless) {
                            formattedMana.append(", ");
                        }
                        formattedMana.append(totalCount).append(" White/Blue");
                        isFirstColor = false;
                        break;
                }
            }
            return formattedMana.toString();
        }
        return noMana;
    }

    // Metodo para formatear los colores
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
    public void toExportView(ActionEvent event) {

        try {
            if (apiData != null && !apiData.isEmpty()) {
                // Pasar los datos de la API a la escena de exportación
                secondWindowController.setApiData(apiData);
            }
            // Cargar la nueva vista desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/exportView.fxml"));
            Parent root = loader.load();

            // Obtener la ventana actual (stage) y cambiar la escena
            Stage stage = (Stage) btnPeople.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void toLogin(ActionEvent event) {
        try {
            // Cargar la nueva vista desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logIn.fxml"));
            Parent root = loader.load();

            // Obtener la ventana actual (stage) desde el botón o la escena activa
            Stage stage = (Stage) returnToLogIn.getScene().getWindow();

            // Configurar la nueva escena y ajustar tamaño mínimo
            stage.setScene(new Scene(root));
            stage.setMinWidth(369.6);
            stage.setMinHeight(370.4);

            // Centrar la ventana en pantalla
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeApp(ActionEvent actionEvent) {
        // Obtener la ventana (Stage) actual
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        // Cerrar la ventana
        stage.close();
    }

    private String fetchApiData() {
        StringBuilder result = new StringBuilder();
        try {
            String nameInput = URLEncoder.encode(search.getText(), StandardCharsets.UTF_8);
            URL url = new URL(CHARACTER_URL + nameInput);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();  // Retornar el JSON recibido
    }
}



