package com.example.unipishopping.core;

import com.example.unipishopping.domain.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginService {
    private final DatabaseReference usersReference;

    public LoginService() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersReference = database.getReference("users");
    }

    public void login(String username, String password,  LoginCallbacks callbacks) {
        usersReference.child(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user == null) {
                        callbacks.onLoginFail(LoginError.taskFailed());
                        return;
                    }

                    if (user.getPassword().equals(password)) {
                        callbacks.onLoginSuccess(user);
                        return;
                    }

                    callbacks.onLoginFail(LoginError.invalidPassword());
                    return;
                }

                callbacks.onLoginFail(LoginError.invalidUsername());
            }

            callbacks.onLoginFail(LoginError.taskFailed());
        });
    }
}
