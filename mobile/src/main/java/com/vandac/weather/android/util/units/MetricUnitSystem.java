package com.vandac.weather.android.util.units;

/**
 * Created by Vanda on 8. 4. 2015.
 */
public class MetricUnitSystem extends UnitSystem
{
    public MetricUnitSystem()
    {
        speedUnit    = "m/s";
        tempUnit     = degree + "C";
        precipUnit   = "mm";
        pressureUnit = "hPa";
        name         = "metric";
    }

}
