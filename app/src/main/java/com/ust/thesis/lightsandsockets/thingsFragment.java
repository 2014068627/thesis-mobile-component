package com.ust.thesis.lightsandsockets;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class thingsFragment extends Fragment {

    ConstraintLayout light1;
    ConstraintLayout socket1Link;
    ConstraintLayout socket2Link;
    ConstraintLayout socket3Link;
    ConstraintLayout socket4Link;
    TextView socket1;
    TextView socket2;
    TextView socket3;
    TextView socket4;
    TextView appliance1;
    TextView appliance2;
    TextView appliance3;
    TextView appliance4;

    Activity activity;
    Context context;

    public thingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_things, container, false);
        light1 =  view.findViewById(R.id.Light1Link);
        socket1Link =  view.findViewById(R.id.socket1Link);
        socket2Link =  view.findViewById(R.id.socket2Link);
        socket3Link =  view.findViewById(R.id.socket3Link);
        socket4Link =  view.findViewById(R.id.socket4Link);
        socket1 = view.findViewById(R.id.socket1);
        socket2 = view.findViewById(R.id.socket2);
        socket3 = view.findViewById(R.id.socket3);
        socket4 = view.findViewById(R.id.socket4);
        appliance1 = view.findViewById(R.id.appliance1);
        appliance2 = view.findViewById(R.id.appliance2);
        appliance3 = view.findViewById(R.id.appliance3);
        appliance4 = view.findViewById(R.id.appliance4);
        activity = getActivity();
        context = activity.getApplicationContext();

        directToLight(light1);
        directToSocket(socket1Link, socket1, appliance1);
        directToSocket(socket2Link, socket2, appliance2);
        directToSocket(socket3Link, socket3, appliance3);
        directToSocket(socket4Link, socket4, appliance4);
        return view;
    }

    public void directToSocket(ConstraintLayout layout, TextView text, TextView appliances/*temporary*/){
        final String socket = text.getText().toString();
        final String appliance = appliances.getText().toString();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char socket_id = socket.charAt(socket.length() - 1);
                String url = getString(R.string.apiserver) + "api/powerboard/socket_status/" + String.valueOf(socket_id);
                Bundle bundle = new Bundle();
                bundle.putString("socket", socket);
                bundle.putString("appliance", appliance); /*temporary*/
                socketRequest(url, bundle);
            }
        });
    }

    public void directToLight(ConstraintLayout layout){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(thingsFragment.this.getActivity(), LightActivity.class);
                startActivity(myIntent);
            }
        });
    }

    /**
     * function for socket status thru API Request
     */
    private void socketRequest(String url, final Bundle bundle){
        //dialog box for loading
        final ProgressDialog api_dialog = new ProgressDialog(activity);
        api_dialog.setMessage(getString(R.string.api_wait));
        api_dialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
//                    String message = json_response.getString("message");
//                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    if(success){
                        JSONObject json_socket = response.getJSONObject("socket");
                        String socket = json_socket.getString("socket");
                        String socket_status = json_socket.getString("socket_status");
                        boolean schedule = json_socket.getBoolean("schedule");
                        if(schedule){
                            String sched_id = json_socket.getString("sched_id");
                            String date_sched = json_socket.getString("date_sched");
                            bundle.putString("sched_id",sched_id);
                            bundle.putString("date_sched",date_sched);
                        }
                        bundle.putBoolean("schedule",schedule);
                        bundle.putString("socket_id", socket);
                        bundle.putString("socket_status", socket_status);
                        //schedule bundle
                        Intent intent = new Intent(thingsFragment.this.getActivity(), SocketActivity.class);
                        intent.putExtras(bundle);

                        api_dialog.dismiss();
                        startActivity(intent);
                    }else{
                        Toast.makeText(activity, "Something occurred, Try again later.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(activity, "Something occurred, Try again later!", Toast.LENGTH_SHORT).show();
                }
                api_dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show();
                api_dialog.dismiss();
            }
        });
        LSingleton.getInstance(context).addToRequestQueue(request);
    }
}
