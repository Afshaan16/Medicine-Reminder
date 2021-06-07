package com.mhss.gomed.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String PREFERENCES_NAME = "Go-Med";

    public static final String KEY_IS_LOGIN = "is_login";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_C_MOBILE = "c_mobile";
    public static final String KEY_C_EMAIL = "c_email";
    public static final String KEY_OTP = "OTP";
    public static final String KEY_IMAGE = "image";

    public static void setPreferenceValue(Context context, String key,
                                          String value) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static String getPreferenceValue(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        return prefs.getString(key, null);

    }

    public static void setPreferenceValue(Context context, String key, int value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getPreferenceValueInt(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        return prefs.getInt(key, 0);

    }
}
