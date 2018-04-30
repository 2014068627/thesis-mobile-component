package com.ust.thesis.lightsandsockets;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ust.thesis.lightsandsockets.objects.LGraph;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LightGraphWeeklyFragment extends Fragment {

    LineChart lineChart;
    Context context;

    public LightGraphWeeklyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_graph, container, false);
        initialize(view);

        char socket_id = getArguments().getString("socket_id").charAt(0);
        String url = getString(R.string.apiserver) + "api/powerboard/weekly_graph/" + socket_id;

        weeklygraphRequest(url);
        return view;
    }

    private void initialize(View view){
        lineChart = view.findViewById(R.id.graph);
        context = getActivity().getApplicationContext();
    }

    private void weeklygraphRequest(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
                    String message = json_response.getString("message");
                    if(success){
                        JSONArray value = response.getJSONArray("weekly_graph");

                        LGraph linegraph = new LGraph(value);
                        if(linegraph != null){
                            LineData theData = linegraph.getLineData(context.getApplicationContext());
                            lineChart.setTouchEnabled(true);
                            lineChart.setDragEnabled(true);
                            lineChart.setScaleEnabled(true);
                            lineChart.setData(theData);

                            // https://stackoverflow.com/questions/49477368/mpandroidchart-inside-volley-onresponse
                            lineChart.invalidate();
                        }else{
                            Toast.makeText(context,"Something went wrong :(, try again laterc.",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context,"Something went wrong :(, try again laterd.",Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(context,"Something occured :(, try again laterb",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        LSingleton.getInstance(context).addToRequestQueue(request);
    }

}
