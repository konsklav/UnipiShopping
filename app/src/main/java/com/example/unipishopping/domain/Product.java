package com.example.unipishopping.domain;

import java.time.LocalDate;

public class Product {
    int id;
    int title;
    String description;
    LocalDate releaseDate;
    double price;
    double locationLatitude;
    double locationLongitude;

    // Required for Firebase
    private Product() {}

    public Product(int id, int title, String description, LocalDate releaseDate, double price, double locationLatitude, double locationLongitude) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.price = price;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }

    // Required for Firebase
    public int getId() { return this.id; }
    public int getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public LocalDate getReleaseDate() { return this.releaseDate; }
    public double getPrice() { return price; }
    public double getLocationLatitude() { return locationLatitude; }
    public double getLocationLongitude() { return locationLongitude; }
}
