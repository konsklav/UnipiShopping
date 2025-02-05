package com.example.unipishopping.ui.formats;

import java.util.Locale;

public class NumFormatter {
    public static String formatAsPrice(double number, Locale locale) {
        return String.format(locale, "%.2f", number) + "$";
    }
}
