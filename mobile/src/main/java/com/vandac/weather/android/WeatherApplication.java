package com.vandac.weather.android;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.vandac.weather.android.network.adapter.ApiService;
import com.vandac.weather.android.util.units.UnitSystem;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Vanda on 28. 3. 2015.
 */
public class WeatherApplication extends Application
{
    public static final String LOCATION_KEY = "LOCATION";

    private ApiService apiService;

    @Override public void onCreate()
    {
        super.onCreate();

        UnitSystem.init(this);

        initImageLoader();
        initApiService();
    }

    @Override public Object getSystemService(String name)
    {
        if ("ApiService".equals(name))
        {
            return apiService;
        }

        return super.getSystemService(name);
    }

    private void initImageLoader()
    {
        DisplayImageOptions sDefaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageOnFail(android.R.drawable.stat_notify_error)
                .showImageOnLoading(R.drawable.overlay_weather_status_today)
                .showImageForEmptyUri(android.R.drawable.stat_notify_error)
                .displayer(new FadeInBitmapDisplayer(350))
                .build();

        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
                .diskCacheSize(10*1024)
                .defaultDisplayImageOptions(sDefaultOptions)
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }

    private void initApiService()
    {
        Executor executor = Executors.newCachedThreadPool();
        OkHttpClient okHttpClient = new OkHttpClient();
        File cacheDir = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
        Cache cache = new Cache(cacheDir, 1024);
        okHttpClient.setCache(cache);

        RestAdapter restAdapter = new RestAdapter.Builder().setExecutors(executor,executor).setClient(new OkClient(okHttpClient)).setEndpoint(
                ApiService.endpoint).build();

        apiService = restAdapter.create(ApiService.class);
    }
}
