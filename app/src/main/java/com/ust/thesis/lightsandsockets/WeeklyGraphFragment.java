package com.ust.thesis.lightsandsockets;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class WeeklyGraphFragment extends Fragment {

    private LineChart lineChart;
    private Context context;

    public WeeklyGraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekly_graph, container, false);
        initialize(view);

        //get socket number
        String socket_name = getArguments().getString("socket");
        char socket_id = socket_name.charAt(socket_name.length() - 1);

        //get from this url
        String url = getString(R.string.apiserver) + "api/powerboard/weekly_graph/" + socket_id;

        weeklygraphRequest(url);


//        lineChart = (LineChart) view.findViewById(R.id.graph);
//        ArrayList<Entry> entries = new ArrayList<>();
//        entries.add(new Entry(100f,0));
//        entries.add(new Entry(46f,1));
//        entries.add(new Entry(22f,2));
//        entries.add(new Entry(12f,3));
//        LineDataSet lineDataSet = new LineDataSet(entries,"Consumption");
//        lineDataSet.setLineWidth(5f);
//        lineDataSet.setColor(getResources().getColor(R.color.lineColor));
////        lineDataSet.setCircleColor(getResources().getColor(R.color.dotColor));
////        lineDataSet.setCircleSize(7f);
//        lineDataSet.setValueTextSize(15f);
//        lineDataSet.setValueTextColor(getResources().getColor(R.color.TextGraphColor));
//
//        ArrayList<String> theDates = new ArrayList<>();
//        theDates.add("WEEK 1");
//        theDates.add("WEEK 2");
//        theDates.add("WEEK 3");
//        theDates.add("WEEK 4");
//
//        LineData theData = new LineData(theDates,lineDataSet);
//        lineChart.setData(theData);
//        lineChart.setTouchEnabled(true);
//        lineChart.setDragEnabled(true);
//        lineChart.setScaleEnabled(true);
        return view;
    }
    private void initialize(View view){
        lineChart = view.findViewById(R.id.graph);
        context = getActivity();
    }

    private void weeklygraphRequest(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
                    String message = json_response.getString("message");
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context,"Something occured :(, try again latera",Toast.LENGTH_SHORT).show();
                
            }
        });
        LSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }


}
