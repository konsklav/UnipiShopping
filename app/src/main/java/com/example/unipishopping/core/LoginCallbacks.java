package com.example.unipishopping.core;

import com.example.unipishopping.domain.User;

public interface LoginCallbacks {
    void onSuccess(User user);
    void onFail();
}
