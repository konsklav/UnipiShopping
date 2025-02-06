package com.example.unipishopping.ui;

import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.unipishopping.core.products.ProductLocationListener;
import com.example.unipishopping.core.products.ProductProvider;
import com.example.unipishopping.core.products.ProductReceivedListener;
import com.example.unipishopping.databinding.ActivityMainBinding;
import com.example.unipishopping.domain.Product;
import com.example.unipishopping.domain.User;
import com.example.unipishopping.ui.constants.IntentExtras;
import com.example.unipishopping.ui.list.ProductRecyclerViewAdapter;
import com.example.unipishopping.ui.list.ProductRecyclerViewHelper;

import java.util.List;


public class MainActivity extends AppActivityBase<ActivityMainBinding> implements ProductReceivedListener {
    private User user;
    private ProductLocationListener listener;
    private ProductRecyclerViewAdapter adapter;

    @Override
    protected void onAfterCreate() {
        user = getIntent().getParcelableExtra(IntentExtras.USER_PARCELABLE);
        if (user == null) {
            Log.e("Main Activity", "User parcelable is NULL!");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        RecyclerView productList = getBinding().rvProducts;
        adapter = new ProductRecyclerViewAdapter(this, p -> {
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra(IntentExtras.PRODUCT_PARCELABLE, p);
            intent.putExtra(IntentExtras.USER_PARCELABLE, user);
            startActivity(intent);
        });

        productList.setAdapter(adapter);
        ProductRecyclerViewHelper.applyStyling(productList, this);

        ProductProvider.getInstance().setOnReceivedListener(this);
        listener = new ProductLocationListener(this, 2500);
    }


    @Override
    public void onProductsReceived(List<Product> products) {
        adapter.add(products);
    }

    @Override
    protected void onStop() {
        super.onStop();
        listener.stopListening();
        ProductProvider.getInstance().removeOnReceivedListener(this);
    }
}