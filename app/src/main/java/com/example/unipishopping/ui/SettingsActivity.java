package com.example.unipishopping.ui;

import com.example.unipishopping.core.session.UserSession;
import com.example.unipishopping.core.settings.SettingsService;
import com.example.unipishopping.databinding.ActivitySettingsBinding;
import com.example.unipishopping.domain.User;
import com.example.unipishopping.domain.UserSettings;

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

    private void changeFontSize(int size) {
        recreate();
    }

    private void changeBackground() {
        recreate();
    }

    private void changeLocale(String languageCode) {
        UserSettings settings = SettingsService.get(this);
        settings.setLocaleLanguageCode(languageCode);
        SettingsService.save(settings, this);

        recreate();
    }
}