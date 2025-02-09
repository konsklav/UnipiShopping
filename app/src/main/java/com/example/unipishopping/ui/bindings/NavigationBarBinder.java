package com.example.unipishopping.ui.bindings;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;

import com.example.unipishopping.R;
import com.example.unipishopping.core.products.ProductLocationListener;
import com.example.unipishopping.ui.AppActivityBase;
import com.example.unipishopping.ui.MainActivity;
import com.example.unipishopping.ui.SettingsActivity;

public class NavigationBarBinder implements Binder{
    public <T extends ViewBinding> void bind(AppActivityBase<T> activity) {
        ImageButton homeBtn = activity.findViewById(R.id.nav_home);
        ImageButton locationBtn = activity.findViewById(R.id.nav_location);
        ImageButton settingsBtn = activity.findViewById(R.id.nav_settings);
        ConstraintLayout nav = activity.findViewById(R.id.navigationBar);

        if (homeBtn == null || locationBtn == null || settingsBtn == null || nav == null) {
            Log.w("Navbar", "Couldn't bind to navbar");
            return;
        }

        homeBtn.setOnClickListener(v -> {
            if (activity instanceof MainActivity) return;

            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        });

        settingsBtn.setOnClickListener(v -> {
            if (activity instanceof SettingsActivity) return;

            Intent intent = new Intent(activity, SettingsActivity.class);
            activity.startActivity(intent);
        });

        locationBtn.setOnClickListener(v -> {
            String[] locationPerms = ProductLocationListener.REQUIRED_PERMISSIONS;
            activity.requestPermissions(locationPerms, 2);
        });
    }
}
