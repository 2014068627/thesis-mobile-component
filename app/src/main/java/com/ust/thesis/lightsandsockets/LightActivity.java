package com.ust.thesis.lightsandsockets;

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

public class LightActivity extends AppCompatActivity {

    Button weeklyButton;
    Button dailyButton;
    Button showNV;
    Button Percent0;
    Button Percent25;
    Button Percent50;
    Button Percent75;
    Button Percent100;
    private LightGraphDailyFragment DF;
    private LightGraphWeeklyFragment WF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        initialize();
        switchGraphs();
        lightBrightness();
        goBack();
    }

    public void initialize(){
        DF = new LightGraphDailyFragment();
        WF = new LightGraphWeeklyFragment();
        weeklyButton = (Button) findViewById(R.id.WeeklyButton);
        dailyButton = (Button) findViewById(R.id.DailyButton);
        showNV = (Button) findViewById(R.id.showNV);
        Percent0 = (Button) findViewById(R.id.Percent0);
        Percent25 = (Button) findViewById(R.id.Percent25);
        Percent50 = (Button) findViewById(R.id.Percent50);
        Percent75 = (Button) findViewById(R.id.Percent75);
        Percent100 = (Button) findViewById(R.id.Percent100);
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
                Intent myIntent = new Intent(LightActivity.this, ShowLightNumericalValuesActivity.class);
                startActivity(myIntent);
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
        button.setBackgroundColor(getResources().getColor(R.color.lineColor));
        button.setTextColor(getResources().getColor(R.color.white));
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

}
