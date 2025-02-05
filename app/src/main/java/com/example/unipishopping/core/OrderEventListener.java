package com.example.unipishopping.core;

public interface OrderEventListener {
    void onOrderSuccess();
    void onOrderFailed(OrderError error);
}
