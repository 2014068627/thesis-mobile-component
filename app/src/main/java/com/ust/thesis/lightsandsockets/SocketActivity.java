package com.ust.thesis.lightsandsockets;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONException;
import org.json.JSONObject;


public class SocketActivity extends AppCompatActivity {

    Button weeklyButton;
    Button dailyButton;
    ImageButton refreshButton;
    Button showNV;
    Bundle bundle;
    Boolean schedule;
    DailyGraphFragment DF;
    WeeklyGraphFragment WF;
    CancelScheduleFragment CSF;
    SwitchButtonFragment SBF;
    Button scheduleButton;
    SwitchCompat switch_socket;
    TextView identifiedDevice;
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
        goBack();
        refreshAppliance();
    }

    /**
     * function to initialize design variables
     */
    private void initialize(){
        scheduleButton = findViewById(R.id.scheduleButton);
        switch_socket = findViewById(R.id.switch1);
        refreshButton = findViewById(R.id.refreshButton);
        showNV = findViewById(R.id.showNV);
        identifiedDevice = findViewById(R.id.identifiedDevice);
        context = getApplicationContext();
        bundle = getIntent().getExtras();
        String socket = bundle.getString("socket"); /*Contains the name of the socket currently selected"*/
//        String appliance = bundle.getString("appliance"); /*Temporary device identification*/
        socket_id = bundle.getString("socket_id").charAt(0);
        schedule = bundle.getBoolean("schedule");
        identifiedDevice.setText(bundle.getString("appliance"));

        identifiedDevice.setText(socket);

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
        // https://stackoverflow.com/questions/20702333/refresh-fragment-at-reload
        fragmentTransaction.detach(fragment).attach(fragment).commit();
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
            CSF.setArguments(bundle);
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
                    String date_sched = data.getStringExtra("date_sched");
                    String sched_id = data.getStringExtra("sched_id");
                    bundle.putString("date_sched", date_sched);
                    bundle.putString("sched_id", sched_id);
                    CheckSchedule(true);
                }else{
                    Toast.makeText(getApplicationContext(), "Something happened, Try again later!", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goBack(){
        ImageButton button_back = findViewById(R.id.backButton);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void refreshAppliance(){
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.apiserver) + "api/powerboard/get_appliance/" + socket_id;
                Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                getApplianceRequest(url);
            }
        });
    }
    private void getApplianceRequest(String url){
        //dialog box for loading
        final ProgressDialog api_dialog = new ProgressDialog(this);
        api_dialog.setMessage(getString(R.string.api_wait));
        api_dialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success  = json_response.getBoolean("success");
                    if(success){
                        JSONObject json_socket = response.getJSONObject("socket");
                        String appliance = json_socket.getString("appliance");
                        if (appliance.equalsIgnoreCase("none")){
                            identifiedDevice.setText(bundle.getString("socket"));
                        }else{
                            identifiedDevice.setText(appliance);
                        }
                    }else{
                        identifiedDevice.setText(bundle.getString("socket"));
                        Toast.makeText(context, "Something occured, Try again later.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    identifiedDevice.setText(bundle.getString("socket"));
                    Toast.makeText(context, "Something occured, Try again later!", Toast.LENGTH_SHORT).show();
                }
                api_dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                identifiedDevice.setText(bundle.getString("socket"));
                Toast.makeText(context , "ERROR!", Toast.LENGTH_SHORT).show();
                api_dialog.dismiss();
            }
        });
        LSingleton.getInstance(context).addToRequestQueue(request);
    }


}


