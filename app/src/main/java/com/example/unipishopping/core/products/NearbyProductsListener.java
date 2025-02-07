package com.example.unipishopping.core.products;

import com.example.unipishopping.domain.Product;

import java.util.List;

public interface NearbyProductsListener {
    void onNearbyProductsFound(List<Product> nearbyProducts);
}
