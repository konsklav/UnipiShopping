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
    private final FirebaseDatabase database;
    private final DatabaseReference usersReference;

    public LoginService() {
        database = FirebaseDatabase.getInstance();
        usersReference = database.getReference("users");
    }

    public void login(String username, String password,  LoginCallbacks callbacks) {
        usersReference.child(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if(user != null && user.getPassword().equals(password)) {
                        callbacks.onSuccess(user);
                        return;
                    }
                }
            }
            callbacks.onFail(); // No user found or wrong password
        });
    }
}
