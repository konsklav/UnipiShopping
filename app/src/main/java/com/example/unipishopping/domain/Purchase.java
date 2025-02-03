package com.example.unipishopping.domain;

import java.time.LocalDateTime;

public class Purchase {
    int userId;
    int productId;
    LocalDateTime timestamp;

    // Required for Firebase
    private Purchase() {}

    public Purchase(int userId, int productId, LocalDateTime timestamp) {
        this.userId = userId;
        this.productId = productId;
        this.timestamp = timestamp;
    }

    // Required for Firebase
    public int getUserId() { return userId; }
    public int getProductId() { return productId; }
    public LocalDateTime getTimestamp() { return this.timestamp; }
}
