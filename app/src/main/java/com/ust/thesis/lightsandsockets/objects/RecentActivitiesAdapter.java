package com.ust.thesis.lightsandsockets.objects;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ust.thesis.lightsandsockets.R;

import java.util.List;

/**
 * Created by BINS on 09/04/2018.
 */

public class RecentActivitiesAdapter extends BaseAdapter {

    private Context mContext;
    private List<RecentActivities> mActivityList;

    public RecentActivitiesAdapter(Context mContext, List<RecentActivities> mActivityList) {
        this.mContext = mContext;
        this.mActivityList = mActivityList;
    }

    @Override
    public int getCount() {
        return mActivityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mActivityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.text_recent_activities, null);
        TextView activity = (TextView) view.findViewById(R.id.date);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView username = (TextView) view.findViewById(R.id.username);
        activity.setText(mActivityList.get(position).getActivity());
        time.setText(mActivityList.get(position).getTime());
        username.setText(mActivityList.get(position).getUsername());

        view.setTag(mActivityList.get(position).getId());
        return view;
    }
}
