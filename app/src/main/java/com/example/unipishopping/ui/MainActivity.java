package com.example.unipishopping.ui;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import com.example.unipishopping.core.ProductLocationListener;
import com.example.unipishopping.core.ProductProvider;
import com.example.unipishopping.core.ProductReceivedListener;
import com.example.unipishopping.core.ProductService;
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

    @Override
    protected void onAfterCreate() {
        user = getIntent().getParcelableExtra(IntentExtras.USER_PARCELABLE);
        ProductProvider.getInstance().setOnReceivedListener(this);

        new ProductService().addProducts(ProductExampleList.getExampleProducts());

        listener = new ProductLocationListener(this, 2500);
    }

    @Override
    public void onProductsReceived(List<Product> products) {
        RecyclerView productList = getBinding().rvProducts;
        ProductRecyclerViewAdapter adapter = new ProductRecyclerViewAdapter(this, products, p -> {
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra(IntentExtras.PRODUCT_PARCELABLE, p);
            intent.putExtra(IntentExtras.USER_PARCELABLE, user);
            startActivity(intent);
        });

        productList.setAdapter(adapter);
        ProductRecyclerViewHelper.applyStyling(productList, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listener.stopListening();
        ProductProvider.getInstance().removeOnReceivedListener(this);
    }
}