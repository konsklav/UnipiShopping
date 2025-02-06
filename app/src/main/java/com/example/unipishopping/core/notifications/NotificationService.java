package com.example.unipishopping.core.notifications;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.unipishopping.R;

import java.util.function.Consumer;

/**
 * Responsible for building and showing notifications to the user
 */
public class NotificationService {
    private static final String TAG = "UniPiShopping Notifications";
    private static final String CHANNEL_ID = "UniPiShopping_NOTIF";
    private static final String CHANNEL_NAME = "UniPiPliShopping";

    /**
     * Creates a notification channel for sending notifications.
     */
    public void start(Context context) {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
        channel.setDescription("General purpose notifications for UniPi Shopping!");

        context.getSystemService(NotificationManager.class)
                .createNotificationChannel(channel);
    }

    /**
     * Shows the notification after building it through the <b>notificationConfiguration</b> delegate.
     */
    public void show(Context context, Consumer<NotificationCompat.Builder> notificationConfiguration) {
        int notificationId = 1;

        Log.i(TAG, "Showing notification with ID '" + notificationId + "'.");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.earth_icon)
                .setAutoCancel(true);

        // Further build the notification
        notificationConfiguration.accept(builder);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Activity doesn't have POST_NOTIFICATIONS permission! Requesting access...");
            return;
        }

        NotificationManagerCompat
                .from(context)
                .notify(notificationId, builder.build());
    }
}
