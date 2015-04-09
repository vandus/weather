package com.vandac.weather.android.network.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vandac.weather.android.R;
import com.vandac.weather.android.network.model.forecast.DailyWeather;

import java.util.List;

/**
 * Created by Vanda on 8. 4. 2015.
 */
public class ForecastAdapter extends ArrayAdapter<DailyWeather>
{

    public ForecastAdapter(Context context, int resource, List<DailyWeather> objects)
    {
        super(context, resource, objects);
    }


    @Override public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_forecast_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.iconView = (ImageView) convertView.findViewById(R.id.weather_main_icon);
            viewHolder.dayName = (TextView) convertView.findViewById(R.id.forecast_dayName);
            viewHolder.temperature = (TextView) convertView.findViewById(R.id.forecast_temperature);
            viewHolder.description = (TextView) convertView.findViewById(R.id.forecast_description);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DailyWeather dailyWeather = (DailyWeather) getItem(position);
        if (dailyWeather != null)
        {
            String iconURL = ApiService.iconEndpoint + dailyWeather.getIconCode() + ".png";

            viewHolder.dayName.setText(dailyWeather.getDayOfWeek());
            viewHolder.description.setText(dailyWeather.getDescription());
            viewHolder.temperature.setText(dailyWeather.getTemperature());

            ImageLoader.getInstance().displayImage(iconURL, viewHolder.iconView);
        }

        return convertView;
    }

    private static class ViewHolder
    {
        private ImageView iconView;
        private TextView  dayName;
        private TextView  temperature;
        private TextView  description;
    }
}
