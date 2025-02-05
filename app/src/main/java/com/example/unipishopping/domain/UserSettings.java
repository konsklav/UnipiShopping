package com.example.unipishopping.domain;

import androidx.annotation.Nullable;

public class UserSettings {

    public static final int TEXT_SIZE_SMALL = 0;
    public static final int TEXT_SIZE_MEDIUM = 1;
    public static final int TEXT_SIZE_LARGE = 2;

    @Nullable String firstName;
    @Nullable String lastName;
    @Nullable String localeLanguageCode;
    int textSize = TEXT_SIZE_MEDIUM;

    public void setFirstName(@Nullable String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    public void setLocaleLanguageCode(@Nullable String localeLanguageCode) {
        this.localeLanguageCode = localeLanguageCode;
    }
    public void setTextSize(int textSize) {
        this.textSize = textSize;
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
    public int getTextSize() { return textSize; }
}
