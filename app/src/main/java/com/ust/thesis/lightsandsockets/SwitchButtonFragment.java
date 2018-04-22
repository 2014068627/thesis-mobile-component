package com.ust.thesis.lightsandsockets;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.SwitchCompat;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSession;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SwitchButtonFragment extends Fragment {

    Button scheduleButton;
    SwitchCompat switch_socket;
    Bundle bundle;

    char socket_id;

    public SwitchButtonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_switch_button, container, false);
        initialize(view);

        //for enabling schedule button if it is on
        enableSwitch(switch_socket);
        changeButtonLook(switch_socket.isChecked());
        directToSchedule(scheduleButton);

        return view;
    }

    private void initialize(View view){
        scheduleButton = view.findViewById(R.id.scheduleButton);
        switch_socket = view.findViewById(R.id.switch1);
        bundle = getArguments();
        String socket = getArguments().getString("socket");
        String appliance = getArguments().getString("appliance");
        setButtonStatus(switch_socket, getArguments().getString("socket_status"));
        socket_id = getArguments().getString("socket_id").charAt(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            try{
//                String message = data.getStringExtra("message");
////                Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this.getContext(), "asdasd", Toast.LENGTH_SHORT).show();
                boolean schedule = data.getBooleanExtra("schedule", false);
                if(schedule){
                    Toast.makeText(this.getContext(), "this is success", Toast.LENGTH_SHORT).show();

                    //do fragment here
//                    SocketActivity SA = new SocketActivity();
//                    SA.CheckSchedule(true);
                }else{
                    Toast.makeText(this.getContext(), "this is fail", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
            }
        }
    }

    public void directToSchedule(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent, 100);
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
     * function to change output if switch is switched
     */
    public void enableSwitch(SwitchCompat sw){
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String url = getString(R.string.apiserver) + "api/powerboard/switch_socket";

                LSession user_session = new LSession();
                String [] user_arr = user_session.getUserSession(getActivity());
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
        final ProgressDialog api_dialog = new ProgressDialog(this.getContext());
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
                        Toast.makeText(getActivity(), "Something occurred, Try again later.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(getActivity(), "Something occurred, Try again later!", Toast.LENGTH_SHORT).show();
                }
                api_dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                api_dialog.dismiss();
            }
        });
        LSingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

}
