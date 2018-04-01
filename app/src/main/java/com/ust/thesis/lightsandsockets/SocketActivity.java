package com.ust.thesis.lightsandsockets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SocketActivity extends AppCompatActivity {

    TextView socketNumber;
    TextView  Iappliance;

    /*
    NOTE:
        Think of a way that the device identification will sill be present in this activity. Use the string socket as the way.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        socketNumber = (TextView) findViewById(R.id.socket_number);
        Iappliance = (TextView) findViewById(R.id.identifiedDevice);
        Bundle bundle = getIntent().getExtras();
        String socket = bundle.getString("socket"); /*Contains the name of the socket currently selected"*/
        String appliance = bundle.getString("appliance"); /*Temporary device identification*/
        socketNumber.setText(socket);
        Iappliance.setText(appliance);

    }
}
