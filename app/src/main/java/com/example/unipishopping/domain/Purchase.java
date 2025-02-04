package com.example.unipishopping.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class Purchase {
    int productId;
    long timestamp;

    // Required for Firebase
    private Purchase() {}

    public Purchase(int productId, long timestamp) {
        this.productId = productId;
        this.timestamp = timestamp;
    }

    // Required for Firebase
    public int getProductId() { return productId; }
    public long getTimestamp() { return this.timestamp; }
}
