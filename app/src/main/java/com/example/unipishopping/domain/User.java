package com.example.unipishopping.domain;

import java.util.List;

public class User {
    int id;
    String firstname;
    String lastname;
    String username;
    String password;
    List<Purchase> purchases = null;

    // Required for Firebase
    private User() {}

    public User(int id, String firstname, String lastname, String username, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }
}
