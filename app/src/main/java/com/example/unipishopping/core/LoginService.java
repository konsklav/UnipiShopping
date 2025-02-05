package com.example.unipishopping.core;

import com.example.unipishopping.domain.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Abstracts the login procedure for Activities
 */
public class LoginService {
    private final DatabaseReference usersReference;

    public LoginService() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersReference = database.getReference("users");
    }

    /**
     * Attempt to login by searching through Firebase Realtime Database for the given username and password
     * @param callbacks The caller provides an implementation of LoginCallbacks to get notified
     *                  when the login either succeeded or failed.
     */
    public void login(String username, String password, LoginCallbacks callbacks) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            callbacks.onLoginFail(LoginError.invalidCredentials());
            return;
        }

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

                    callbacks.onLoginFail(LoginError.passwordNotFound());
                    return;
                }

                callbacks.onLoginFail(LoginError.usernameNotFound());
            }

            callbacks.onLoginFail(LoginError.taskFailed());
        });
    }
}
