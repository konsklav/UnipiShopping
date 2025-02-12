package com.example.unipishopping.core.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.unipishopping.domain.Product;

public class NearbyProductNotification {
    public static void show(Context context, Intent intent, Product product) {
        NotificationService service = new NotificationService();
        String title = context.getString(product.getTitleId());

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                product.getId(),
                intent,
                PendingIntent.FLAG_IMMUTABLE);

        Log.i("Nearby Products Notification", "Showing notification for '" + product.getId() + "'");

        service.show(context, builder -> builder
                .setContentTitle("'" + title + "' is nearby!")
                .setContentIntent(pendingIntent));
    }
}
