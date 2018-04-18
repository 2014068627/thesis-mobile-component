package com.ust.thesis.lightsandsockets;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.ActivitiesAdapter;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment {

    ListView lv;
    Context context;
    Activity activity;

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        activity = getActivity();
        context = activity.getApplicationContext();
        initialize(view);

        String url = getString(R.string.apiserver) + "api/powerboard/activities";
        activityRequest(url);

        return view;
    }
    private void initialize(View view){
        lv = view.findViewById(R.id.listView);
    }

    private void activityRequest(String url){
        //dialog box for loading
        final ProgressDialog api_dialog = new ProgressDialog(activity);
        api_dialog.setMessage(getString(R.string.api_wait));
        api_dialog.show();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject jres = response.getJSONObject("response");
                    boolean success = jres.getBoolean("success");
                    String message = jres.getString("message");
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                    if(success){
                        JSONArray activities = response.getJSONArray("user_activity");
                        ActivitiesAdapter act = new ActivitiesAdapter(context, activities);
                        lv.setAdapter(act);
                    }else{
                        Toast.makeText(context,"Something occurred. Try again later",Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                }
                api_dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error!",Toast.LENGTH_SHORT).show();
                api_dialog.dismiss();
            }
        });

        LSingleton.getInstance(context).addToRequestQueue(request);
    }
}
