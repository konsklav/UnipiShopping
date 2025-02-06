package com.example.unipishopping.core;

import android.content.Context;
import android.location.LocationManager;

import com.example.unipishopping.domain.Product;

import java.util.List;

public class ProductLocationListener implements ProductReceivedListener {

    private final LocationManager locationManager;

    public ProductLocationListener(Context context) {
        locationManager = context.getSystemService(LocationManager.class);
    }

    @Override
    public void onProductsReceived(List<Product> products) {

    }
}
