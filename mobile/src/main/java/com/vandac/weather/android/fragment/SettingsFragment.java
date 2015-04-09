package com.vandac.weather.android.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.vandac.weather.android.R;

/**
 * Created by Vanda on 8. 4. 2015.
 */

public class SettingsFragment extends PreferenceFragment
{
    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);
    }
}
