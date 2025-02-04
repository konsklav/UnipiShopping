package com.example.unipishopping.domain;

import androidx.annotation.Nullable;

public class UserSettings {

    @Nullable String firstName;
    @Nullable String lastName;

    @Nullable String localeLanguageCode;

    public UserSettings(
            @Nullable String firstName,
            @Nullable String lastName,
            @Nullable String localeLanguageCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.localeLanguageCode = localeLanguageCode;
    }

    @Nullable
    public String getLocaleLanguageCode() {
        return localeLanguageCode;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }
}
