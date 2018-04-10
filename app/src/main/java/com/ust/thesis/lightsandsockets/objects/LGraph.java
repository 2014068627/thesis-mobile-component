package com.ust.thesis.lightsandsockets.objects;

import android.app.Fragment;
import android.content.Context;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ust.thesis.lightsandsockets.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Reinier on 4/10/2018.
 */

public class LGraph{
    private JSONArray json_values;

    public LGraph(JSONArray json_values){
        this.json_values = json_values;
    }
    public LineData getLineData(Context context){
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        try{
            for(int i = 0; i < this.json_values.length(); i++){
                JSONObject jobject = this.json_values.getJSONObject(i);
                float watt = Float.parseFloat(jobject.getString("watt_consumed"));
                String date_format = jobject.getString("date_format");
                entries.add(new Entry(watt, i));
                dates.add(date_format);
            }

            LineDataSet lineDataSet = new LineDataSet(entries,"Consumption");
            lineDataSet.setLineWidth(5f);
            lineDataSet.setColor(context.getResources().getColor(R.color.lineColor));
            lineDataSet.setCircleColor(context.getResources().getColor(R.color.dotColor));
            lineDataSet.setCircleSize(7f);
            lineDataSet.setValueTextSize(15f);
            lineDataSet.setValueTextColor(context.getResources().getColor(R.color.TextGraphColor));

            LineData theData = new LineData(dates,lineDataSet);
            return theData;
        }catch(Exception e){
            Toast.makeText(context, e.toString(),Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public JSONArray getJson_values() {
        return json_values;
    }

    public void setJson_values(JSONArray json_values) {
        this.json_values = json_values;
    }
}
