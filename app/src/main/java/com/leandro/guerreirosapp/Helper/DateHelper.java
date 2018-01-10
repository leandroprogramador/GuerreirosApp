package com.leandro.guerreirosapp.Helper;

import java.util.Calendar;

/**
 * Created by leandro.araujo on 10/01/2018.
 */

public class DateHelper {

    public static String timeStampToBr(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return String.format("%02d/%02d/%04d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,  calendar.get(Calendar.YEAR));
    }
}
