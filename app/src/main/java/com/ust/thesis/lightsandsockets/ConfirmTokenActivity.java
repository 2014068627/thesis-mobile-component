package com.ust.thesis.lightsandsockets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfirmTokenActivity extends AppCompatActivity {

    Button nextActivity;
    EditText token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_token);
        initialize();
        toConfirmTokenActivity();
    }

    private void initialize(){
        nextActivity = findViewById(R.id.nextActivity);
        token = findViewById(R.id.token);
    }

    private void toConfirmTokenActivity(){
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ConfirmTokenActivity.this, ChangeForgottenPasswordActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
