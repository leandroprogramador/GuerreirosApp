package com.leandro.guerreirosapp.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by leani on 09/11/2017.
 */

public class SharedHelper {

    public static void saveData(Activity context, String key, String value){
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getData(Activity activity, String key){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, "");
        return value;
    }

    public static void deleteData(Activity activity, String key){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit().clear();
        editor.commit();
    }
}
