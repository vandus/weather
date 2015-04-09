package com.vandac.weather.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.vandac.weather.android.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Vanda on 7. 4. 2015.
 */
@EFragment
public abstract class LocationAwareFragment extends Fragment
{
    protected Location location;

    @ViewById(R.id.loading_overlay)
    View overlay;

    @ViewById(R.id.loading_progressBar)
    View progressbar;

    @ViewById(R.id.loading_txt)
    TextView loadingText;

    /**
     * Used for calls when location is updated
     * @param location
     */
    public abstract void locationUpdated(Location location);

    /**
     * Downloads data if device is connected
     */
    protected abstract void refreshData();

    /**
     * Shows loading progressbar and waits for the device to connect
     */
    protected void waitForNetwork()
    {
        showLoadingText();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    protected boolean isConnected()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Makes whole layout and progressbar visible
     */
    protected void showLoading()
    {
        overlay.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.VISIBLE);
    }

    /**
     * Provides user with text informing that network is not available
     */
    protected void showLoadingText()
    {
        showLoading();
        loadingText.setText(R.string.loading_no_network);
        loadingText.setVisibility(View.VISIBLE);
    }

    protected void hideLoading()
    {
        overlay.setVisibility(View.INVISIBLE);
        progressbar.setVisibility(View.INVISIBLE);
        loadingText.setVisibility(View.INVISIBLE);
    }

    /**
     * Broadcast receiver registered when network is unavailable, unregisters itself after network is back on, along with data refresh.
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override public void onReceive(Context context, Intent intent)
        {
            if (isConnected())
            {
                refreshData();
                context.unregisterReceiver(this);
            }
        }
    };

}
