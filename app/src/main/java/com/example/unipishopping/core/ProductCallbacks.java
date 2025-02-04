package com.example.unipishopping.core;

import com.example.unipishopping.domain.Product;
import com.example.unipishopping.domain.User;

public interface ProductCallbacks {
    void onProductFound(Product product, User buyer);
    void onProductNotFound(); // TODO!
}
