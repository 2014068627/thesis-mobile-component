package com.ust.thesis.lightsandsockets;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSession;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LightActivity extends AppCompatActivity {

    Button weeklyButton;
    Button dailyButton;
    Button showNV;
    Button Percent0;
    Button Percent25;
    Button Percent50;
    Button Percent75;
    Button Percent100;
    Context context;
    Bundle bundle;

    char socket_id;
    String brightness;

    private LightGraphDailyFragment DF;
    private LightGraphWeeklyFragment WF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        initialize();

        //give bundle to fragment
        DF.setArguments(bundle);
        WF.setArguments(bundle);

        switchGraphs();
        lightBrightness();
        goBack();
    }

    public void initialize(){
        DF = new LightGraphDailyFragment();
        WF = new LightGraphWeeklyFragment();
        weeklyButton = findViewById(R.id.WeeklyButton);
        dailyButton = findViewById(R.id.DailyButton);
        showNV = findViewById(R.id.showNV);
        Percent0 = findViewById(R.id.Percent0);
        Percent25 = findViewById(R.id.Percent25);
        Percent50 = findViewById(R.id.Percent50);
        Percent75 = findViewById(R.id.Percent75);
        Percent100 = findViewById(R.id.Percent100);
        context = getApplicationContext();
        bundle = getIntent().getExtras();
        socket_id = bundle.getString("socket_id").charAt(0);
        brightness = bundle.getString("brightness");
//        Toast.makeText(context, brightness + " " + socket_id, Toast.LENGTH_SHORT).show();
        setBrightnessButton(brightness);

        directToShowNV(showNV);
        setFragment(DF);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FragmentContainerGraph, fragment);
        fragmentTransaction.commit();
    }

    public void directToShowNV(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LightActivity.this, ShowLightNumericalValuesActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void switchGraphs(){
        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyButton.setBackgroundColor(getResources().getColor(R.color.white));
                dailyButton.setTextColor(getResources().getColor(R.color.maroon));
                weeklyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
                weeklyButton.setTextColor(getResources().getColor(R.color.peach));
                setFragment(DF);
            }
        });

        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeklyButton.setBackgroundColor(getResources().getColor(R.color.white));
                weeklyButton.setTextColor(getResources().getColor(R.color.maroon));
                dailyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
                dailyButton.setTextColor(getResources().getColor(R.color.peach));
                setFragment(WF);
            }
        });
    }

    public void lightBrightness(){
        Percent0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLightBrightness(Percent0);
            }
        });

        Percent25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLightBrightness(Percent25);
            }
        });

        Percent50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLightBrightness(Percent50);
            }
        });

        Percent75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLightBrightness(Percent75);
            }
        });
        Percent100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLightBrightness(Percent100);
            }
        });
    }

    private void buttonLightBrightness(final Button button){


        Percent0.setBackgroundColor(getResources().getColor(R.color.gray));
        Percent0.setTextColor(getResources().getColor(R.color.lineColor));
        Percent25.setBackgroundColor(getResources().getColor(R.color.gray));
        Percent25.setTextColor(getResources().getColor(R.color.lineColor));
        Percent50.setBackgroundColor(getResources().getColor(R.color.gray));
        Percent50.setTextColor(getResources().getColor(R.color.lineColor));
        Percent75.setBackgroundColor(getResources().getColor(R.color.gray));
        Percent75.setTextColor(getResources().getColor(R.color.lineColor));
        Percent100.setBackgroundColor(getResources().getColor(R.color.gray));
        Percent100.setTextColor(getResources().getColor(R.color.lineColor));

        String url = getString(R.string.apiserver) + "api/powerboard/change_brightness";
        String [] user_session = new LSession().getUserSession(context);
        HashMap json_brightness = new HashMap();
        switch(button.getId()){
            case R.id.Percent0:

                json_brightness.put("brightness", "0");
                json_brightness.put("user_id", user_session[0]);
                json_brightness.put("user_username", user_session[1]);
                brightnessRequest(url, json_brightness, button);
                break;
            case R.id.Percent25:
                json_brightness.put("brightness", "25");
                json_brightness.put("user_id", user_session[0]);
                json_brightness.put("user_username", user_session[1]);
                brightnessRequest(url, json_brightness, button);
                break;
            case R.id.Percent50:
                json_brightness.put("brightness", "50");
                json_brightness.put("user_id", user_session[0]);
                json_brightness.put("user_username", user_session[1]);
                brightnessRequest(url, json_brightness, button);
                break;
            case R.id.Percent75:
                json_brightness.put("brightness", "75");
                json_brightness.put("user_id", user_session[0]);
                json_brightness.put("user_username", user_session[1]);
                brightnessRequest(url, json_brightness, button);
                break;
            case R.id.Percent100:
                json_brightness.put("brightness", "100");
                json_brightness.put("user_id", user_session[0]);
                json_brightness.put("user_username", user_session[1]);
                brightnessRequest(url, json_brightness, button);
                break;
        }
    }

    /**
     * function to go back to last activity
     */
    private void goBack(){
        ImageButton button_back = findViewById(R.id.backButton);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setBrightnessButton(String brightness){
        int bright = Integer.parseInt(brightness);
        switch(bright){
            case 0:
                Percent0.setBackgroundColor(getResources().getColor(R.color.lineColor));
                Percent0.setTextColor(getResources().getColor(R.color.white));
                Percent0.setEnabled(false);
                break;
            case 25:
                Percent25.setBackgroundColor(getResources().getColor(R.color.lineColor));
                Percent25.setTextColor(getResources().getColor(R.color.white));
                Percent25.setEnabled(false);
                break;
            case 50:
                Percent50.setBackgroundColor(getResources().getColor(R.color.lineColor));
                Percent50.setTextColor(getResources().getColor(R.color.white));
                Percent50.setEnabled(false);
                break;
            case 75:
                Percent75.setBackgroundColor(getResources().getColor(R.color.lineColor));
                Percent75.setTextColor(getResources().getColor(R.color.white));
                Percent75.setEnabled(false);
                break;
            case 100:
                Percent100.setBackgroundColor(getResources().getColor(R.color.lineColor));
                Percent100.setTextColor(getResources().getColor(R.color.white));
                Percent100.setEnabled(false);
                break;

        }
    }

    /**
     * function to request API from server
     */
    private void brightnessRequest(String url, HashMap json_brightness, final Button button){
        //dialog box for loading
        final ProgressDialog api_dialog = new ProgressDialog(this);
        api_dialog.setMessage(getString(R.string.api_wait));
        api_dialog.show();

        JsonObjectRequest request = (JsonObjectRequest) new JsonObjectRequest(Request.Method.POST, url, new JSONObject(json_brightness), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
                    if(success){
                        Button [] button_id = {Percent0, Percent25, Percent50, Percent75, Percent100};
                        for(int i = 0; i < button_id.length; i++){
                            button_id[i].setEnabled(true);
                        }

                        button.setBackgroundColor(getResources().getColor(R.color.lineColor));
                        button.setTextColor(getResources().getColor(R.color.white));
                        button.setEnabled(false);
                    }
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                }catch(JSONException e){

                }

                api_dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                api_dialog.dismiss();
            }
        })      // https://stackoverflow.com/questions/6330260/finish-all-previous-activities
                .setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        LSingleton.getInstance(context).addToRequestQueue(request);
    }
}
