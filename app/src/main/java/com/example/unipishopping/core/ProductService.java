package com.example.unipishopping.core;

import android.util.Log;

import com.example.unipishopping.domain.Product;
import com.example.unipishopping.domain.Purchase;
import com.example.unipishopping.domain.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.function.Consumer;

public class ProductService{
    private final DatabaseReference productReference;
    private final DatabaseReference userReference;

    public ProductService() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        productReference = database.getReference("products");
        userReference = database.getReference("users");
    }

    public void order(int productId, User buyer, OrderEventListener listener){
        getProductById(productId, product -> {
            if (product == null) {
                listener.onOrderFailed(OrderError.productNotFound());
                return;
            }

            Purchase purchase = buyer.order(product);

            userReference
                    .child(buyer.getUsername())
                    .child("purchases")
                    .setValue(purchase)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            listener.onOrderSuccess();
                        } else {
                            listener.onOrderFailed(OrderError.taskFailed());
                        }
                    });

        });
    }

    public void addProducts(List<Product> products) { products.forEach(this::addProduct); }
    public void addProduct(Product product) {
        productReference
                .child(String.valueOf(product.getId()))
                .setValue(product);
    }


    public void getProductById(int productId, Consumer<Product> callback) {
        productReference.child(String.valueOf(productId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    callback.accept(snapshot.getValue(Product.class));
                    return;
                }
            } else {
                Log.e("Unipi Shopping FB", "Fetch error", task.getException());
            }

            callback.accept(null);
        });
    }
}
