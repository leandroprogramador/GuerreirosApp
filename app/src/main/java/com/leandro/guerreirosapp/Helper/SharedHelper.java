package com.leandro.guerreirosapp.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by leani on 09/11/2017.
 */

public class SharedHelper {

    public static void saveData(Activity activity, String key, String value){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getData(Activity activity, String key){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        String value = sharedPref.getString(key, "");
        return value;
    }

    public static void deleteData(Activity activity, String key){
        SharedPreferences mySPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.remove(key);
        editor.apply();
    }
}
