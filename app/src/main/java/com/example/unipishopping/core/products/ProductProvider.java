package com.example.unipishopping.core.products;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.unipishopping.domain.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A singleton instance containing all the products. The products list is updated in realtime.
 */
public class ProductProvider {
    private static final String TAG = "Products Provider";
    private static ProductProvider instance;
    public static ProductProvider getInstance() {
        if (instance == null) {
            instance = new ProductProvider();
        }

        return instance;
    }

    private final List<ProductReceivedListener> onReceivedListeners;
    private boolean hasInitialized = false;
    private final List<Product> products;

    private ProductProvider() {
        DatabaseReference productsReference = FirebaseDatabase
                .getInstance()
                .getReference("products");

        ProductValueListener listener = new ProductValueListener();
        productsReference.addValueEventListener(listener);

        onReceivedListeners = new ArrayList<>();
        products = new ArrayList<>();
    }

    public void setOnReceivedListener(ProductReceivedListener callback) {
        if (hasInitialized) {
            Log.i(TAG, "Products were already initialized!");
            callback.onProductsReceived(products);
        }
        onReceivedListeners.add(callback);
    }

    public void removeOnReceivedListener(ProductReceivedListener callback) {
        if (onReceivedListeners.remove(callback)) {
            Log.i(TAG, "Successfully removed OnReceived event listener from ProductProvider.");
        } else {
            Log.w(TAG, "Couldn't find OnReceived event listener to remove from ProductProvider.");
        }
    }

    private class ProductValueListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            int count = 0;

            // Not exactly thread-safe, but it will do!
            for (DataSnapshot child : snapshot.getChildren()) {
                whenValid(child, products::add);
                count++;
            }

            Log.i(TAG, "Retrieved " + count + " products from DB.");

            hasInitialized = true;
            onReceivedListeners.forEach(l -> l.onProductsReceived(products));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e(TAG, error.getDetails());
        }

        private void whenValid(DataSnapshot snapshot, Consumer<Product> callback) {
            Product product = snapshot.getValue(Product.class);
            if (product == null) {
                Log.e(TAG,
                        "Couldn't deserialize product from DataSnapshot! Key = '" +
                        snapshot.getKey() + "'. Ref = '" + snapshot.getRef().getKey() + "'.");
                return;
            }

            callback.accept(product);
        }
    }
}
