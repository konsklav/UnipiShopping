package com.example.unipishopping.core.products;

import com.example.unipishopping.domain.Product;

public interface NearbyProductsListener {
    void onNearbyProductFound(Product product);
}
