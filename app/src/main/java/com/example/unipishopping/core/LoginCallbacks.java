package com.example.unipishopping.core;

import com.example.unipishopping.domain.User;

public interface LoginCallbacks {
    void onLoginSuccess(User user);
    void onLoginFail(LoginError error);
}
