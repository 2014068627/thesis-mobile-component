package com.ust.thesis.lightsandsockets;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LightGraphWeeklyFragment extends Fragment {

    LineChart lineChart;

    public LightGraphWeeklyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_graph, container, false);
        lineChart = (LineChart) view.findViewById(R.id.graph);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f,0));
        entries.add(new Entry(35f,1));
        entries.add(new Entry(100f,2));
        entries.add(new Entry(32f,3));
        LineDataSet lineDataSet = new LineDataSet(entries,"Consumption");
        lineDataSet.setLineWidth(5f);
        lineDataSet.setColor(getResources().getColor(R.color.lineColor));
        lineDataSet.setCircleColor(getResources().getColor(R.color.dotColor));
        lineDataSet.setCircleSize(7f);
        lineDataSet.setValueTextSize(15f);
        lineDataSet.setValueTextColor(getResources().getColor(R.color.TextGraphColor));

        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("WEEK 1");
        theDates.add("WEEK 2");
        theDates.add("WEEK 3");
        theDates.add("WEEK 4");

        LineData theData = new LineData(theDates,lineDataSet);
        lineChart.setData(theData);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        return view;
    }

}
