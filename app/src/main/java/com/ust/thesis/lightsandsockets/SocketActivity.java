package com.ust.thesis.lightsandsockets;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class SocketActivity extends AppCompatActivity {

    TextView socketNumber;
    TextView  Iappliance;
    Button weeklyButton;
    Button dailyButton;
    Button showNV;
    Button scheduleButton;
    SwitchCompat s;
    private DailyGraphFragment DF;
    private WeeklyGraphFragment WF;

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
        showNV = (Button) findViewById(R.id.showNV);
        scheduleButton = (Button) findViewById(R.id.scheduleButton);
        s = (SwitchCompat) findViewById(R.id.switch1);
        Bundle bundle = getIntent().getExtras();
        String socket = bundle.getString("socket"); /*Contains the name of the socket currently selected"*/
        String appliance = bundle.getString("appliance"); /*Temporary device identification*/
        socketNumber.setText(socket);
        Iappliance.setText(appliance);

        //SET FRAGMENTS
        DF = new DailyGraphFragment();
        WF = new WeeklyGraphFragment();
        weeklyButton = (Button) findViewById(R.id.WeeklyButton);
        dailyButton = (Button) findViewById(R.id.DailyButton);

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


        EnableButton(s);
        ChangeButtonlook(s.isChecked());
        directToShowNV(showNV, socketNumber, Iappliance/*temporary*/);
        directToSchedule(scheduleButton, socketNumber, Iappliance/*temporary*/);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public void directToShowNV(Button button, TextView number, TextView appliances/*temporary*/){
        final String socket = number.getText().toString();
        final String appliance = appliances.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SocketActivity.this, ShowNumericalValuesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("socket", socket);
                bundle.putString("appliance", appliance); /*temporary*/
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
    }

    public void directToSchedule(Button button, TextView number, TextView appliances/*temporary*/){
        final String socket = number.getText().toString();
        final String appliance = appliances.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SocketActivity.this, ScheduleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("socket", socket);
                bundle.putString("appliance", appliance); /*temporary*/
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
    }

    public void EnableButton(SwitchCompat sw) {
        scheduleButton = (Button) findViewById(R.id.scheduleButton);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ChangeButtonlook(isChecked);
            }
        });
    }

    public void ChangeButtonlook(boolean bol){
        if(bol) {
            scheduleButton.setBackgroundColor(Color.parseColor("#9c382d"));
            scheduleButton.setTextColor(Color.parseColor("#dbc3c0"));
            scheduleButton.setEnabled(true);
        } else {
            scheduleButton.setBackgroundColor(Color.parseColor("#dbc3c0"));
            scheduleButton.setTextColor(Color.parseColor("#ffffff"));
            scheduleButton.setEnabled(false);
        }
    }

}
