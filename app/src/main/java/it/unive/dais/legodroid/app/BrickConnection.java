package it.unive.dais.legodroid.app;

import java.io.IOException;

import it.unive.dais.legodroid.lib.comm.BluetoothConnection;

public class BrickConnection {

    private BluetoothConnection connection;
    private String brickName;

    public BrickConnection (String brickName){
        this.brickName = brickName;
        this.connection =  new BluetoothConnection(brickName);
    }

    public BluetoothConnection.BluetoothChannel connectToEv3() throws IOException {
        return this.connection.connect();
    }
}
