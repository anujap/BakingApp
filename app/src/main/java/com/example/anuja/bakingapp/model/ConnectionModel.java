package com.example.anuja.bakingapp.model;

import com.example.anuja.bakingapp.common.ConnectionStatus;

public class ConnectionModel {

    /**
     * the signal strength
     */
    private ConnectionStatus connectionStatus;

    public ConnectionModel(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }
}
