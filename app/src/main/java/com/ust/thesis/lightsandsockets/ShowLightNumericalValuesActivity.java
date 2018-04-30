package com.ust.thesis.lightsandsockets;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.ConsumptionNumericalValuesAdapter;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowLightNumericalValuesActivity extends AppCompatActivity {

    Button weeklyButton;
    Button dailyButton;
    Button showNV;
    Context context;
    ListView lv;
    Bundle bundle;

    String socket_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_light_numerical_values);

        initialize();
        socket_id = bundle.getString("socket_id");
        numericClicked(dailyButton);
        goBack();

        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDailyInfo();
            }
        });

        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWeeklyInfo();
            }
        });
    }

    public void initialize(){
        showNV = findViewById(R.id.showNV);
        weeklyButton = findViewById(R.id.WeeklyButton);
        dailyButton = findViewById(R.id.DailyButton);
        context = getApplicationContext();
        bundle = getIntent().getExtras();

        lv = findViewById(R.id.listViewNumerical);
    }

    /**
     * function to compare button clicked for API Request
     */
    private void numericClicked(Button numeric_button){
        String url = getString(R.string.apiserver) + "api/powerboard/";
        switch(numeric_button.getId()){
            case R.id.DailyButton:
                url += "daily_consumed/" + socket_id;
                numericRequest(url);
                break;
            case R.id.WeeklyButton:
                url += "weekly_consumed/" + socket_id;
                numericRequest(url);
                break;
            default:
        }
    }

    public void addDailyInfo(){
        dailyButton.setBackgroundColor(getResources().getColor(R.color.white));
        dailyButton.setTextColor(getResources().getColor(R.color.maroon));
        weeklyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
        weeklyButton.setTextColor(getResources().getColor(R.color.peach));
        lv.setAdapter(null);
        numericClicked(dailyButton);
    }

    public void AddWeeklyInfo(){
        weeklyButton.setBackgroundColor(getResources().getColor(R.color.white));
        weeklyButton.setTextColor(getResources().getColor(R.color.maroon));
        dailyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
        dailyButton.setTextColor(getResources().getColor(R.color.peach));
        lv.setAdapter(null);
        numericClicked(weeklyButton);
    }

    private void goBack(){
        ImageButton bttn = findViewById(R.id.backButton);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * function for API Request for numerical data
     */
    private void numericRequest(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
                    String socket_data = json_response.getString("socket_data");
                    if(success){
                        ConsumptionNumericalValuesAdapter ocnva;
                        if(socket_data.equals("daily")){
                            JSONArray json_daily_array = response.getJSONArray("daily_consumed");
                            ocnva = new ConsumptionNumericalValuesAdapter(context, json_daily_array);
                        }else{
                            JSONArray json_weekly_array = response.getJSONArray("weekly_consumed");
                            ocnva = new ConsumptionNumericalValuesAdapter(context, json_weekly_array);
                        }
                        lv.setAdapter(ocnva);
                    }else{
                        Toast.makeText(context,"Something occurred, Try again!",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something occurred, Try again!",Toast.LENGTH_SHORT).show();
            }
        });
        LSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(request);
    }
}
