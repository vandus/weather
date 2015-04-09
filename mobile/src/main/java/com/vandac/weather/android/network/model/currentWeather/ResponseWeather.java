package com.vandac.weather.android.network.model.currentWeather;

import com.google.gson.annotations.SerializedName;
import com.vandac.weather.android.util.units.UnitSystem;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vanda on 6. 4. 2015.
 */
public class ResponseWeather
{

    private String             name;
    private int                cod;
    private long               id;
    private Rain               rain;
    private Wind               wind;
    private WeatherMain        main;
    @SerializedName("weather")
    private ArrayList<Weather> weathers;
    private Sys                sys;
    private UnitSystem         unitSystem;

    public String getName()
    {
        return name;
    }

    public String getPrecipitation()
    {
        if (rain == null)
        {
            return "0.0 "+UnitSystem.getInstance().getPrecipUnit();
        }
        return rain.getPrecip() + " " + UnitSystem.getInstance().getPrecipUnit();
    }

    public String getWindSpeed()
    {
        if (wind == null)
        {
            return "? "+UnitSystem.getInstance().getSpeedUnit();
        }
        return wind.getSpeed() + " " + UnitSystem.getInstance().getSpeedUnit();
    }

    public String getWindDirection()
    {
        if (wind == null)
        {
            return "";
        }

        double direction = wind.getDirection();
        double part = 22.5;
        if (direction <= 1 * part || direction > 15 * part)
        {
            return "N";
        }
        if (direction > 1 * part && direction <= 3 * part)
        {
            return "NW";
        }
        if (direction > 3 * part && direction <= 5 * part)
        {
            return "W";
        }
        if (direction > 5 * part && direction <= 7 * part)
        {
            return "SW";
        }
        if (direction > 7 * part && direction <= 9 * part)
        {
            return "S";
        }
        if (direction > 9 * part && direction <= 11 * part)
        {
            return "SE";
        }
        if (direction > 11 * part && direction <= 13 * part)
        {
            return "E";
        }
        if (direction > 13 * part && direction <= 15 * part)
        {
            return "NE";
        }
        return "";
    }

    public String getTemperature()
    {

        if (main == null)
        {
            return "?" + UnitSystem.getInstance().getTempUnit();
        }

        return main.getFormattedTemp() + UnitSystem.getInstance().getTempUnit();
    }

    public String getPressure()
    {
        if (main == null)
        {
            return "?" + UnitSystem.getInstance().getPressureUnit();
        }
        return main.getPressure() + UnitSystem.getInstance().getPressureUnit();
    }

    public String getHumidity()
    {
        if (main == null)
        {
            return "?" + "%";
        }
        return main.getHumidity() + "%";
    }

    public String getIconCode()
    {
        if (weathers == null | weathers.isEmpty())
        {
            return "";
        }
        return weathers.get(0).getIcon();
    }

    public String getDescription()
    {
        if (weathers == null | weathers.isEmpty())
        {
            return "";
        }
        return weathers.get(0).getDescription();
    }

    public String getCountry()
    {
        try
        {
            String countryCode = sys.getCountry();
            Locale locale = new Locale("", countryCode);
            return locale.getDisplayCountry();
        }
        catch (Exception e)
        {
            return "";
        }
    }

    @Override public String toString()
    {
        return "ResponseWeather{" +
                "id=" + id +
                ", main=" + main +
                ", name='" + name + '\'' +
                ", rain=" + rain +
                ", sys=" + sys +
                ", weathers=" + weathers +
                ", wind=" + wind +
                '}';
    }
}
