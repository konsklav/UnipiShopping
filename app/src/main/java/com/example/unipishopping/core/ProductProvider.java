package com.example.unipishopping.core;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.unipishopping.domain.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A singleton instance containing all the products. The products list is updated in realtime.
 */
public class ProductProvider {
    private static ProductProvider instance;
    public static ProductProvider getInstance() {
        if (instance == null) {
            instance = new ProductProvider();
        }

        return instance;
    }

    private final DatabaseReference productsReference;
    private final ArrayList<Product> products;

    private ProductProvider() {
        productsReference = FirebaseDatabase
                .getInstance()
                .getReference("products");

        productsReference.addChildEventListener(new ProductListener());

        products = new ArrayList<>();
    }

    public List<Product> getAllProducts() {
        return products;
    }

    private class ProductListener implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            // TODO
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            // TODO
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            // TODO
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            // TODO
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            // TODO
        }
    }
}
