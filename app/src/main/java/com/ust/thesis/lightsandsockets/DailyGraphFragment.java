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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
public class DailyGraphFragment extends Fragment {

    LineChart lineChart;
    Context context;

    public DailyGraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_graph, container, false);
        initialize(view);
        String socket_name = getArguments().getString("socket");
        char socket_id = socket_name.charAt(socket_name.length() - 1);
        String url = getString(R.string.apiserver) + "api/powerboard/daily_graph/" + socket_id;
//        Toast.makeText(context,url,Toast.LENGTH_SHORT).show();
        dailygraphRequest(url, view);

//        ArrayList<Entry> entries = new ArrayList<>();
//        entries.add(new Entry(44f,0));
//        entries.add(new Entry(88f,1));
//        entries.add(new Entry(66f,2));
//        entries.add(new Entry(12f,3));
//        entries.add(new Entry(19f,4));
//        entries.add(new Entry(54f,5));
//        entries.add(new Entry(200f,6));
//        LineDataSet lineDataSet = new LineDataSet(entries,"Consumption");
//        lineDataSet.setLineWidth(5f);
//        lineDataSet.setColor(getResources().getColor(R.color.lineColor));
//        lineDataSet.setCircleColor(getResources().getColor(R.color.dotColor));
//        lineDataSet.setCircleSize(7f);
//        lineDataSet.setValueTextSize(15f);
//        lineDataSet.setValueTextColor(getResources().getColor(R.color.TextGraphColor));
//
//        ArrayList<String> theDates = new ArrayList<>();
//        theDates.add("Monday");
//        theDates.add("Tuesday");
//        theDates.add("Wednesday");
//        theDates.add("Thursday");
//        theDates.add("Friday");
//        theDates.add("Saturday");
//        theDates.add("Sunday");
//
//
//        LineData theData = new LineData(theDates,lineDataSet);
//        lineChart.setData(theData);
//        lineChart.setTouchEnabled(true);
//        lineChart.setDragEnabled(true);
//        lineChart.setScaleEnabled(true);
        return view;
    }
    private void initialize(View view){
        lineChart = (LineChart) view.findViewById(R.id.graph);
        context = getActivity();
    }

    private void dailygraphRequest(String url, View view){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject jres = response.getJSONObject("response");
                    boolean success = jres.getBoolean("success");
                    String message = jres.getString("message");
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                    if(success){
                        JSONArray value = response.getJSONArray("daily_graph");

                        ArrayList<Entry> entries = new ArrayList<>();
                        ArrayList<String> dates = new ArrayList<>();
                        for(int i = 0; i < value.length(); i++){
                            JSONObject jobject = value.getJSONObject(i);
                            float watt = Float.parseFloat(jobject.getString("watt_consumed"));
                            String date_format = jobject.getString("date_format");
                            entries.add(new Entry(watt, i));
                            dates.add(date_format);
                        }

                        Toast.makeText(context,"checkpoint 1",Toast.LENGTH_SHORT).show();
                        LineDataSet lineDataSet = new LineDataSet(entries,"Consumption");
                        lineDataSet.setLineWidth(5f);
                        lineDataSet.setColor(context.getResources().getColor(R.color.lineColor));
                        Toast.makeText(context,"checkpoint 2",Toast.LENGTH_SHORT).show();
//                        lineDataSet.setCircleColor(context.getResources().getColor(R.color.dotColor));
//                        lineDataSet.setCircleSize(7f);
                        lineDataSet.setValueTextSize(15f);
                        lineDataSet.setValueTextColor(context.getResources().getColor(R.color.TextGraphColor));
                        Toast.makeText(context,"checkpoint 3",Toast.LENGTH_SHORT).show();

                        LineData theData = new LineData(dates,lineDataSet);
                        Toast.makeText(context,"checkpoint 4",Toast.LENGTH_SHORT).show();
                        lineChart.setData(theData);
                        Toast.makeText(context,"checkpoint 5",Toast.LENGTH_SHORT).show();
//                        lineChart.setTouchEnabled(true);
//                        lineChart.setDragEnabled(true);
//                        lineChart.setScaleEnabled(true);


//                        LGraph linegraph = new LGraph(value);
//                        if(linegraph != null){
//                            LineData theData = linegraph.getLineData(context.getApplicationContext());
//                            lineChart.setData(theData);
//                            lineChart.setTouchEnabled(true);
//                            lineChart.setDragEnabled(true);
//                            lineChart.setScaleEnabled(true);
//                        }else{
//                            Toast.makeText(context,"Something went wrong :(, try again later.",Toast.LENGTH_SHORT).show();
//                        }
                    }else{
                        Toast.makeText(context,"Something happened, try again later.",Toast.LENGTH_SHORT).show();

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
