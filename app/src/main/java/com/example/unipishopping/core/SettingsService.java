package com.example.unipishopping.core;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.core.os.LocaleListCompat;

import com.example.unipishopping.domain.UserSettings;
import com.google.gson.Gson;

/**
 * Abstraction for accessing user settings/configuration.
 */
public class SettingsService {
    private static final String PREF_KEY = "UniPi-Shopping_SETTINGS";
    public static final String JSON_KEY = "USER";

    /**
     * Helper extension method that retrieves the user's settings and specifically get the locale used
     * and returns it in an Activity-friendly way.
     * @param context The calling activity's context.
     * @return A list of supported locales (typically only one locale is returned)
     */
    public static LocaleListCompat getLocale(Context context) {
        UserSettings settings = get(context);
        if (settings == null) {
            return LocaleListCompat.getEmptyLocaleList();
        }
        return LocaleService.getLocale(context, settings);
    }

    /**
     * Retrieve the user's settings/configuration from local storage, if they exist.
     * @param context The calling activity's context.
     * @return The UserSettings if they exist, or <b>null</b> if they don't.
     * @apiNote <b>Null</b> will be returned if the UserSettings have never been saved on the
     *          local machine.
     */
    @Nullable
    public static UserSettings get(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        String settingsJson = sp.getString(JSON_KEY, "");

        if (settingsJson.isEmpty()) {
            return null;
        }

        return new Gson().fromJson(settingsJson, UserSettings.class);
    }

    /**
     * Saves the given UserSettings in local storage.
     * @param settings The instance that will be persisted.
     * @param context The calling activity's context.
     */
    public static void save(UserSettings settings, Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        String settingsJson = new Gson().toJson(settings);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(JSON_KEY, settingsJson);
        editor.apply();
    }
}