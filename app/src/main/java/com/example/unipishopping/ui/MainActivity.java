package com.example.unipishopping.ui;

import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.unipishopping.core.notifications.NotificationService;
import com.example.unipishopping.core.products.NearbyProductsListener;
import com.example.unipishopping.core.products.ProductLocationListener;
import com.example.unipishopping.core.products.ProductProvider;
import com.example.unipishopping.core.products.ProductReceivedListener;
import com.example.unipishopping.core.session.UserSession;
import com.example.unipishopping.databinding.ActivityMainBinding;
import com.example.unipishopping.domain.Product;
import com.example.unipishopping.domain.User;
import com.example.unipishopping.ui.constants.IntentExtras;
import com.example.unipishopping.ui.list.ProductRecyclerViewAdapter;
import com.example.unipishopping.ui.list.ProductRecyclerViewHelper;

import java.util.List;


public class MainActivity
        extends AppActivityBase<ActivityMainBinding>
        implements ProductReceivedListener, NearbyProductsListener {
    private User user;
    private ProductLocationListener listener;
    private ProductRecyclerViewAdapter adapter;
    private NotificationService notificationService;

    @Override
    protected void onAfterCreate() {
        // Get user information from LoginActivity
        user = UserSession.getInstance().getUser();
        if (user == null) {
            Log.e("Main Activity", "User is NULL!");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        // Initialize RecyclerView for Products
        RecyclerView productList = getBinding().rvProducts;
        adapter = new ProductRecyclerViewAdapter(this, p -> {
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra(IntentExtras.PRODUCT_PARCELABLE, p);
            intent.putExtra(IntentExtras.USER_PARCELABLE, user);
            startActivity(intent);
        });

        productList.setAdapter(adapter);
        ProductRecyclerViewHelper.applyStyling(productList, this);

        // Defer the adding of the products to the RV to when the ProductProvider has
        // the products ready and available.
        ProductProvider.getInstance().setOnReceivedListener(this);

        notificationService = new NotificationService();
        listener = new ProductLocationListener(this, 2500, this);
    }

    @Override
    public void onProductsReceived(List<Product> products) {
        adapter.add(products);
    }

    /**
     * Detach/remove event listeners onStop and not onDestroy
     * <a href="https://developer.android.com/guide/components/activities/activity-lifecycle"></a>
     */
    @Override
    protected void onStop() {
        super.onStop();
        listener.stopListening();
        ProductProvider.getInstance().removeOnReceivedListener(this);
    }

    @Override
    public void onNearbyProductsFound(List<Product> nearbyProducts) {
        for (Product p : nearbyProducts) {
            String title = getString(p.getTitleId());

            notificationService.show(this, builder -> builder
                    .setContentTitle("'" + title + "' is nearby!"));
        }
    }
}