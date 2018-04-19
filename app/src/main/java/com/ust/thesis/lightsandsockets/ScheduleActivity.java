package com.ust.thesis.lightsandsockets;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSession;
import com.ust.thesis.lightsandsockets.objects.LSingleton;
import com.ust.thesis.lightsandsockets.objects.ScheduleActivityAdapter;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ScheduleActivity extends AppCompatActivity {

    NumberPicker NPHour;
    NumberPicker NPMin;
    Button scheduleButton;
    Button resetButton;
    Button cancelButton;
    TextView socketNumber;
    TextView dynamic_sched;
    Bundle bundle;

    Context context;

    char socket_id;
    String user_id;
    String user_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initialize();
        bundle = getIntent().getExtras();
        //get socket id
        String socket_name = bundle.getString("socket"); /*Contains the name of the socket currently selected"*/
        socket_id = socket_name.charAt(socket_name.length() - 1);
        //get userid and username
        LSession user_session = new LSession();
        String [] user_arr = user_session.getUserSession(context);
        user_id = user_arr[0];
        user_username = user_arr[1];

        socketNumber.setText(socket_name);

        onClickActivities();
        onChangeNumberPicker();
    }

    /**
     * initialize views
     */
    public void initialize(){
        NPHour = findViewById(R.id.NPHour);
        NPMin = findViewById(R.id.NPMin);
        scheduleButton = findViewById(R.id.scheduleButton);
        cancelButton = findViewById(R.id.cancelButton);
        resetButton = findViewById(R.id.resetButton);
        socketNumber = findViewById(R.id.socketNumber);
        dynamic_sched = findViewById(R.id.dynamic_sched);
        context = getApplicationContext();
        npFormatter(NPHour);
        npFormatter(NPMin);

        NPHour.setMaxValue(23);
        NPHour.setMinValue(0);
        NPMin.setMaxValue(59);
        NPMin.setMinValue(0);
        getDateTimeSched();
    }

    /**
     * function for clicked buttons
     */
    public void onClickActivities(){
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = new LocalTime(NPHour.getValue(), NPMin.getValue()).toString(DateTimeFormat.forPattern("HH:mm:ss"));
//                Toast.makeText(context, time, Toast.LENGTH_SHORT).show();
                HashMap json_sched = new HashMap();
                json_sched.put("socket", String.valueOf(socket_id));
                json_sched.put("switch", "off");
                json_sched.put("user_id", user_id);
                json_sched.put("user_username", user_username);
                json_sched.put("time", time);
                String url = getString(R.string.apiserver) + "api/powerboard/schedule";
                scheduleRequest(url, json_sched);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NPHour.setValue(0);
                NPMin.setValue(0);
                getDateTimeSched();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * function for change in number picker
     */
    public void onChangeNumberPicker(){
        NPHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getDateTimeSched();
            }
        });
        NPMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getDateTimeSched();
            }
        });
    }


    /**
     * format NumberPicker for Leading Zeros
     */
    public void npFormatter(NumberPicker np){
        np.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
    }

    /**
     * function to show the dynamic_sched the datetime scheduled
     */
    private void getDateTimeSched(){
        int hour = NPHour.getValue();
        int min = NPMin.getValue();
        LocalTime time_sched = new LocalTime(hour, min);
        LocalTime time_now = new LocalTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MMM dd HH:mm");
        DateTime datetime;
        if(time_sched.isBefore(time_now)){
            LocalDate date_now = new LocalDate().plusDays(1);
            datetime = date_now.toDateTime(time_sched);
        }else{
            LocalDate date_sched = new LocalDate();
            datetime = date_sched.toDateTime(time_sched);
        }
        dynamic_sched.setText(datetime.toString(formatter));
    }

    private void scheduleRequest(String url, HashMap json_sched){
        //dialog box for loading
        final ProgressDialog api_dialog = new ProgressDialog(this);
        api_dialog.setMessage(getString(R.string.api_wait));
        api_dialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(json_sched), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
                    if(success){
                        String message = json_response.getString("message");
                        Intent intent = new Intent();
                        intent.putExtra("message", message);
                        setResult(100, intent);
                    }else{
                        Toast.makeText(context, "There is an error, Try again later.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(context, "There is an error, Try again later!", Toast.LENGTH_SHORT).show();
                }
                api_dialog.dismiss();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                api_dialog.dismiss();
            }
        });
        LSingleton.getInstance(context).addToRequestQueue(request);
    }
}
