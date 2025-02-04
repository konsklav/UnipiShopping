package com.example.unipishopping.core;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.core.os.LocaleListCompat;

import com.example.unipishopping.domain.UserSettings;
import com.google.gson.Gson;

public class SettingsService {
    private static final String PREF_KEY = "UniPi-Shopping_SETTINGS";
    public static final String JSON_KEY = "USER";

    public static LocaleListCompat getLocale(Context context) {
        UserSettings settings = get(context);
        if (settings == null) {
            return LocaleListCompat.getEmptyLocaleList();
        }
        return LocaleService.getLocale(context, settings);
    }

    @Nullable
    public static UserSettings get(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        String settingsJson = sp.getString(JSON_KEY, "");

        if (settingsJson.isEmpty()) {
            return null;
        }

        return new Gson().fromJson(settingsJson, UserSettings.class);
    }

    public static void save(UserSettings settings, Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        String settingsJson = new Gson().toJson(settings);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(JSON_KEY, settingsJson);
        editor.apply();
    }
}