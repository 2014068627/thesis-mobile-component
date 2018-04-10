package com.ust.thesis.lightsandsockets;


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

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment {

    private ListView lv;
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
