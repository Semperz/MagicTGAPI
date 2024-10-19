package edu.badpals.magictg.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class userService {
    private static final String USERS_FILE = "users.json";
    private ObjectMapper objectMapper;

    public userService() {
        objectMapper = new ObjectMapper();
    }

    // Método para obtener todos los usuarios del JSON
    public List<User> getUsers() {
        try {
            File file = new File(USERS_FILE);
            if (file.exists()) {
                // Leer el archivo y convertirlo en una lista de usuarios
                return objectMapper.readValue(file, new TypeReference<List<User>>() {});
            } else {
                // Si el archivo no existe, retornar una lista vacía
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Método para guardar la lista de usuarios en el JSON
    public void saveUsers(List<User> users) {
        try {
            objectMapper.writeValue(new File(USERS_FILE), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para agregar un usuario al archivo JSON
    public void addUser(User user) {
        List<User> users = getUsers(); // Obtener la lista actual de usuarios
        users.add(user); // Agregar el nuevo usuario
        saveUsers(users); // Guardar la lista actualizada
    }

    // Método para buscar un usuario por ID
    public User findUserById(String id) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null; // Retorna null si el usuario no es encontrado
    }
}

