package com.example.unipishopping.domain;

import androidx.annotation.Nullable;

import com.example.unipishopping.ui.constants.BackgroundColor;
import com.example.unipishopping.ui.constants.TextSize;

public class UserSettings {

    @Nullable String username;
    @Nullable String password;
    @Nullable String localeLanguageCode;

    TextSize textSize = TextSize.MEDIUM;
    BackgroundColor bgColor = BackgroundColor.PRIMARY;

    public void setUsername(@Nullable String username) {
        this.username = username;
    }
    public void setPassword(@Nullable String password) {
        this.password = password;
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
    @Nullable public String getUsername() { return username; }
    @Nullable public String getPassword() { return password; }
}
