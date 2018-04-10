package com.ust.thesis.lightsandsockets.objects;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ust.thesis.lightsandsockets.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reinier on 4/10/2018.
 */

public class ActivitiesAdapter extends BaseAdapter {

    private Context context;
    private List<Activities> listActivities;

    public ActivitiesAdapter(Context context, JSONArray json_array){
        this.context = context;
        List<Activities> act = new ArrayList<Activities>();
        try{
            for(int i=0; i<json_array.length(); i++){
                JSONObject jobject = json_array.getJSONObject(i);
                int id = Integer.parseInt(jobject.getString("id"));
                String activity = jobject.getString("user_activity");
                String username = jobject.getString("username");
                String date = jobject.getString("date_time");
                act.add(new Activities(id, activity, username, date));
            }
        }catch(Exception e){

        }
        this.listActivities = act;
    }




    @Override
    public int getCount(){
        return listActivities.size();
    }

    @Override
    public Object getItem(int position){
        return listActivities.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = View.inflate(context, R.layout.text_recent_activities, null);
        TextView activity = (TextView) view.findViewById(R.id.activity);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView username = (TextView) view.findViewById(R.id.username);
        activity.setText(listActivities.get(position).getActivity());
        username.setText(listActivities.get(position).getUsername());
        time.setText(listActivities.get(position).getTime());
        view.setTag(listActivities.get(position).getId());

        return view;
    }


    class Activities{
        private int id;
        private String activity;
        private String time;
        private String username;

        public Activities(int id, String activity, String username, String time){
            this.id = id;
            this.activity = activity;
            this.time = time;
            this.username = username;
        }

        public int getId() {
            return id;
        }

        public String getActivity() {
            return activity;
        }

        public String getTime() {
            return time;
        }

        public String getUsername() {
            return username;
        }
    }
}
