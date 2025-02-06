package com.example.unipishopping.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

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
import com.example.unipishopping.core.SettingsService;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
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
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the Locale from storage and refresh the Activity if it's not the default
        LocaleListCompat appLocale = SettingsService.getLocale(this);
        locale = appLocale.isEmpty() ? Locale.getDefault() : appLocale.get(0);

        AppCompatDelegate.setApplicationLocales(appLocale);

        if (!permissionsRequested) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                String[] perms = new String[] { Manifest.permission.POST_NOTIFICATIONS };
                requestPermissions(perms, 1);
            }
        }

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

    /**
     * Called immediately after the regular onCreate method finishes setting up bindings, locales, etc...
     */
    protected abstract void onAfterCreate();
}