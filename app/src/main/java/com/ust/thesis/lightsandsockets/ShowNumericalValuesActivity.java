package com.ust.thesis.lightsandsockets;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ust.thesis.lightsandsockets.objects.ConsumptionNumericalValuesAdapter;
import com.ust.thesis.lightsandsockets.objects.ConsumptionNumericalValuesAdapter.*;

import java.util.ArrayList;
import java.util.List;

public class ShowNumericalValuesActivity extends AppCompatActivity {

    TextView socketNumber;
    TextView  Iappliance;
    Button weeklyButton;
    Button dailyButton;
    Button showNV;
    Context context;

    private ListView lv;
    private ConsumptionNumericalValuesAdapter cnva;
    private List<ConsumptionNumerical> mConsumptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_numerical_values);

        socketNumber = (TextView) findViewById(R.id.socket_number);
        Iappliance = (TextView) findViewById(R.id.identifiedDevice);
        showNV = (Button) findViewById(R.id.showNV);
        weeklyButton = (Button) findViewById(R.id.WeeklyButton);
        dailyButton = (Button) findViewById(R.id.DailyButton);
        context = getApplicationContext();
        Bundle bundle = getIntent().getExtras();
        String socket = bundle.getString("socket"); /*Contains the name of the socket currently selected"*/
        String appliance = bundle.getString("appliance"); /*Temporary device identification*/
        socketNumber.setText(socket);
        Iappliance.setText(appliance);

        lv = (ListView) findViewById(R.id.listViewNumerical);
        mConsumptionList = new ArrayList<>();

        mConsumptionList.add(new ConsumptionNumerical(1, "2/2/2018", "300 W"));
        cnva = new ConsumptionNumericalValuesAdapter(context, mConsumptionList);
        lv.setAdapter(cnva);

        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyButton.setBackgroundColor(getResources().getColor(R.color.white));
                dailyButton.setTextColor(getResources().getColor(R.color.maroon));
                weeklyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
                weeklyButton.setTextColor(getResources().getColor(R.color.peach));
                lv.setAdapter(null);
                mConsumptionList.clear();
                mConsumptionList.add(new ConsumptionNumerical(1, "2/2/2018", "300 W"));
                cnva = new ConsumptionNumericalValuesAdapter(context, mConsumptionList);
                lv.setAdapter(cnva);
            }
        });

        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeklyButton.setBackgroundColor(getResources().getColor(R.color.white));
                weeklyButton.setTextColor(getResources().getColor(R.color.maroon));
                dailyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
                dailyButton.setTextColor(getResources().getColor(R.color.peach));
                lv.setAdapter(null);
                mConsumptionList.clear();
                mConsumptionList.add(new ConsumptionNumerical(1, "2/2/2018", "600 W"));
                mConsumptionList.add(new ConsumptionNumerical(1, "2/2/2018", "900 W"));
                cnva = new ConsumptionNumericalValuesAdapter(context, mConsumptionList);
                lv.setAdapter(cnva);
            }
        });
    }

}
