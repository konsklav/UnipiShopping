package com.example.unipishopping.ui;

import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.unipishopping.R;
import com.example.unipishopping.core.session.UserSession;
import com.example.unipishopping.core.settings.SettingsService;
import com.example.unipishopping.databinding.ActivitySettingsBinding;
import com.example.unipishopping.domain.User;
import com.example.unipishopping.domain.UserSettings;
import com.example.unipishopping.ui.bindings.BackgroundBinder;
import com.example.unipishopping.ui.constants.BackgroundColor;
import com.example.unipishopping.ui.constants.TextSize;

import java.util.function.Consumer;

public class SettingsActivity extends AppActivityBase<ActivitySettingsBinding> {
    @Override
    protected void onAfterCreate() {
        getBinding().btnEnglish.setOnClickListener(v -> changeLocale("en"));
        getBinding().btnGreek.setOnClickListener(v -> changeLocale("el"));
        getBinding().btnSpanish.setOnClickListener(v -> changeLocale("es"));

        User user = UserSession.getInstance().getUser();
        getBinding().tvUsername.setText(user.getUsername());
        getBinding().tvPassword.setText(user.getPassword());

        getBinding().btnSmallFont.setOnClickListener(v -> changeFontSize(TextSize.SMALL));
        getBinding().btnNormalFont.setOnClickListener(v -> changeFontSize(TextSize.MEDIUM));
        getBinding().btnBigFont.setOnClickListener(v -> changeFontSize(TextSize.BIG));

        Button bgButton = getBinding().btnChangeBackground;
        bgButton.setOnClickListener(v -> toggleBackgroundColor());
        actOnBackgroundColor(color -> {
            if (color == BackgroundColor.PRIMARY) {
                bgButton.setBackgroundColor(getColor(R.color.light_blue));
                bgButton.setText(R.string.color_btn_secondary);
            }
            else if (color == BackgroundColor.SECONDARY) {
                bgButton.setBackgroundColor(getColor(R.color.lilac));
                bgButton.setText(R.string.color_btn_primary);
            }
        });
    }

    private void changeFontSize(TextSize size) {
        UserSettings settings = SettingsService.get(this);
        settings.setTextSize(size);
        saveSettings(settings);
    }

    private void toggleBackgroundColor() {
        actOnBackgroundColor(color -> {
            UserSettings settings = SettingsService.get(this);
            settings.setBackgroundColor(color == BackgroundColor.PRIMARY
                    ? BackgroundColor.SECONDARY
                    : BackgroundColor.PRIMARY);
            saveSettings(settings);
        });
    }

    private void actOnBackgroundColor(Consumer<BackgroundColor> colorAction) {
        View bgView = new BackgroundBinder().getBackgroundView(getBinding().getRoot());
        if (bgView == null) {
            Log.e("BG Binding", "No 'main' view found.");
            return;
        }

        if (!(bgView.getBackground() instanceof ColorDrawable)) {
            Log.e("BG Binding", "'Main' Background needs to be a color!");
            return;
        }

        int currentColor = ((ColorDrawable) bgView.getBackground()).getColor();
        if (currentColor == getColor(R.color.lilac)) {
            colorAction.accept(BackgroundColor.PRIMARY);
            return;
        }
        if (currentColor == getColor(R.color.light_blue)) {
            colorAction.accept(BackgroundColor.SECONDARY);
        }
    }

    private void changeLocale(String languageCode) {
        UserSettings settings = SettingsService.get(this);
        settings.setLocaleLanguageCode(languageCode);
        saveSettings(settings);
    }

    private void saveSettings(UserSettings settings) {
        SettingsService.save(settings, this);
        recreate();
    }
}