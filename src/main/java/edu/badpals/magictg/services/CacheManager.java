package edu.badpals.magictg.services;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    private static final String CACHE_FILE = "src/main/resources/cache.json";
    private static ObjectMapper objectMapper = new ObjectMapper();

    // Cargar la caché desde el archivo JSON
    public static Map<String, Object> cargarCache() {
        File archivoCache = new File(CACHE_FILE);

        if (!archivoCache.exists()) {
            // Si el archivo no existe, devolvemos un HashMap vacío
            return new HashMap<>();
        }

        try {
            // Leer el archivo JSON y convertirlo en un Map
            return objectMapper.readValue(archivoCache, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    // Guardar la caché en el archivo JSON
    public static void guardarCache(Map<String, Object> cache) {
        try {
            // Escribir el Map en el archivo JSON
            objectMapper.writeValue(new File(CACHE_FILE), cache);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
