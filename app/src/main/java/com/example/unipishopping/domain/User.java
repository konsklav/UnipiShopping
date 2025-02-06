package com.example.unipishopping.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {
    int id;
    String username;
    String password;

    // Required for Firebase
    private User() {}

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Required for Firebase
    public int getId() { return this.id; }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public Purchase order(Product product) {
        return new Purchase(product.getId(), getId(), System.currentTimeMillis());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private User(Parcel in) {
        id = in.readInt();
        username = in.readString();
        password = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(password);
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
