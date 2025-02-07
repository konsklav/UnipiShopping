package com.example.unipishopping.core.session;

import com.example.unipishopping.domain.User;

public class UserSession {
    private static UserSession instance;
    private User user;
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }

        return instance;
    }

    private UserSession() { }

    public User getUser() { return user; }
    public void setUser(User user) {
        this.user = user;
    }
}
