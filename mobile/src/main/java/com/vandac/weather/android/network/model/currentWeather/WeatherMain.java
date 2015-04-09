package com.vandac.weather.android.network.model.currentWeather;

import java.text.DecimalFormat;

/**
 * Created by Vanda on 6. 4. 2015.
 */
public class WeatherMain
{
    private int    humidity;
    private float  pressure;
    private double temp;

    DecimalFormat df = new DecimalFormat("#.0");

    public int getHumidity()
    {
        return humidity;
    }

    public float getPressure()
    {
        return pressure;
    }

    /**
     * Returns temperature in degrees of Celcius, rounded to two decimal places
     * @return
     */
    public String getFormattedTemp()
    {
        return df.format(temp);
    }

    public double getTemp()
    {
        return temp;
    }

    @Override public String toString()
    {
        return "WeatherMain{" +
                "humidity=" + humidity +
                ", pressure=" + pressure +
                ", temp=" + temp +
                '}';
    }
}
