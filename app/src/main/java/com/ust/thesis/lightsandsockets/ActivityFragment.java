package com.ust.thesis.lightsandsockets;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ust.thesis.lightsandsockets.objects.RecentActivities;
import com.ust.thesis.lightsandsockets.objects.RecentActivitiesAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment {

    private ListView lv;
    private RecentActivitiesAdapter raa;
    private List<RecentActivities> mActivityList;

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        lv = (ListView) view.findViewById(R.id.listView);
        mActivityList = new ArrayList<>();

        mActivityList.add(new RecentActivities(1, "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making", "2/2/2018", "user"));
        mActivityList.add(new RecentActivities(1, "Schedule socket 2 to turn off", "3/3/2018", "apple"));
        mActivityList.add(new RecentActivities(1, "Schedule socket 3 to turn off", "4/4/2018", "orange"));
        mActivityList.add(new RecentActivities(1, "Schedule socket 4 to turn off", "5/5/2018", "something"));
        mActivityList.add(new RecentActivities(1, "Schedule socket 2 to turn off", "6/6/2018", "user2"));
        mActivityList.add(new RecentActivities(1, "Schedule socket 1 to turn off", "7/7/2018", "banana"));

        raa = new RecentActivitiesAdapter(getContext(), mActivityList);
        lv.setAdapter(raa);
        return view;
    }

}
