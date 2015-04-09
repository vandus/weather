package com.vandac.weather.android.network.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

/**
 * Created by Vanda on 8. 4. 2015.
 */
public class Temperature
{
    @SerializedName("day")
    private double temperature;

    DecimalFormat df = new DecimalFormat("#.0");

    public String getTemperature()
    {
        return df.format(temperature);
    }

    @Override public String toString()
    {
        return "Temperature{" +
                "temperature=" + temperature +
                '}';
    }
}
