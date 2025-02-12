package com.example.unipishopping.core.products;

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
    private final DatabaseReference purchasesReference;

    private static final String TAG = "Product Service";

    public ProductService() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        purchasesReference = database.getReference("purchases");
        productReference = database.getReference("products");
    }

    public void order(int productId, User buyer, OrderEventListener listener){
        getProductById(productId, product -> {
            if (product == null) {
                listener.onOrderFailed(OrderError.productNotFound());
                return;
            }

            Purchase purchase = buyer.order(product);

            purchasesReference
                    .push() // This auto-generates a unique ID for the purchase!
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
                    Product product = snapshot.getValue(Product.class);
                    if (product == null) {
                        Log.e(TAG, "Couldn't deserialize Product into class!");
                        callback.accept(null);
                        return;
                    }

                    callback.accept(product);
                    return;
                }
            } else {
                Log.e("Unipi Shopping FB", "Fetch error", task.getException());
            }

            callback.accept(null);
        });
    }
}
