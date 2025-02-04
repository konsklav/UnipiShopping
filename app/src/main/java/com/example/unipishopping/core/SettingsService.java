package com.example.unipishopping.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.os.LocaleListCompat;

public class SettingsService {

    private static final String PREF_NAME = "UniPi-Shopping_SETTINGS";
    private static final String LANG_KEY = "lang";
    private static final String TAG = "Settings Service";

    /**
     * Get the currently used Locale by the application.
     * @return The current locale stored in local storage..
     */
    public static LocaleListCompat getLocale(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String languageCode = sp.getString(LANG_KEY, null);

        if (languageCode == null) {
            Log.i(TAG, "Didn't find any specified locale. Using default...");
            return LocaleListCompat.getEmptyLocaleList();
        }

        Log.i(TAG, "Found locale '" + languageCode + "'.");
        return LocaleListCompat.forLanguageTags(languageCode);
    }

    /**
     * Saves the desired Locale in local storage.
     * @param languageCode The two letter language code of the locale you want to use.
     *                     Specify NULL if you want to use the default locale.
     */
    public static void setLocale(Context context, @Nullable String languageCode) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(LANG_KEY, languageCode);
        editor.apply();

        Log.i(TAG, "Set application locale to: '" + languageCode + "'.");
    }
}