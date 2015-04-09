package com.vandac.weather.android.network.model.currentWeather;

/**
 * Created by Vanda on 6. 4. 2015.
 */
public class Sys
{
    private String country;

    public String getCountry()
    {
        return country;
    }

    @Override public String toString()
    {
        return "Sys{" +
                "country='" + country + '\'' +
                '}';
    }
}
