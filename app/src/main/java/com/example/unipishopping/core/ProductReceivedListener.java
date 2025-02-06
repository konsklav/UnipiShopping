package com.example.unipishopping.core;

import com.example.unipishopping.domain.Product;

import java.util.List;

public interface ProductReceivedListener {
    void onProductsReceived(List<Product> products);
}
