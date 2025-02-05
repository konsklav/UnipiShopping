package com.example.unipishopping.domain;

public class Product {
    int id;
    int titleId;
    String description;
    long releaseDate;
    double price;
    double locationLatitude;
    double locationLongitude;

    int imageId;

    // Required for Firebase
    private Product() {}

    public Product(int id, int titleId, String description, long releaseDate, double price, double locationLatitude, double locationLongitude, int imageId) {
        this.id = id;
        this.titleId = titleId;
        this.description = description;
        this.releaseDate = releaseDate;
        this.price = price;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.imageId = imageId;
    }


    // Required for Firebase
    public int getId() { return this.id; }
    public int getTitleId() { return this.titleId; }
    public String getDescription() { return this.description; }
    public long getReleaseDate() { return this.releaseDate; }
    public double getPrice() { return price; }
    public double getLocationLatitude() { return locationLatitude; }
    public double getLocationLongitude() { return locationLongitude; }
    public int getImageId() { return imageId; }
}
