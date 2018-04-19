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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSession;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONObject;

import java.util.HashMap;


public class SocketActivity extends AppCompatActivity {

    TextView socketNumber;
    TextView  iappliance;
    Button weeklyButton;
    Button dailyButton;
    Button showNV;
    Bundle bundle;
    DailyGraphFragment DF;
    WeeklyGraphFragment WF;
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

        String socket = bundle.getString("socket"); /*Contains the name of the socket currently selected"*/
        String appliance = bundle.getString("appliance"); /*Temporary device identification*/
        setButtonStatus(switch_socket, bundle.getString("socket_status"));
        socket_id = bundle.getString("socket_id").charAt(0);
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

        //for enabling schedule button if it is on
        enableSwitch(switch_socket);
        changeButtonLook(switch_socket.isChecked());
        directToSchedule(scheduleButton);
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

    /**
     * function for clicked schedule button to go to schedule page
     */
    public void directToSchedule(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocketActivity.this, ScheduleActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 100);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            try{
                String message = data.getStringExtra("message");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }catch(Exception e){
            }
        }
    }



    /**
     * function to change output if switch is switched
     */
    public void enableSwitch(SwitchCompat sw){
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String url = getString(R.string.apiserver) + "api/powerboard/switch_socket";

                LSession user_session = new LSession();
                String [] user_arr = user_session.getUserSession(context);
                String user_id = user_arr[0];
                String user_username = user_arr[1];
                String socket_switch;
                if(isChecked){
                    socket_switch = "on";
                }else{
                    socket_switch = "off";
                }
                String socket = String.valueOf(socket_id);
                HashMap json_socket = new HashMap();
                json_socket.put("socket", socket);
                json_socket.put("switch", socket_switch);
                json_socket.put("user_id", user_id);
                json_socket.put("user_username", user_username);

                switchsocketRequest(url, json_socket);
                changeButtonLook(isChecked);
            }
        });
    }

    /**
     * function to change the enabled button schedule
     */
    public void changeButtonLook(boolean switched){
        if(switched){
            scheduleButton.setBackgroundColor(Color.parseColor("#9c382d"));
            scheduleButton.setTextColor(Color.parseColor("#dbc3c0"));
            scheduleButton.setEnabled(true);
        }else{
            scheduleButton.setBackgroundColor(Color.parseColor("#dbc3c0"));
            scheduleButton.setTextColor(Color.parseColor("#ffffff"));
            scheduleButton.setEnabled(false);

        }
    }

    /**
     * function to check the status of socket and update switch compat
     */
    public void setButtonStatus(SwitchCompat sw, String status){
        if(status.equals("on")){
            sw.setChecked(true);
        }else if(status.equals("off")){
            sw.setChecked(false);
        }
    }

    private void switchsocketRequest(String url, HashMap json_socket){
        //dialog box for loading
        final ProgressDialog api_dialog = new ProgressDialog(this);
        api_dialog.setMessage(getString(R.string.api_wait));
        api_dialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(json_socket), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
                    if(success){
                        JSONObject json_socket = response.getJSONObject("socket");
                        String socket_state = json_socket.getString("socket_state");
                        setButtonStatus(switch_socket, socket_state);
                    }else{
                        Toast.makeText(context, "Something occurred, Try again later.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(context, "Something occurred, Try again later!", Toast.LENGTH_SHORT).show();
                }
                api_dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                api_dialog.dismiss();
            }
        });
        LSingleton.getInstance(context).addToRequestQueue(request);
    }
}
