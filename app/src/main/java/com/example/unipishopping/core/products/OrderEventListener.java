package com.example.unipishopping.core.products;

public interface OrderEventListener {
    void onOrderSuccess();
    void onOrderFailed(OrderError error);
}
