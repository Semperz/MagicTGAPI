package edu.badpals.magictg.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.badpals.magictg.model.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class saveLastSearch {

    // Metodo para obtener la ruta del archivo según el nombre de usuario
    private static String getFilePath(String nombreUsuario) {
        return "src/main/resources/ultima_busqueda_" + nombreUsuario + ".json"; // Ruta del archivo específica para el usuario
    }

    public static void guardarUltimaBusqueda(String nombreUsuario, String nombreBusqueda, Response response) {
        Map<String, Object> ultimaBusqueda = new HashMap<>();
        ultimaBusqueda.put("nombreBusqueda", nombreBusqueda);
        ultimaBusqueda.put("response", response);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String filePath = getFilePath(nombreUsuario);
            objectMapper.writeValue(new File(filePath), ultimaBusqueda);
            System.out.println("Última búsqueda guardada para " + nombreUsuario + " en " + filePath); // Mensaje de confirmación
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> cargarUltimaBusqueda(String nombreUsuario) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String filePath = getFilePath(nombreUsuario); // Obtener la ruta del archivo para el usuario
            // Verifica si el archivo existe
            if (Files.exists(Paths.get(filePath))) {
                return objectMapper.readValue(new File(filePath), HashMap.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Si no se puede cargar, devuelve null
    }
}