package com.ust.thesis.lightsandsockets;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSession;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URL;
import java.util.HashMap;


public class SocketActivity extends AppCompatActivity {

    TextView socketNumber;
    TextView  iappliance;
    Button weeklyButton;
    Button dailyButton;
    Button showNV;
    Bundle bundle;
    Boolean schedule;
    DailyGraphFragment DF;
    WeeklyGraphFragment WF;
    CancelScheduleFragment CSF;
    SwitchButtonFragment SBF;
    Button scheduleButton;
    SwitchCompat switch_socket;
    Context context;

    char socket_id;

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

        //give bundle to fragment class
        DF.setArguments(bundle);
        WF.setArguments(bundle);
        CSF.setArguments(bundle);
        SBF.setArguments(bundle);

        CheckSchedule(schedule);

        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyButton.setBackgroundColor(getResources().getColor(R.color.white));
                dailyButton.setTextColor(getResources().getColor(R.color.maroon));
                weeklyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
                weeklyButton.setTextColor(getResources().getColor(R.color.peach));
                setFragmentGraph(DF);
            }
        });

        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeklyButton.setBackgroundColor(getResources().getColor(R.color.white));
                weeklyButton.setTextColor(getResources().getColor(R.color.maroon));
                dailyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
                dailyButton.setTextColor(getResources().getColor(R.color.peach));
                setFragmentGraph(WF);
            }
        });

        directToShowNV(showNV);
        GoBack();
    }

    /**
     * function to initialize design variables
     */
    private void initialize(){
        socketNumber = findViewById(R.id.socket_number);
        iappliance = findViewById(R.id.identifiedDevice);
        scheduleButton = findViewById(R.id.scheduleButton);
        switch_socket = findViewById(R.id.switch1);
        showNV = findViewById(R.id.showNV);
        context = getApplicationContext();
        bundle = getIntent().getExtras();
        String socket = bundle.getString("socket"); /*Contains the name of the socket currently selected"*/
        String appliance = bundle.getString("appliance"); /*Temporary device identification*/
        socket_id = bundle.getString("socket_id").charAt(0);
        schedule = bundle.getBoolean("schedule");

        socketNumber.setText(socket);
        iappliance.setText(appliance);

        //SET FRAGMENTS
        DF = new DailyGraphFragment();
        WF = new WeeklyGraphFragment();
        CSF = new CancelScheduleFragment();
        SBF = new SwitchButtonFragment();
        weeklyButton = findViewById(R.id.WeeklyButton);
        dailyButton = findViewById(R.id.DailyButton);
        setFragmentGraph(DF);
    }


    private void setFragmentGraph(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FragmentContainerGraph, fragment);
        fragmentTransaction.commit();
    }

    private void setFragmentSchedActive(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FragmentContainerSched, fragment);
        fragmentTransaction.commit();
    }

    /**
     * function for clicked button to show Numerical data
     */
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

    private void CheckSchedule(Boolean sched){
        if(sched){
            setFragmentSchedActive(CSF);
        }else{
            setFragmentSchedActive(SBF);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 100){
            try{
                boolean schedule = data.getBooleanExtra("schedule", false);
                if(schedule){
                    CheckSchedule(true);
                }else{
                    Toast.makeText(getApplicationContext(), "this is fail", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){

            }
        }
    }

    private void GoBack(){
        ImageButton bttn = (ImageButton) findViewById(R.id.backButton);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SocketActivity.this, fragmentContainer.class);
                startActivity(myIntent);
            }
        });
    }
}


