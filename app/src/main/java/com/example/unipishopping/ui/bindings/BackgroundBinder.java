package com.example.unipishopping.ui.bindings;

import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.unipishopping.R;
import com.example.unipishopping.core.settings.SettingsService;
import com.example.unipishopping.ui.constants.BackgroundColor;

import java.util.Objects;

public class BackgroundBinder implements ViewBinder {

    @Override
    public void bind(View view) {
        View rootView = getBackgroundView(view);

        if (rootView == null) {
            Log.e("BG Binding", "Couldn't find root view with ID 'main'.");
            return;
        }

        BackgroundColor color = SettingsService.get(view.getContext()).getBackgroundColor();
        int colorInt = view.getContext().getColor(getColorId(color));
        rootView.setBackgroundColor(colorInt);
    }

    @Nullable
    public View getBackgroundView(View view) {
        return view.findViewById(R.id.main);
    }

    private int getColorId(BackgroundColor color) {
        if (Objects.requireNonNull(color) == BackgroundColor.SECONDARY) {
            return R.color.light_blue;
        }
        return R.color.lilac;
    }
}
