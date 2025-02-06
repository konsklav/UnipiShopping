package com.example.unipishopping.ui;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.unipishopping.core.OrderError;
import com.example.unipishopping.core.OrderEventListener;
import com.example.unipishopping.core.ProductService;
import com.example.unipishopping.databinding.ActivityProductBinding;
import com.example.unipishopping.domain.Product;
import com.example.unipishopping.domain.User;
import com.example.unipishopping.ui.constants.IntentExtras;
import com.example.unipishopping.ui.formats.NumFormatter;

public class ProductActivity extends AppActivityBase<ActivityProductBinding> implements OrderEventListener {
    private ProductService productService;
    private Product product;
    private User user;
    private ImageView buyBtn;

    @Override
    protected void onAfterCreate() {
        user = getIntent().getParcelableExtra(IntentExtras.USER_PARCELABLE);
        product = getIntent().getParcelableExtra(IntentExtras.PRODUCT_PARCELABLE);
        if (product == null) {
            Log.e("Product Activity", "Product parcelable is NULL!");
            return;
        }

        productService = new ProductService();

        getBinding().ivProductImg.setImageResource(product.getImageId());
        getBinding().tvProductName.setText(product.getTitleId());
        getBinding().tvProductPrice.setText(NumFormatter.formatAsPrice(product.getPrice(), getLocale()));

        buyBtn = getBinding().ivBuyButton;
        buyBtn.setOnClickListener(v -> {
            runOnUiThread(() -> buyBtn.setEnabled(false));
            productService.order(product.getId(), user, this);
        });
    }

    @Override
    public void onOrderSuccess() {
        Toast.makeText(
                this,
                "You have purchased '" + getString(product.getTitleId()) + "'.",
                Toast.LENGTH_LONG).show();
        runOnUiThread(() -> buyBtn.setEnabled(true));
    }

    @Override
    public void onOrderFailed(OrderError error) {
        Toast.makeText(this, error.getDescription(), Toast.LENGTH_LONG).show();
        runOnUiThread(() -> buyBtn.setEnabled(true));
    }
}