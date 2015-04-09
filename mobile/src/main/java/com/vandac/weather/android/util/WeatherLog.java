package com.vandac.weather.android.util;

import android.util.Log;

/**
 * Created by Vanda on 9. 4. 2015.
 */
public class WeatherLog
{
    private static final boolean LOGGING = false;
    private static final String  TAG     = "Weather";

    public static void i(String msg)
    {
        if (LOGGING)
        {
            Log.i(TAG, msg);
        }
    }

    public static void e(String msg)
    {
        if (LOGGING)
        {
            Log.e(TAG, msg);
        }
    }

    public static void w(String msg)
    {
        if (LOGGING)
        {
            Log.w(TAG,msg);
        }
    }
}
