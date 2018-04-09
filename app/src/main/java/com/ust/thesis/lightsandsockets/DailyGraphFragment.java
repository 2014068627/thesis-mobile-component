package com.ust.thesis.lightsandsockets;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyGraphFragment extends Fragment {

    LineChart lineChart;

    public DailyGraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_graph, container, false);

        lineChart = (LineChart) view.findViewById(R.id.graph);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(44f,0));
        entries.add(new Entry(88f,1));
        entries.add(new Entry(66f,2));
        entries.add(new Entry(12f,3));
        entries.add(new Entry(19f,4));
        entries.add(new Entry(54f,5));
        entries.add(new Entry(200f,6));
        LineDataSet lineDataSet = new LineDataSet(entries,"Consumption");
        lineDataSet.setLineWidth(5f);
        lineDataSet.setColor(getResources().getColor(R.color.lineColor));
        lineDataSet.setCircleColor(getResources().getColor(R.color.dotColor));
        lineDataSet.setCircleSize(7f);
        lineDataSet.setValueTextSize(15f);
        lineDataSet.setValueTextColor(getResources().getColor(R.color.TextGraphColor));

        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("Monday");
        theDates.add("Tuesday");
        theDates.add("Wednesday");
        theDates.add("Thursday");
        theDates.add("Friday");
        theDates.add("Saturday");
        theDates.add("Sunday");

        LineData theData = new LineData(theDates,lineDataSet);
        lineChart.setData(theData);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        return view;
    }

}
