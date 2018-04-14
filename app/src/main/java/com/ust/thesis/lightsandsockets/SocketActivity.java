package com.ust.thesis.lightsandsockets;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SocketActivity extends AppCompatActivity {

    TextView socketNumber;
    TextView  iappliance;
    Button weeklyButton;
    Button dailyButton;
    Button showNV;
    Bundle bundle;
    DailyGraphFragment DF;
    WeeklyGraphFragment WF;

    /*
    NOTE:
        Think of a way that the device identification will sill be present in this activity. Use the string socket as the way.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        //set variables
        initialize();

        String socket = bundle.getString("socket"); /*Contains the name of the socket currently selected"*/
        String appliance = bundle.getString("appliance"); /*Temporary device identification*/
        socketNumber.setText(socket);
        iappliance.setText(appliance);

        //give bundle to fragment class
        DF.setArguments(bundle);
        WF.setArguments(bundle);

        setFragment(DF);
        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyButton.setBackgroundColor(getResources().getColor(R.color.white));
                dailyButton.setTextColor(getResources().getColor(R.color.maroon));
                weeklyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
                weeklyButton.setTextColor(getResources().getColor(R.color.peach));
                setFragment(DF);
            }
        });

        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeklyButton.setBackgroundColor(getResources().getColor(R.color.white));
                weeklyButton.setTextColor(getResources().getColor(R.color.maroon));
                dailyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
                dailyButton.setTextColor(getResources().getColor(R.color.peach));
                setFragment(WF);
            }
        });

        directToShowNV(showNV);

    }

    /**
     * function to initialize design variables
     */
    private void initialize(){
        socketNumber = findViewById(R.id.socket_number);
        iappliance = findViewById(R.id.identifiedDevice);
        showNV = findViewById(R.id.showNV);
        bundle = getIntent().getExtras();

        //SET FRAGMENTS
        DF = new DailyGraphFragment();
        WF = new WeeklyGraphFragment();
        weeklyButton = findViewById(R.id.WeeklyButton);
        dailyButton = findViewById(R.id.DailyButton);
    }


    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public void directToShowNV(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SocketActivity.this, ShowNumericalValuesActivity.class);
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
    }
}
