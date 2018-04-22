package com.ust.thesis.lightsandsockets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSession;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONObject;

/**
 * Created by BINS on 15/04/2018.
 */

public class profileFragment extends Fragment{

    public profileFragment(){
    }

    Button changePassButton;
    Button logoutButton;
    Button aboutButton;
    TextView profile_username;
    TextView profile_name;

    Context context;
    Activity activity;
    String username;
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize(view);
        String url = getString(R.string.apiserver) + "api/powerboard/userinfo/"+id;
        profileRequest(url);

        changePassword(changePassButton);
        aboutActivity(aboutButton);
        logoutUser(logoutButton);
        return view;
    }

    /**
     * function to initialize UI
     */
    public void initialize(View view){
        changePassButton = view.findViewById(R.id.changePassButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        aboutButton = view.findViewById(R.id.aboutButton);
        profile_name = view.findViewById(R.id.profile_name);
        profile_username = view.findViewById(R.id.profile_username);
        activity = getActivity();
        context = activity.getApplicationContext();
        LSession user_session = new LSession();
        String [] user_arr = user_session.getUserSession(context.getApplicationContext());
        id = user_arr[0];
        username = user_arr[1];
    }

    /**
     * function to go to intent Change Password Activity
     */
    public void changePassword(Button changePassButton){
        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(profileFragment.this.getActivity(), ChangePasswordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("username", username);
                myIntent.putExtras(bundle);
                getActivity().startActivityForResult(myIntent, 301);
            }
        });
    }

    /**
     * function for button to go to intent About Activity
     * @param about
     */
    public void aboutActivity(Button about){
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(profileFragment.this.getActivity(), AboutActivity.class);
                getActivity().startActivityForResult(myIntent, 301);
            }
        });
    }

    /**
     * function for user logout
     */
    public void logoutUser(Button logout){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //alert box to confirm user for logout
                new AlertDialog.Builder(activity)
                        .setTitle("Do you want to logout?")
                        .setMessage("Are you sure you want to logout?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LSession session = new LSession();
                                session.clearSession(context);
                                activity.finish();
                            }
                        }).create().show();
            }
        });
    }

    /**
     * function to request API from server getting the user information
     */
    private void profileRequest(String url){
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
                    if(success){
                        JSONObject json_user = response.getJSONObject("user");
                        String name = json_user.getString("name");
                        username = json_user.getString("username");
                        profile_name.setText(name);
                        profile_username.setText(username);
                    }else{
                        Toast.makeText(context, "Something occurred, Try again later.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(context, "Something occurred, Try again later.", Toast.LENGTH_SHORT).show();
                }
                api_dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show();
                api_dialog.dismiss();
            }
        });
        LSingleton.getInstance(context).addToRequestQueue(request);
    }
}
