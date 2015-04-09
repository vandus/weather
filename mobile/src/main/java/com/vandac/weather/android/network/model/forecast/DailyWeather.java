package com.vandac.weather.android.network.model.forecast;

import com.google.gson.annotations.SerializedName;
import com.vandac.weather.android.util.units.UnitSystem;
import com.vandac.weather.android.network.model.currentWeather.Weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vanda on 6. 4. 2015.
 */
public class DailyWeather
{

    @SerializedName("weather")
    private ArrayList<Weather> weathers;

    @SerializedName("dt")
    private long date;

    private Temperature temp;

    public String getDayOfWeek()
    {
        Date simpleDate = new Date(date * 1000);
        return new SimpleDateFormat("EEEE", Locale.UK).format(simpleDate);
    }

    public String getTemperature()
    {
        char degree = 0x00B0;

        if (temp == null)
        {
            return "?" + UnitSystem.getInstance().getTempUnit();
        }

        return temp.getTemperature()+UnitSystem.getInstance().getTempUnit();
    }

    public String getDescription()
    {
        if (weathers == null | weathers.isEmpty())
        {
            return "";
        }
        return weathers.get(0).getDescription();
    }

    public String getIconCode()
    {
        if (weathers == null | weathers.isEmpty())
        {
            return "";
        }
        return weathers.get(0).getIcon();
    }

    @Override public String toString()
    {
        return "DailyWeather{" +
                "date='" + date + '\'' +
                ", weathers=" + weathers +
                ", temp=" + temp +
                '}';
    }
}
