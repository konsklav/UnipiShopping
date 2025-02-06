package com.example.unipishopping;

import android.app.Application;

import com.example.unipishopping.core.notifications.NotificationService;
import com.example.unipishopping.core.products.ProductService;
import com.example.unipishopping.ui.ProductExampleList;

/**
 * For any code/services that need to run on startup.
 */
public class UnipiShoppingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        NotificationService notificationService = new NotificationService();
        notificationService.start(this);

        new ProductService().addProducts(ProductExampleList.getExampleProducts());
    }
}
