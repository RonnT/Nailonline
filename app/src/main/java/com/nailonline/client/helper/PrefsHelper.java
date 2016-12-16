package com.nailonline.client.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.nailonline.client.App;

/**
 * Created by Roman T. on 14.12.2016.
 */

public class PrefsHelper {

    private static class SingletonHolder {
        private static PrefsHelper INSTANCE;
    }

    public static PrefsHelper getInstance() {
        if (SingletonHolder.INSTANCE == null) {
            SingletonHolder.INSTANCE = new PrefsHelper(App.getAppContext());
        }
        return SingletonHolder.INSTANCE;
    }

    private static final String
            PREFS_NAME = "com.nailonline.client.prefs",

            PREF_FREE_TOKEN = "PREF_FREE_TOKEN",
            PREF_USER_THEME = "PREF_USER_THEME";

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private PrefsHelper(Context pContext) {
        mPrefs = pContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private void putString(String name, String value) {
        mPrefs.edit().putString(name, value).apply();
    }

    private void putLong(String name, long value) {
        mPrefs.edit().putLong(name, value).apply();
    }

    private void putBoolean(String name, boolean value) {
        mPrefs.edit().putBoolean(name, value).apply();
    }

    private void putInt(String name, int value) {
        mPrefs.edit().putInt(name, value).apply();
    }

    private boolean getBoolean(String pKey, boolean pDefValue) {
        return mPrefs.getBoolean(pKey, pDefValue);
    }

    private int getInt(String pKey, int pDefValue) {
        return mPrefs.getInt(pKey, pDefValue);
    }

    private String getString(String pKey, String pDefValue) {
        return mPrefs.getString(pKey, pDefValue);
    }

    private long getLong(String pKey, long pDefValue) {
        return mPrefs.getLong(pKey, pDefValue);
    }

    public void setFreeToken(String token){
        putString(PREF_FREE_TOKEN, token);
    }

    public String getFreeToken(){
        return getString(PREF_FREE_TOKEN, "");
    }

    public void setUserThemeId(int id){
        putInt(PREF_USER_THEME, id);
    }

    public int getUserThemeId(){
        return mPrefs.getInt(PREF_USER_THEME, -1);
    }
}
