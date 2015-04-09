package com.vandac.weather.android.network.model.currentWeather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vanda on 6. 4. 2015.
 */
public class Rain
{
    @SerializedName("3h")
    private float precip;

    public float getPrecip()
    {
        return precip;
    }

    @Override public String toString()
    {
        return "Rain{" +
                "precip=" + precip +
                '}';
    }
}
