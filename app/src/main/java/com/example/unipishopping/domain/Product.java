package com.example.unipishopping.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {
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

    protected Product(Parcel in) {
        id = in.readInt();
        titleId = in.readInt();
        description = in.readString();
        releaseDate = in.readLong();
        price = in.readDouble();
        locationLatitude = in.readDouble();
        locationLongitude = in.readDouble();
        imageId = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(titleId);
        dest.writeString(description);
        dest.writeLong(releaseDate);
        dest.writeDouble(price);
        dest.writeDouble(locationLatitude);
        dest.writeDouble(locationLongitude);
        dest.writeInt(imageId);
    }
}
