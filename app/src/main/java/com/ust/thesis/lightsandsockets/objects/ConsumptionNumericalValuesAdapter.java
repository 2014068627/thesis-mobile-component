package com.ust.thesis.lightsandsockets.objects;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ust.thesis.lightsandsockets.R;

import java.util.List;

/**
 * Created by BINS on 12/04/2018.
 */

public class ConsumptionNumericalValuesAdapter extends BaseAdapter{

    private Context mContext;
    private List<ConsumptionNumerical> mConsumptionList;

    public ConsumptionNumericalValuesAdapter(Context mContext, List<ConsumptionNumerical> mActivityList) {
        this.mContext = mContext;
        this.mConsumptionList = mActivityList;
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
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView consumption = (TextView) view.findViewById(R.id.consumption);
        consumption.setText(mConsumptionList.get(position).getConsumption());
        date.setText(mConsumptionList.get(position).getDate());
        view.setTag(mConsumptionList.get(position).getId());
        return view;
    }

    public static class ConsumptionNumerical {

        private int id;
        private String date;
        private String consumption;

        public ConsumptionNumerical(int id, String date, String consumption){
            this.id = id;
            this.date = date;
            this.consumption = consumption;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getConsumption() {
            return consumption;
        }

        public void setConsumption(String consumption) {
            this.consumption = consumption;
        }

    }

}


