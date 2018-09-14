package com.example.anuja.bakingapp.app.activities;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.anuja.bakingapp.common.ConnectionStatus;
import com.example.anuja.bakingapp.model.ConnectionModel;
import com.example.anuja.bakingapp.receiver.NetworkConnectivityReceiver;

/**
 * This is the Base class for all the activities
 */
public abstract class BaseActivity extends AppCompatActivity {

    // connection is available
    protected abstract void onConnected();

    // connection is unavailable
    protected abstract void onDisconnected();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleConnectivity();
    }

    /**
     * function called to handle the connectivity
     */
    private void handleConnectivity() {
        NetworkConnectivityReceiver connectivityReceiver = new NetworkConnectivityReceiver(getApplicationContext());
        connectivityReceiver.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(@Nullable ConnectionModel connectionModel) {
                if(connectionModel.getConnectionStatus() == ConnectionStatus.CONNECTED)
                    onConnected();
                else if(connectionModel.getConnectionStatus() == ConnectionStatus.NOT_CONNECTED)
                    onDisconnected();
            }
        });
    }
}
