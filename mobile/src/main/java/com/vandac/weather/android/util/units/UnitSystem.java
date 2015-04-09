package com.vandac.weather.android.util.units;

import android.content.Context;
import android.util.Log;

import com.vandac.weather.android.util.WeatherUtil;

/**
 * Created by Vanda on 8. 4. 2015.
 */
public abstract class UnitSystem
{
    protected String speedUnit;
    protected String tempUnit;
    protected String precipUnit;
    protected String pressureUnit;
    protected char degree = 0x00B0;
    protected String name;

    private static UnitSystem sInstance;

    public static void init(Context context)
    {
        if (sInstance == null)
        {
            String name = WeatherUtil.getPrefUnits(context);
            Log.i("UnitSystem", "[Unitsystem]::[init]::[" + name + "]");

            if ("metric".equals(name.toLowerCase()))
            {
                sInstance = new MetricUnitSystem();
            }
            if ("imperial".equals(name.toLowerCase()))
            {
                sInstance = new ImperialUnitSystem();
            }
        }
    }

    public static void restart(Context context)
    {
        sInstance = null;
        init(context);
    }

    public static UnitSystem getInstance()
    {
        return sInstance;
    }

    public String getPrecipUnit()
    {
        return precipUnit;
    }

    public String getPressureUnit()
    {
        return pressureUnit;
    }

    public String getSpeedUnit()
    {
        return speedUnit;
    }

    public String getTempUnit()
    {
        return tempUnit;
    }

    public String getName()
    {
        return name;
    }
}


