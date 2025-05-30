package com.example.unipishopping.ui;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewbinding.ViewBinding;

import com.example.unipishopping.R;
import com.example.unipishopping.core.notifications.NotificationService;
import com.example.unipishopping.core.products.ProductLocationListener;
import com.example.unipishopping.core.settings.SettingsService;
import com.example.unipishopping.ui.bindings.BackgroundBinder;
import com.example.unipishopping.ui.bindings.FontSizeBinder;
import com.example.unipishopping.ui.bindings.NavigationBarBinder;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public abstract class AppActivityBase<TBinding extends ViewBinding> extends AppCompatActivity {
    private static boolean permissionsRequested = false;

    private TBinding binding;
    private Locale locale;
    protected TBinding getBinding() { return binding; }
    protected Locale getLocale() { return locale; }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Inflate the ViewBinding so that all Views (Buttons, TextViews, etc...) are initialized.
        binding = inflateBinding();
        View root = binding.getRoot();
        setContentView(root);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the Locale from storage and refresh the Activity if it's not the default
        LocaleListCompat appLocale = SettingsService.getLocale(this);
        locale = appLocale.isEmpty() ? Locale.getDefault() : appLocale.get(0);
        AppCompatDelegate.setApplicationLocales(appLocale);

        // Get all the required permissions and request the user's permissions.
        if (!permissionsRequested) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ArrayList<String> perms = new ArrayList<>();
                perms.add(NotificationService.REQUIRED_PERMISSIONS);
                Collections.addAll(perms, ProductLocationListener.REQUIRED_PERMISSIONS);

                requestPermissions(perms.toArray(new String[]{}), 1);
            }
        }

        new NavigationBarBinder().bind(this);
        new FontSizeBinder().bind(root);
        new BackgroundBinder().bind(root);

        // Subclasses (Activities that inherit from this) will have their code run here!
        onAfterCreate();
    }

    /**
     * Use reflection to dynamically invoke ViewBinding.inflate(getLayoutInflater()) and
     * avoid having to declare an abstract method for this.
     */
    private TBinding inflateBinding() {
        try {
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<TBinding> bindingClass = (Class<TBinding>) superClass.getActualTypeArguments()[0];

            Method inflateMethod = bindingClass.getMethod("inflate", LayoutInflater.class);
            return (TBinding) inflateMethod.invoke(null, getLayoutInflater());
        } catch (Exception e) {
            throw new RuntimeException("Failed to inflate ViewBinding in " + getClass().getSimpleName(), e);
        }
    }

    /**
     * We override this method to declare that permissions have been requested ONCE.
     * We shouldn't let the application keep sending permission requests to user, or else they'll
     * get 😡 angry!
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            String result = grantResults[i] == PackageManager.PERMISSION_GRANTED
                    ? "GRANTED" : "DENIED";

            Log.i("UniPiShopping Permissions", "Permission: '" + permission + "' " + result);
        }

        permissionsRequested = true;
    }

    private boolean hasLocationPermissions() {
        return Arrays
                .stream(ProductLocationListener.REQUIRED_PERMISSIONS)
                .anyMatch(p -> checkSelfPermission(p) == PackageManager.PERMISSION_DENIED);
    }

    /**
     * Called immediately after the regular onCreate method finishes setting up bindings, locales, etc...
     */
    protected abstract void onAfterCreate();
}