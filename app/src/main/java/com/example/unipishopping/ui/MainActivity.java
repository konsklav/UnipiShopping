package com.example.unipishopping.ui;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import com.example.unipishopping.R;
import com.example.unipishopping.core.ProductProvider;
import com.example.unipishopping.core.ProductService;
import com.example.unipishopping.databinding.ActivityMainBinding;
import com.example.unipishopping.domain.Product;
import com.example.unipishopping.domain.User;
import com.example.unipishopping.ui.constants.IntentExtras;
import com.example.unipishopping.ui.list.ProductRecyclerViewAdapter;
import com.example.unipishopping.ui.list.ProductRecyclerViewHelper;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppActivityBase<ActivityMainBinding> {
    @Override
    protected void onAfterCreate() {
        User user = getIntent().getParcelableExtra(IntentExtras.USER_PARCELABLE);
        ProductProvider.getInstance().setOnReceivedListener(products -> {
            RecyclerView productList = getBinding().rvProducts;
            ProductRecyclerViewAdapter adapter = new ProductRecyclerViewAdapter(this, products, p -> {
                Intent intent = new Intent(this, ProductActivity.class);
                intent.putExtra(IntentExtras.PRODUCT_PARCELABLE, p);
                intent.putExtra(IntentExtras.USER_PARCELABLE, user);
                startActivity(intent);
            });

            productList.setAdapter(adapter);
            ProductRecyclerViewHelper.applyStyling(productList, this);
        });

        List<Product> products = new ArrayList<>();
        products.add(new Product(1, R.string.product_name_1, "Testing!", System.currentTimeMillis(), 19.99, 34, 40, R.drawable.bag1_icon));
        products.add(new Product(2, R.string.product_name_2, "Testing!", System.currentTimeMillis(), 9.99, 43, 11, R.drawable.bag2_icon));

        new ProductService().addProducts(products);
    }
}