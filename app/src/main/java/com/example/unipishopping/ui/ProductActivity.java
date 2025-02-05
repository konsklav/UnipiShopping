package com.example.unipishopping.ui;

import android.util.Log;
import android.widget.Toast;

import com.example.unipishopping.databinding.ActivityProductBinding;
import com.example.unipishopping.domain.Product;
import com.example.unipishopping.ui.constants.IntentExtras;
import com.example.unipishopping.ui.formats.NumFormatter;

public class ProductActivity extends AppActivityBase<ActivityProductBinding> {
    @Override
    protected void onAfterCreate() {
        Product product = getIntent().getParcelableExtra(IntentExtras.PRODUCT_PARCELABLE);
        if (product == null) {
            Log.e("Product Activity", "Product parcelable is NULL!");
            return;
        }

        getBinding().ivProductImg.setImageResource(product.getImageId());
        getBinding().tvProductName.setText(product.getTitleId());
        getBinding().tvProductPrice.setText(NumFormatter.formatAsPrice(product.getPrice(), getLocale()));

        getBinding().ivBuyButton.setOnClickListener(v -> {
            Toast.makeText(this, "TEMP: You bought this product!!!", Toast.LENGTH_SHORT).show();
        });
    }
}