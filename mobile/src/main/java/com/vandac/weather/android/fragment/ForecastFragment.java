package com.vandac.weather.android.fragment;


import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.vandac.weather.android.R;
import com.vandac.weather.android.WeatherApplication;
import com.vandac.weather.android.network.adapter.ApiService;
import com.vandac.weather.android.network.adapter.ForecastAdapter;
import com.vandac.weather.android.network.model.forecast.ResponseForecast;
import com.vandac.weather.android.util.WeatherLog;
import com.vandac.weather.android.util.WeatherUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Vanda on 31. 3. 2015.
 */
@EFragment(R.layout.fragment_forecast)
public class ForecastFragment extends LocationAwareFragment
{
    @ViewById(R.id.forecastList)
    ListView forecastList;

    @ViewById(R.id.loading_overlay)
    View loadingOverlay;

    private ResponseForecast lastResponse;

    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        //if location was already found for some reason, don't get it from arguments
        if (location == null)
        {
            location = getArguments().getParcelable(WeatherApplication.LOCATION_KEY);
        }

        WeatherLog.i("[ForecastFragment]::[onCreate]");
    }

    @Override public void locationUpdated(Location location)
    {
        WeatherLog.i("[ForecastFragment]::[locationUpdated]::[" + location + "]");

        this.location = location;
        refreshData();
    }

    @AfterViews void setViews()
    {
        Log.i("ForecastFragment",
              "[ForecastFragment]::[setViews]");

        //if data wasn't yet downloaded, refresh
        if (lastResponse == null && location != null)
        {
            refreshData();
        }
        else
        {
            //if data was already downloaded, just update the view
            updateViews();
        }
    }

    /**
     * First checks the network state. If the device is connected, downloads the data, otherwise shows loading
     */
    protected void refreshData()
    {
        if (!isConnected())
        {
            waitForNetwork();
        }
        else
        {
            downloadData();
        }
    }

    /**
     * Downloads data only if location is known. Fix of a case when location is not yet known during fragment creation
     */
    private void downloadData()
    {
        new AsyncTask<Void, Void, ResponseForecast>()
        {
            @Override protected ResponseForecast doInBackground(Void... params)
            {
                ApiService apiService = (ApiService) getActivity().getApplication().getSystemService("ApiService");
                ResponseForecast responseForecast = apiService.getWeatherForecast(location.getLatitude(), location.getLongitude(),
                                                                                  WeatherUtil.getPrefUnits(getActivity()));
                WeatherLog.i("[ForecastFragment]::[refreshData]::[" + responseForecast + "]");
                return responseForecast;
            }

            @Override protected void onPostExecute(ResponseForecast responseForecast)
            {
                super.onPostExecute(responseForecast);
                lastResponse = responseForecast;

                //since data download is asynchronous, the fragment may not longer be visible when asynctask is done
                if (isVisible())
                {
                    updateViews();
                }
            }
        }.execute();
    }

    private void updateViews()
    {
        WeatherLog.i("[ForecastFragment]::[updateViews]::[" + lastResponse + "]");

        if (forecastList != null && lastResponse != null)
        {
            ForecastAdapter forecastAdapter = new ForecastAdapter(getActivity(), R.layout.item_forecast_list, lastResponse.getList());
            forecastList.setAdapter(forecastAdapter);

            loadingOverlay.setVisibility(View.INVISIBLE);
        }
    }

    @UiThread
    public void doSomethingInBackground()
    {
    }
}
