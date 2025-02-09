package com.example.unipishopping.ui;

import com.example.unipishopping.core.session.UserSession;
import com.example.unipishopping.core.settings.SettingsService;
import com.example.unipishopping.databinding.ActivitySettingsBinding;
import com.example.unipishopping.domain.User;
import com.example.unipishopping.domain.UserSettings;
import com.example.unipishopping.ui.constants.BackgroundColor;
import com.example.unipishopping.ui.constants.TextSize;

public class SettingsActivity extends AppActivityBase<ActivitySettingsBinding> {
    @Override
    protected void onAfterCreate() {
        getBinding().btnEnglish.setOnClickListener(v -> changeLocale("en"));
        getBinding().btnGreek.setOnClickListener(v -> changeLocale("el"));
        getBinding().btnSpanish.setOnClickListener(v -> changeLocale("es"));

        User user = UserSession.getInstance().getUser();
        getBinding().tvUsername.setText(user.getUsername());
        getBinding().tvPassword.setText(user.getPassword());
    }

    private void changeFontSize(TextSize size) {
        UserSettings settings = SettingsService.get(this);
        settings.setTextSize(size);
        saveSettings(settings);
    }

    private void changeBackground(BackgroundColor color) {
        UserSettings settings = SettingsService.get(this);
        settings.setBackgroundColor(color);
        saveSettings(settings);
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