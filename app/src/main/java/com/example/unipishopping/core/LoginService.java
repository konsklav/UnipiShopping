package com.example.unipishopping.core;

import androidx.annotation.NonNull;

import com.example.unipishopping.domain.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;

public class LoginService {
    private boolean login;

    private final FirebaseDatabase database;
    private final DatabaseReference usersReference;

    public LoginService() {
        this.login = false;

        database = FirebaseDatabase.getInstance();
        usersReference = database.getReference("users");
    }

    public boolean login(String username, String password) {
        checkDatabase(username, password, user -> { if(user != null) { login = true; } });
        return login;
    }

    public void checkDatabase(String username, String password, Consumer<User> callback) {
        usersReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String storedPassword = userSnapshot.child("password").getValue(String.class);
                        if (storedPassword != null && storedPassword.equals(password)) {
                            callback.accept(userSnapshot.getValue(User.class)); // Login successful
                            return;
                        }
                    }
                }
                callback.accept(null); // No user found or wrong password
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.accept(null); // Handle errors
            }
        });
    }
}
