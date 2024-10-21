package edu.badpals.magictg.users;

public class User {
    private String id;
    private String password;

    // Constructor vacío requerido por Jackson
    public User() {}

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

