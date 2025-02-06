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

    // Store listeners (such as Activities and NotificationService) in a list so we can invoke
    // all listeners' methods when receiving products from Firebase.
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

    /**
     * Registers the given <b>ProductReceivedListener</b>. If data is already present in the provider,
     * the listener's method is invoked immediately.
     */
    public void setOnReceivedListener(ProductReceivedListener listener) {
        if (hasInitialized) {
            Log.i(TAG, "Products were already initialized!");
            listener.onProductsReceived(products);
        }
        Log.i(TAG, "Added OnReceived event listener to ProductProvider");
        onReceivedListeners.add(listener);
    }

    /**
     * API consumers should take care to call this method as no automatic cleanup is done!
     */
    public void removeOnReceivedListener(ProductReceivedListener listener) {
        if (onReceivedListeners.remove(listener)) {
            Log.i(TAG, "Successfully removed OnReceived event listener from ProductProvider.");
        } else {
            Log.w(TAG, "Couldn't find OnReceived event listener to remove from ProductProvider.");
        }
    }

    /**
     * Simple implementation of ValueEventListener for Firebase. This is an inner class that acts as
     * a closure that has access to the outer-scope products List.
     */
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
