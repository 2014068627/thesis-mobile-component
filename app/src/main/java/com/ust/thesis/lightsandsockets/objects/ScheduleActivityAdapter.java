package com.ust.thesis.lightsandsockets.objects;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.ust.thesis.lightsandsockets.R;

import java.util.List;

/**
 * Created by BINS on 14/04/2018.
 */

public class ScheduleActivityAdapter extends BaseAdapter {


    private Context mContext;
    private List<ScheduleSocket> mScheduleList;

    public ScheduleActivityAdapter(Context mContext, List<ScheduleSocket> mActivityList) {
        this.mContext = mContext;
        this.mScheduleList = mActivityList;
    }

    @Override
    public int getCount() {
        return mScheduleList.size();
    }

    @Override
    public Object getItem(int position) {
        return mScheduleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.activity_schedule, null);
        /*TextView date = (TextView) view.findViewById(R.id.date);
        TextView consumption = (TextView) view.findViewById(R.id.consumption);
        consumption.setText(mConsumptionList.get(position).getConsumption());
        date.setText(mConsumptionList.get(position).getDate());
        view.setTag(mConsumptionList.get(position).getId());*/
        NumberPicker NPHour = (NumberPicker) view.findViewById(R.id.NPHour);
        NumberPicker NPMin = (NumberPicker) view.findViewById(R.id.NPMin);
        NPHour.setValue(Integer.parseInt(mScheduleList.get(position).getHour()));
        NPMin.setValue(Integer.parseInt(mScheduleList.get(position).getMin()));
        return view;
    }

    public static class ScheduleSocket {

        private int id;
        private String hour;
        private String min;

        public ScheduleSocket(int id, String hour, String min){
            this.id = id;
            this.hour = hour;
            this.min = min;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }
    }

}
