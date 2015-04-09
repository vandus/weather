package com.vandac.weather.android.network.model.currentWeather;

/**
 * Created by Vanda on 6. 4. 2015.
 */
public class Weather
{
    private long id;
    private String main;
    private String description;
    private String icon;

    public String getMain()
    {
        return main;
    }

    public void setMain(String main)
    {
        this.main = main;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Override public String toString()
    {
        return "Weather{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", main='" + main + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
