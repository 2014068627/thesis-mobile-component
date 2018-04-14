package com.ust.thesis.lightsandsockets.objects;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ust.thesis.lightsandsockets.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BINS on 12/04/2018.
 */

public class ConsumptionNumericalValuesAdapter extends BaseAdapter{

    private Context mContext;
    private List<ConsumptionNumerical> mConsumptionList;

    public ConsumptionNumericalValuesAdapter(Context mContext, JSONArray json_array) {
        this.mContext = mContext;
        DecimalFormat df = new DecimalFormat("#.##");
        List<ConsumptionNumerical> cons = new ArrayList<>();
        try{
            for(int i = 0; i < json_array.length(); i++) {
                JSONObject json_object = json_array.getJSONObject(i);
                int id = Integer.parseInt(json_object.getString("id"));
                float f_watts = Float.parseFloat(json_object.getString("watt_consumed"));
                String wattage_consumed = df.format(f_watts) + " W";
                String date = json_object.getString("date_text");
                cons.add(new ConsumptionNumerical(id, wattage_consumed, date));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        this.mConsumptionList = cons;
    }

    @Override
    public int getCount() {
        return mConsumptionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mConsumptionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.text_numerical_values, null);
        TextView date = view.findViewById(R.id.numeric_date);
        TextView consumption = view.findViewById(R.id.numeric_consumption);
        consumption.setText(mConsumptionList.get(position).getConsumption());
        date.setText(mConsumptionList.get(position).getDate());
        view.setTag(mConsumptionList.get(position).getId());
        return view;
    }

    class ConsumptionNumerical {

        int id;
        String date;
        String consumption;

        ConsumptionNumerical(int id, String consumption, String date){
            this.id = id;
            this.date = date;
            this.consumption = consumption;
        }

        int getId() {
            return id;
        }

        void setId(int id) {
            this.id = id;
        }

        String getDate() {
            return date;
        }

        void setDate(String date) {
            this.date = date;
        }

        String getConsumption() {
            return consumption;
        }

        void setConsumption(String consumption) {
            this.consumption = consumption;
        }

    }

}


