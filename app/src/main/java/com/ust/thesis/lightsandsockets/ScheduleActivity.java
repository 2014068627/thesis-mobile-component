package com.ust.thesis.lightsandsockets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.ust.thesis.lightsandsockets.objects.ScheduleActivityAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {

    NumberPicker NPHour;
    NumberPicker NPMin;
    Button scheduleButton;
    Button resetButton;
    Button cancelButton;
    TextView socketNumber;
    Bundle bundle;

//    ListView lv;
//    private ScheduleActivityAdapter saa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initialize();
        bundle = getIntent().getExtras();
        String socket = bundle.getString("socket"); /*Contains the name of the socket currently selected"*/
        socketNumber.setText(socket);

        onClickActivities();
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

        NPHour.setMaxValue(23);
        NPHour.setMinValue(0);
        NPMin.setMaxValue(59);
        NPMin.setMinValue(0);
        npFormatter(NPHour);
        npFormatter(NPMin);
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
     * function for clicked buttons
     */
    public void onClickActivities(){
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                DateFormat dformat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();

                int hour = NPHour.getValue();
                int min = NPMin.getValue();
                String time = String.format("%02d", hour) + ":" + String.format("%02d", min);

                try {
                    Date time_picked = new SimpleDateFormat("HH:mm").parse(time);

                    Toast.makeText(getApplicationContext(), dformat.format(time_picked), Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NPHour.setValue(0);
                NPMin.setValue(0);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
