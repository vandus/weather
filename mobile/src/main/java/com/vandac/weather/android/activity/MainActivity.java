package com.vandac.weather.android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.vandac.weather.android.R;
import com.vandac.weather.android.WeatherApplication;
import com.vandac.weather.android.fragment.ForecastFragment_;
import com.vandac.weather.android.fragment.LocationAwareFragment;
import com.vandac.weather.android.fragment.NavigationDrawerFragment;
import com.vandac.weather.android.fragment.TodayFragment_;
import com.vandac.weather.android.util.WeatherLog;
import com.vandac.weather.android.util.units.UnitSystem;

import org.androidannotations.annotations.EActivity;

import java.util.List;

@EActivity
public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    private Location        mLastLocation;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        UnitSystem.restart(this);

        if (checkPlayServices())
        {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if (savedInstanceState != null)
        {
            mLastLocation = savedInstanceState.getParcelable(WeatherApplication.LOCATION_KEY);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        checkPlayServices();
        WeatherLog.i("[MainActivity]::[onResume]::[is googleapiclient connected?]::[" + mGoogleApiClient.isConnected() + "]");

        //when user comes back to the app after some time, location might be outdated, thus it is refreshed if older than 30 minutes
        if (mLastLocation != null && (System.currentTimeMillis() - mLastLocation.getTime()) > 30 * 60 * 1000)
        {
            WeatherLog.i("[MainActivity]::[onResume]::[location is out of date, updating]");
            mLastLocation = getLastKnownLocation();
            broadcastLocation(mLastLocation);
        }
    }

    @Override protected void onStart()
    {
        super.onStart();
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.connect();
        }
    }

    @Override protected void onStop()
    {
        super.onStop();
        stopLocationUpdates();
    }

    @Override protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putParcelable(WeatherApplication.LOCATION_KEY, mLastLocation);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        WeatherLog.i("[MainActivity]::[onNavigationDrawerItemSelected]::[" + position + "]");
                // update the main content by replacing fragments
        onSectionAttached(position);
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = null;
        String tag = "";

        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putParcelable(WeatherApplication.LOCATION_KEY, mLastLocation);

        switch (position)
        {
        case 0:
            tag = getString(R.string.today);
            fragment = fragmentManager.findFragmentByTag(tag);

            if (fragment == null)
            {
                fragment = new TodayFragment_();
                fragment.setArguments(fragmentBundle);
            }
            break;
        case 1:
            tag = getString(R.string.forecast);
            fragment = fragmentManager.findFragmentByTag(tag);

            if (fragment == null)
            {
                fragment = new ForecastFragment_();
                fragment.setArguments(fragmentBundle);
            }
            break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit();
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                                                      PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                               "Your device is missing Google Play Services. Please install and come back later.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    public void onSectionAttached(int number)
    {
        switch (number)
        {
        case 0:
            mTitle = getString(R.string.today);
            break;
        case 1:
            mTitle = getString(R.string.forecast);
            break;
        }

        getSupportActionBar().setTitle(mTitle);
    }

    public void restoreActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!mNavigationDrawerFragment.isDrawerOpen())
        {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_about)
        {
            showAboutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAboutDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AboutDialog);
        builder.setTitle(getResources().getString(R.string.action_about));
        builder.setMessage(getResources().getString(R.string.about))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                    }
                });
        builder.create().show();
    }

    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest()
    {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(30 * 60 * 1000);
        mLocationRequest.setFastestInterval(10 * 60 * 1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdates()
    {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdates()
    {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }

    @Override public void onConnected(Bundle bundle)
    {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null)
        {
            mLastLocation = getLastKnownLocation();
        }

        WeatherLog.i("[MainActivity]::[onConnected]::[" + mLastLocation + "]");

        broadcastLocation(mLastLocation);
//        startLocationUpdates();
    }

    private Location getLastKnownLocation()
    {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;

        for (String provider : providers)
        {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null)
            {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy())
            {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override public void onConnectionSuspended(int i)
    {
        WeatherLog.w("[MainActivity]::[onConnectionSuspended]::[" + i + "]");
        mGoogleApiClient.connect();
    }

    @Override public void onConnectionFailed(ConnectionResult connectionResult)
    {
        WeatherLog.w("[MainActivity]::[onConnectionFailed]::[" + connectionResult + "]");
    }

    @Override public void onLocationChanged(Location location)
    {
        WeatherLog.i("[MainActivity]::[onLocationChanged]::[" + location + "]");

        if (mLastLocation != location)
        {
            broadcastLocation(location);
            mLastLocation = location;
        }
    }

    private void broadcastLocation(Location location)
    {
        for (Fragment fragment : getSupportFragmentManager().getFragments())
        {
            if (fragment instanceof LocationAwareFragment)
            {
                ((LocationAwareFragment) fragment).locationUpdated(location);
            }
        }
    }

}
