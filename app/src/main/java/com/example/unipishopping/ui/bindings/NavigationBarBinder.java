package com.example.unipishopping.ui.bindings;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;

import com.example.unipishopping.R;
import com.example.unipishopping.core.products.ProductLocationListener;
import com.example.unipishopping.ui.AppActivityBase;
import com.example.unipishopping.ui.MainActivity;
import com.example.unipishopping.ui.SettingsActivity;

public class NavigationBarBinder implements ActivityBinder {
    public  <T extends ViewBinding> void bind(AppActivityBase<T> view) {
        ImageButton homeBtn = view.findViewById(R.id.nav_home);
        ImageButton locationBtn = view.findViewById(R.id.nav_location);
        ImageButton settingsBtn = view.findViewById(R.id.nav_settings);
        ConstraintLayout nav = view.findViewById(R.id.navigationBar);

        if (homeBtn == null || locationBtn == null || settingsBtn == null || nav == null) {
            Log.w("Navbar", "Couldn't bind to navbar");
            return;
        }

        homeBtn.setOnClickListener(v -> {
            if (view instanceof MainActivity) return;

            Intent intent = new Intent(view, MainActivity.class);
            view.startActivity(intent);
        });

        settingsBtn.setOnClickListener(v -> {
            if (view instanceof SettingsActivity) return;

            Intent intent = new Intent(view, SettingsActivity.class);
            view.startActivity(intent);
        });

        locationBtn.setOnClickListener(v -> {
            String[] locationPerms = ProductLocationListener.REQUIRED_PERMISSIONS;
            view.requestPermissions(locationPerms, 2);
        });
    }
}
