package com.example.unipishopping.core.products;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.unipishopping.domain.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ProductLocationListener implements ProductReceivedListener, LocationListener {

    private final LocationManager locationManager;
    private final Context context;
    private static final long COOLDOWN_MS = 1000 * 60 * 3; // 3 Minute Cooldown
    private static final int LOCATION_DELAY_MIN = 5000;
    private static final float LOCATION_DISTANCE_MIN = 0;
    private static final String TAG = ProductLocationListener.class.getName();

    private final double detectionRadius;
    private static final HashMap<Integer, Long> nearbyProductTimeout = new HashMap<>();
    private List<Product> products = new ArrayList<>();
    private final NearbyProductsListener nearbyCallback;

    public static final String[] REQUIRED_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @SuppressLint("MissingPermission")
    public ProductLocationListener(Context context, double detectionRadius, NearbyProductsListener callback) {
        locationManager = context.getSystemService(LocationManager.class);
        this.context = context;

        this.detectionRadius = detectionRadius;
        this.nearbyCallback = callback;
        ProductProvider.getInstance().setOnReceivedListener(this);

        if (hasMissingLocationPermissions()) {
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_DELAY_MIN,
                LOCATION_DISTANCE_MIN,
                this);
    }

    @Override
    public void onProductsReceived(List<Product> products) {
        this.products = products;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        checkForNearbyProducts(location);
    }

    public void stopListening() {
        locationManager.removeUpdates(this);
        ProductProvider.getInstance().removeOnReceivedListener(this);
    }

    private void checkForNearbyProducts(Location clientLocation) {
        double lon = clientLocation.getLongitude();
        double lat = clientLocation.getLatitude();

        Log.v(TAG, String.format(Locale.getDefault(),"Received location (%.5f, %.5f)", lat, lon));
        List<Product> nearbyProducts = products
                .stream()
                .filter(this::isCooledDown)
                .filter(p -> {
                    double pLon = p.getLocationLongitude();
                    double pLat = p.getLocationLatitude();

                    float[] distanceResults = new float[1];
                    Location.distanceBetween(lat, lon, pLat, pLon, distanceResults);

                    float distance = distanceResults[0];

                    Log.v(TAG, "D(" + p.getId() + ") = " + distance);
                    return distance <= detectionRadius;
                })
                .collect(Collectors.toList());

        invokeProductFound(nearbyProducts);
        Log.i(TAG, nearbyProducts.size() + " nearby products found.");
    }

    private boolean hasMissingLocationPermissions() {
        return !Arrays.stream(REQUIRED_PERMISSIONS)
                .allMatch(perm -> {
                    int grantType = ActivityCompat.checkSelfPermission(context, perm);
                    boolean isGranted = grantType == PackageManager.PERMISSION_GRANTED;

                    if (isGranted) {
                        Log.i(TAG, "Permission " + perm + " GRANTED!");
                    } else {
                        Log.w(TAG, "Permission " + perm + " DENIED :(");
                    }

                    return isGranted;
                });
    }

    private void invokeProductFound(List<Product> products) {
        if (!products.isEmpty()) {
            Product product = products.get(0);
            Log.i(TAG, "Invoking callback for '" + product.getId() + "'.");
            nearbyCallback.onNearbyProductFound(product);
            nearbyProductTimeout.put(product.getId(), getCurrentMs());
        }
    }

    private boolean isCooledDown(Product product) {
        Long lastNotified = nearbyProductTimeout.get(product.getId());
        if (lastNotified == null)
            return true;

        long elapsed = getCurrentMs() - lastNotified;
        if (elapsed >= COOLDOWN_MS) {
            nearbyProductTimeout.remove(product.getId());
            return true;
        }

        return false;
    }

    private long getCurrentMs() { return System.currentTimeMillis(); }
}
