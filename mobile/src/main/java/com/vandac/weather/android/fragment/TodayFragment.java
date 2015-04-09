package com.vandac.weather.android.fragment;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vandac.weather.android.R;
import com.vandac.weather.android.WeatherApplication;
import com.vandac.weather.android.network.adapter.ApiService;
import com.vandac.weather.android.network.model.currentWeather.ResponseWeather;
import com.vandac.weather.android.util.WeatherLog;
import com.vandac.weather.android.util.WeatherUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Vanda on 31. 3. 2015.
 */
@EFragment(R.layout.fragment_today)
public class TodayFragment extends LocationAwareFragment
{
    @ViewById(R.id.weather_main_icon)
    ImageView mainIconView;

    @ViewById(R.id.today_city)
    TextView cityNameView;

    @ViewById(R.id.today_degrees)
    TextView degressView;

    @ViewById(R.id.today_desc)
    TextView descriptionView;

    @ViewById(R.id.today_humidity)
    TextView humidityView;

    @ViewById(R.id.today_precip)
    TextView precipitationView;

    @ViewById(R.id.today_pressure)
    TextView pressureView;

    @ViewById(R.id.today_wind)
    TextView windSpeedView;

    @ViewById(R.id.today_wind_direction)
    TextView windDirectionView;

    private ResponseWeather lastResponse;

    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        //if location was already found for some reason, don't get it from arguments
        if (location == null)
        {
            location = getArguments().getParcelable(WeatherApplication.LOCATION_KEY);
        }
    }

    @Override public void locationUpdated(Location location)
    {
        WeatherLog.i("[WeatherFragment]::[locationUpdated]::[" + location + "]");

        this.location = location;
        refreshData();
    }

    @AfterViews
    public void setViews()
    {
        hideLoading();

        if (lastResponse == null && location != null)
        {
            refreshData();
        }
        else
        {
            updateViews();
        }
    }

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

    private void downloadData()
    {
        new AsyncTask<Void, Void, ResponseWeather>()
        {
            @Override protected void onPreExecute()
            {
                super.onPreExecute();
                showLoading();
            }

            @Override protected ResponseWeather doInBackground(Void... params)
            {
                try
                {
                    ApiService apiService = (ApiService) getActivity().getApplication().getSystemService("ApiService");
                    ResponseWeather responseWeather = apiService.getWeatherForToday(location.getLatitude(), location.getLongitude(),
                                                                                    WeatherUtil.getPrefUnits(getActivity()));
                    WeatherLog.i("[WeatherFragment]::[refreshData]::[" + responseWeather + "]");
                    return responseWeather;
                }
                catch (Exception e)
                {
                    return null;
                }
            }

            @Override protected void onPostExecute(ResponseWeather responseWeather)
            {
                super.onPostExecute(responseWeather);
                lastResponse = responseWeather;
                updateViews();
            }
        }.execute();
    }

    private void updateViews()
    {
        WeatherLog.i("[WeatherFragment]::[updateViews]::[" + mainIconView + "]::[" + cityNameView + "]::[" + lastResponse + "]");

        if (mainIconView != null && lastResponse != null)
        {
            String iconURL = ApiService.iconEndpoint + lastResponse.getIconCode() + ".png";
            ImageLoader.getInstance().displayImage(iconURL, mainIconView);

            cityNameView.setText(lastResponse.getName() + ", " + lastResponse.getCountry());
            degressView.setText(lastResponse.getTemperature());
            descriptionView.setText(lastResponse.getDescription());
            humidityView.setText(lastResponse.getHumidity());
            precipitationView.setText(lastResponse.getPrecipitation());
            pressureView.setText(lastResponse.getPressure());
            windSpeedView.setText(lastResponse.getWindSpeed());
            windDirectionView.setText(lastResponse.getWindDirection());

            hideLoading();
        }


    }

}
