package com.vandac.weather.android.network.adapter;

import com.vandac.weather.android.network.model.currentWeather.ResponseWeather;
import com.vandac.weather.android.network.model.forecast.ResponseForecast;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Vanda on 6. 4. 2015.
 */
public interface ApiService
{
    public static String endpoint = "http://api.openweathermap.org/data/2.5";

    public static String iconEndpoint = "http://openweathermap.org/img/w/";

    @GET("/weather") ResponseWeather getWeatherForToday(@Query("lat") double lat, @Query("lon") double lng,@Query("units")
    String units);

    @GET("/forecast/daily?mode=json&cnt=10") ResponseForecast getWeatherForecast(@Query("lat") double lat, @Query("lon") double lng,@Query("units")
    String units);

}
