package com.vandac.weather.android.network.model.currentWeather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vanda on 6. 4. 2015.
 */
public class Wind
{
    private float speed;

    @SerializedName("deg")
    private float direction;

    public float getDirection()
    {
        return direction;
    }

    public float getSpeed()
    {
        return speed;
    }

    @Override public String toString()
    {
        return "Wind{" +
                "direction=" + direction +
                ", speed=" + speed +
                '}';
    }
}
