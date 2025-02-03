package com.example.unipishopping.domain;

import java.time.LocalDate;

public class Product {
    int id;
    String title;
    String description;
    LocalDate releaseDate;
    float price;
    float locationLatitude;
    float locationLongitude;

    // Required for Firebase
    private Product() {}

    public Product(int id, String title, String description, LocalDate releaseDate, float price, float locationLatitude, float locationLongitude) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.price = price;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }
}
