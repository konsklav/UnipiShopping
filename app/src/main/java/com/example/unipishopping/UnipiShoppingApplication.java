package com.example.unipishopping;

import android.app.Application;

import com.example.unipishopping.core.notifications.NotificationService;

public class UnipiShoppingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        NotificationService notificationService = new NotificationService();
        notificationService.start(this);
    }
}
