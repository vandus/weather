package com.vandac.weather.android.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.vandac.weather.android.R;

/**
 * Created by Martin on 8.1.2015.
 */
public class SettingsActivity extends ActionBarActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }
}
