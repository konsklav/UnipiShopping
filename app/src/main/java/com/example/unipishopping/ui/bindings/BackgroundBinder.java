package com.example.unipishopping.ui.bindings;

import android.util.Log;
import android.view.View;

import androidx.viewbinding.ViewBinding;

import com.example.unipishopping.R;
import com.example.unipishopping.core.settings.SettingsService;
import com.example.unipishopping.domain.UserSettings;
import com.example.unipishopping.ui.AppActivityBase;
import com.example.unipishopping.ui.constants.BackgroundColor;

import java.util.Objects;

public class BackgroundBinder implements Binder {
    @Override
    public <T extends ViewBinding> void bind(AppActivityBase<T> activity) {
        View rootView = activity.findViewById(R.id.main);

        if (rootView == null) {
            Log.e("BG Binding", "Couldn't find root view with ID 'main'.");
            return;
        }

        BackgroundColor color = SettingsService.get(activity).getBackgroundColor();
        rootView.setBackgroundColor(getColorId(color));
    }

    private int getColorId(BackgroundColor color) {
        if (Objects.requireNonNull(color) == BackgroundColor.SECONDARY) {
            return R.color.light_blue;
        }
        return R.color.lilac;
    }
}
