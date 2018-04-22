package com.ust.thesis.lightsandsockets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ust.thesis.lightsandsockets.objects.ConsumptionNumericalValuesLightAdapter;
import com.ust.thesis.lightsandsockets.objects.ConsumptionNumericalValuesLightAdapter.ConsumptionNumerical;

import java.util.ArrayList;
import java.util.List;

public class ShowLightNumericalValuesActivity extends AppCompatActivity {

    Button weeklyButton;
    Button dailyButton;
    Button showNV;
    Context context;

    private ListView lv;
    private ConsumptionNumericalValuesLightAdapter cnva;
    private List<ConsumptionNumerical> mConsumptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_light_numerical_values);

        initialize();
        AddDailyInfo();
        GoBack();

        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDailyInfo();
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
        showNV = (Button) findViewById(R.id.showNV);
        weeklyButton = (Button) findViewById(R.id.WeeklyButton);
        dailyButton = (Button) findViewById(R.id.DailyButton);
        context = getApplicationContext();

        lv = (ListView) findViewById(R.id.listViewNumerical);
        mConsumptionList = new ArrayList<>();
    }

    public void AddDailyInfo(){
        dailyButton.setBackgroundColor(getResources().getColor(R.color.white));
        dailyButton.setTextColor(getResources().getColor(R.color.maroon));
        weeklyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
        weeklyButton.setTextColor(getResources().getColor(R.color.peach));
        lv.setAdapter(null);
        mConsumptionList.clear();
        mConsumptionList.add(new ConsumptionNumerical(1, "3/3/2018", "300 W"));
        cnva = new ConsumptionNumericalValuesLightAdapter(context, mConsumptionList);
        lv.setAdapter(cnva);
    }

    public void AddWeeklyInfo(){
        weeklyButton.setBackgroundColor(getResources().getColor(R.color.white));
        weeklyButton.setTextColor(getResources().getColor(R.color.maroon));
        dailyButton.setBackgroundColor(getResources().getColor(R.color.maroon));
        dailyButton.setTextColor(getResources().getColor(R.color.peach));
        lv.setAdapter(null);
        mConsumptionList.clear();
        mConsumptionList.add(new ConsumptionNumerical(1, "3/5/2018", "600 W"));
        mConsumptionList.add(new ConsumptionNumerical(1, "6/6/2018", "900 W"));
        cnva = new ConsumptionNumericalValuesLightAdapter(context, mConsumptionList);
        lv.setAdapter(cnva);
    }

    private void GoBack(){
        ImageButton bttn = (ImageButton) findViewById(R.id.backButton);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ShowLightNumericalValuesActivity.this, LightActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
