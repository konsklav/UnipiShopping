package com.example.unipishopping.ui;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import com.example.unipishopping.R;
import com.example.unipishopping.databinding.ActivityMainBinding;
import com.example.unipishopping.domain.Product;
import com.example.unipishopping.domain.User;
import com.example.unipishopping.ui.constants.IntentExtras;
import com.example.unipishopping.ui.list.ProductRecyclerViewAdapter;
import com.example.unipishopping.ui.list.ProductRecyclerViewHelper;

public class MainActivity extends AppActivityBase<ActivityMainBinding> {
    @Override
    protected void onAfterCreate() {
        User user = getIntent().getParcelableExtra(IntentExtras.USER_PARCELABLE);

        RecyclerView productList = getBinding().rvProducts;
        ProductRecyclerViewAdapter adapter = new ProductRecyclerViewAdapter(this, this::goToProduct);

        productList.setAdapter(adapter);
        ProductRecyclerViewHelper.applyStyling(productList, this);

        Product testProduct1 = new Product(
                1,
                R.string.product_name_1,
                "Test",
                System.currentTimeMillis(),
                19.99,
                31.5,
                41.5,
                R.drawable.bag1_icon);
        Product testProduct2 = new Product(
                2,
                R.string.product_name_2,
                "Test Test Test!",
                System.currentTimeMillis(),
                6.99,
                31.5,
                41.5,
                R.drawable.bag2_icon);

        adapter.add(testProduct1, 0);
        adapter.add(testProduct2, 1);
    }

    private void goToProduct(Product product) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra(IntentExtras.PRODUCT_PARCELABLE, product);
        startActivity(intent);
    }
}