package com.leandro.guerreirosapp.Helper;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;

import org.aviran.cookiebar2.CookieBar;
import org.aviran.cookiebar2.OnActionClickListener;

/**
 * Created by leandro.araujo on 11/01/2018.
 */

public class CookieHelper {
    public static void createCookieToast(Activity activity, String title, String message, String action, int icon, int color){
        CookieBar.build(activity)
                .setTitle(title)
                .setLayoutGravity(Gravity.BOTTOM)
                .setIcon(icon)
                .setMessage(message)
                .setAction(action, new OnActionClickListener() {
                    @Override
                    public void onClick() {
                    }
                })
                .setBackgroundColor(color)
                .show();
    }
}
