package com.example.unipishopping.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    int id;
    String firstname;
    String lastname;
    String username;
    String password;
    List<Purchase> purchases = new ArrayList<>();

    // Required for Firebase
    private User() {}

    public User(int id, String firstname, String lastname, String username, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }
    public User(int id, String firstname, String lastname, String username, String password, List<Purchase> purchases)  {
        this(id, firstname, lastname, username, password);
        this.purchases = purchases;
    }

    // Required for Firebase
    public int getId() { return this.id; }
    public String getFirstname() { return this.firstname; }
    public String getLastname() { return this.lastname; }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public List<Purchase> getPurchases() { return this.purchases; }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }
}
