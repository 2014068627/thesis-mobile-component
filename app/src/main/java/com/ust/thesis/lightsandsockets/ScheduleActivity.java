package com.ust.thesis.lightsandsockets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.ust.thesis.lightsandsockets.objects.ScheduleActivityAdapter;

public class ScheduleActivity extends AppCompatActivity {

    NumberPicker NPHour;
    NumberPicker NPMin;
    Button scheduleButton;
    Button resetButton;
    Button cancelButton;
    TextView socketNumber;

    private ListView lv;
    private ScheduleActivityAdapter saa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Initialize();

        Bundle bundle = getIntent().getExtras();
        String socket = bundle.getString("socket"); /*Contains the name of the socket currently selected"*/
        socketNumber.setText(socket);

        OnClickActivities(socket, bundle.getString("appliance"));
    }

    public void Initialize(){
        NPHour = (NumberPicker) findViewById(R.id.NPHour);
        NPMin = (NumberPicker) findViewById(R.id.NPMin);
        scheduleButton = (Button) findViewById(R.id.scheduleButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        socketNumber = (TextView) findViewById(R.id.socketNumber);

        NPHour.setMaxValue(23);
        NPHour.setMinValue(0);
        NPMin.setMaxValue(59);
        NPMin.setMinValue(0);

    }

    public void OnClickActivities(String number, String appliance){
        final String Snumber = number;
        final String Sappliance = appliance;
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Do something to schedule*/
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NPHour.setValue(0);
                NPMin.setValue(0);
                /*CHANGE VALUES OF SCHEDULE TO 0*/
                /**Intent myIntent = new Intent(ScheduleActivity.this, SocketActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("socket", Snumber);
                bundle.putString("appliance", Sappliance);
                myIntent.putExtras(bundle);
                startActivity(myIntent);**/
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ScheduleActivity.this, SocketActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("socket", Snumber);
                bundle.putString("appliance", Sappliance); /*temporary*/
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
    }

}
