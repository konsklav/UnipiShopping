package com.example.unipishopping.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Purchase implements Parcelable {
    int productId;
    int userId;
    long timestamp;

    // Required for Firebase
    private Purchase() {}

    public Purchase(int productId, int userId, long timestamp) {
        this.productId = productId;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    protected Purchase(Parcel in) {
        productId = in.readInt();
        timestamp = in.readLong();
    }

    public static final Creator<Purchase> CREATOR = new Creator<Purchase>() {
        @Override
        public Purchase createFromParcel(Parcel in) {
            return new Purchase(in);
        }

        @Override
        public Purchase[] newArray(int size) {
            return new Purchase[size];
        }
    };

    // Required for Firebase
    public int getProductId() { return productId; }
    public int getUserId() {
        return userId;
    }
    public long getTimestamp() { return this.timestamp; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeLong(timestamp);
    }
}
