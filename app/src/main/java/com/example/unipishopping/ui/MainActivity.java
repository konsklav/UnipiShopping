package com.example.unipishopping.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.unipishopping.R;
import com.example.unipishopping.domain.Product;
import com.example.unipishopping.domain.Purchase;
import com.example.unipishopping.domain.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference productsReference;
    private DatabaseReference usersReference;

    public User testUser;
    public Product testProduct;
    public Purchase testPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        testUser = new User(1, "test", "test", "test", "test", new ArrayList<>());
        testProduct = new Product(1, "test", "test", LocalDate.now(), 2.99d, 1.233455353d, 4.245345665d);
        testPurchase = new Purchase(1, LocalDateTime.now());

        testUser.addPurchase(testPurchase);

        database = FirebaseDatabase.getInstance();
        productsReference = database.getReference("products");
        usersReference = database.getReference("users");

        productsReference.setValue(testProduct);
        usersReference.setValue(testUser);
    }
}