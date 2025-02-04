package com.example.unipishopping.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    int id;
    String username;
    String password;
    List<Purchase> purchases = new ArrayList<>();

    // Required for Firebase
    private User() {}

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public User(int id, String username, String password, List<Purchase> purchases)  {
        this(id, username, password);
        this.purchases = purchases;
    }

    // Required for Firebase
    public int getId() { return this.id; }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public List<Purchase> getPurchases() { return this.purchases; }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private User(Parcel in) {
        id = in.readInt();
        username = in.readString();
        password = in.readString();

        in.readTypedList(purchases, Purchase.CREATOR);
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeTypedList(purchases);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
