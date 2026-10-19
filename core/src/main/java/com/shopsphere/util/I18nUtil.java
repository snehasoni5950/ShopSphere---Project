package com.shopsphere.util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * RTU Syllabus: Internationalization (Locale, ResourceBundle, formatted values).
 */
public class I18nUtil {

    public static ResourceBundle getBundle(String lang) {
        Locale locale = "hi".equalsIgnoreCase(lang) ? new Locale("hi") : Locale.ENGLISH;
        return ResourceBundle.getBundle("messages", locale);
    }

    public static String getMessage(String key, String lang) {
        try {
            ResourceBundle bundle = getBundle(lang);
            return bundle.getString(key);
        } catch (Exception e) {
            return key;
        }
    }

    public static String formatCurrency(double amount, String lang) {
        Locale locale = "hi".equalsIgnoreCase(lang) ? new Locale("hi", "IN") : new Locale("en", "IN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        try {
            return nf.format(amount);
        } catch (Exception e) {
            return "₹" + String.format("%.2f", amount);
        }
    }
}
