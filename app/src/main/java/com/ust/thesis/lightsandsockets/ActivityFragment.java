package com.ust.thesis.lightsandsockets;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.ActivitiesAdapter;
import com.ust.thesis.lightsandsockets.objects.LSingleton;
import com.ust.thesis.lightsandsockets.objects.RecentActivities;
import com.ust.thesis.lightsandsockets.objects.RecentActivitiesAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment {

    private ListView lv;
    private RecentActivitiesAdapter raa;
    private List<RecentActivities> mActivityList;
    private Context context;

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        context = getActivity();
        initialize(view);

        String url = getString(R.string.apiserver) + "api/powerboard/activities";
        activityRequest(url, view);

//        lv = (ListView) view.findViewById(R.id.listView);
//        mActivityList = new ArrayList<>();
//
//        mActivityList.add(new RecentActivities(1, "Schedule socket 1 to turn off", "2/2/2018", "user"));
//        mActivityList.add(new RecentActivities(1, "Schedule socket 2 to turn off", "3/3/2018", "apple"));
//        mActivityList.add(new RecentActivities(1, "Schedule socket 3 to turn off", "4/4/2018", "orange"));
//        mActivityList.add(new RecentActivities(1, "Schedule socket 4 to turn off", "5/5/2018", "something"));
//        mActivityList.add(new RecentActivities(1, "Schedule socket 2 to turn off", "6/6/2018", "user2"));
//        mActivityList.add(new RecentActivities(1, "Schedule socket 1 to turn off", "7/7/2018", "banana"));
//
//        raa = new RecentActivitiesAdapter(getContext(), mActivityList);
//        lv.setAdapter(raa);
        return view;
    }
    private void initialize(View view){
        lv = (ListView) view.findViewById(R.id.listView);
    }

    private void activityRequest(String url, View view){
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
                        ActivitiesAdapter act = new ActivitiesAdapter(getContext(), activities);
                        lv.setAdapter(act);
                    }else{
                        Toast.makeText(context,"Something occurred. Try again later",Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error!",Toast.LENGTH_SHORT).show();

            }
        });

        LSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }
}
