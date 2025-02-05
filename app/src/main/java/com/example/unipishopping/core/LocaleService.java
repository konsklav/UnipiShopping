package com.example.unipishopping.core;

import android.content.Context;
import android.util.Log;

import androidx.core.os.LocaleListCompat;

import com.example.unipishopping.domain.UserSettings;

/**
 * Package class that provides utilities for getting/setting locales.
 */
class LocaleService {
    private static final String TAG = "Settings Service";

    /**
     * Get the currently used Locale by the application.
     * @return The current locale stored in local storage..
     */
    public static LocaleListCompat getLocale(Context context, UserSettings settings) {
        String languageCode = settings.getLocaleLanguageCode();

        if (languageCode == null) {
            Log.i(TAG, "Didn't find any specified locale. Using default...");
            return LocaleListCompat.getEmptyLocaleList();
        }

        Log.i(TAG, "Found locale '" + languageCode + "'.");
        return LocaleListCompat.forLanguageTags(languageCode);
    }
}
