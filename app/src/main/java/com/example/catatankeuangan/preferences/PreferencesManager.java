package com.example.catatankeuangan.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public PreferencesManager(Context context) {
        pref = context.getSharedPreferences("CatatanKeuangan.pref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void put(String key, Boolean value) {
        editor.putBoolean(key,value)
                .apply();
    }

    public void put(String key, String value) {
        editor.putString(key,value)
                .apply();
    }

    public void put(String key, Integer value) {
        editor.putInt(key,value)
                .apply();
    }

    public Boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public String getString(String key) {
        return pref.getString(key, "");
    }

    public Integer getInt(String key) {
        return pref.getInt(key, 0);
    }

    public void clear() {
        editor.clear()
                .apply();
    }
}
