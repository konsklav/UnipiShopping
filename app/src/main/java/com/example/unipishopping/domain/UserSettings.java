package com.example.unipishopping.domain;

import androidx.annotation.Nullable;

import com.example.unipishopping.ui.constants.BackgroundColor;
import com.example.unipishopping.ui.constants.TextSize;

public class UserSettings {

    @Nullable String firstName;
    @Nullable String lastName;
    @Nullable String localeLanguageCode;

    TextSize textSize = TextSize.MEDIUM;
    BackgroundColor bgColor = BackgroundColor.PRIMARY;

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
    public void setBackgroundColor(BackgroundColor color) {
        this.bgColor = color;
    }

    public TextSize getTextSize() {
        if (textSize == null)
            return TextSize.MEDIUM;

        return textSize;
    }
    public BackgroundColor getBackgroundColor() {
        if (bgColor == null)
            return BackgroundColor.PRIMARY;
        return bgColor;
    }
    @Nullable public String getLocaleLanguageCode() { return localeLanguageCode; }
    @Nullable public String getFirstName() { return firstName; }
    @Nullable public String getLastName() { return lastName; }
}
