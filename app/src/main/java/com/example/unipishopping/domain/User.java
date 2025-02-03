package com.example.unipishopping.domain;

public class User {
    int id;
    String firstname;
    String lastname;
    String username;
    String password;

    // Required for Firebase
    private User() {}

    public User(int id, String firstname, String lastname, String username, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }
}
