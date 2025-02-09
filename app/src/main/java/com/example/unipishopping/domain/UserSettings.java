package com.example.unipishopping.domain;

import androidx.annotation.Nullable;

import com.example.unipishopping.ui.constants.TextSize;

public class UserSettings {

    @Nullable String firstName;
    @Nullable String lastName;
    @Nullable String localeLanguageCode;
    TextSize textSize = TextSize.MEDIUM;

    public void setFirstName(@Nullable String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    public void setLocaleLanguageCode(@Nullable String localeLanguageCode) {
        this.localeLanguageCode = localeLanguageCode;
    }
    public void setTextSize(TextSize textSize) {
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
    public TextSize getTextSize() { return textSize; }
}
