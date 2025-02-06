package com.example.unipishopping.ui;

import com.example.unipishopping.R;
import com.example.unipishopping.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductExampleList {
    public List<Product> getExampleProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1, R.string.product_name_1, "Testing!", System.currentTimeMillis(), 19.99, 37.960289766212625, 23.753818262136246, R.drawable.bag1_icon));
        products.add(new Product(2, R.string.product_name_2, "Testing!", System.currentTimeMillis(), 9.99, 37.423, -122.08405, R.drawable.bag2_icon));

        return products;
    }
}
