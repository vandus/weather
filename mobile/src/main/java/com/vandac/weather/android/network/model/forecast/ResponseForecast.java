package com.vandac.weather.android.network.model.forecast;

import java.util.ArrayList;

/**
 * Created by Vanda on 6. 4. 2015.
 */
public class ResponseForecast
{
    private ArrayList<DailyWeather> list;

    public ArrayList<DailyWeather> getList()
    {
        return list;
    }

    @Override public String toString()
    {
        return "ResponseForecast{" +
                "list=" + list +
                '}';
    }
}
