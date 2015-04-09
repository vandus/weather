package com.vandac.weather.android.util;

import android.content.Context;
import android.preference.PreferenceManager;

import com.vandac.weather.android.R;
import com.vandac.weather.android.util.units.MetricUnitSystem;

/**
 * Created by Vanda on 8. 4. 2015.
 */
public class WeatherUtil
{
    public static String getPrefUnits(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.prefs_units),new MetricUnitSystem().getName());
    }

}
