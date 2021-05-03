package com.codecool.shop.model;

public class User {
    private int id;
    private String name;
    private static User instance = null;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User(1, "John Kowalski");
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
