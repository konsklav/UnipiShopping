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
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ProductLocationListener implements ProductReceivedListener, LocationListener {

    private final LocationManager locationManager;
    private final Context context;
    private static final int LOCATION_DELAY_MIN = 5000;
    private static final float LOCATION_DISTANCE_MIN = 5;
    private static final String TAG = ProductLocationListener.class.getName();

    private final double detectionRadius;
    private List<Product> products = new ArrayList<>();

    public static final String[] REQUIRED_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @SuppressLint("MissingPermission")
    public ProductLocationListener(Context context, double detectionRadius) {
        locationManager = context.getSystemService(LocationManager.class);
        this.context = context;

        this.detectionRadius = detectionRadius;
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
}
