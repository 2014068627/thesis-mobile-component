package com.ust.thesis.lightsandsockets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class forgotPassword extends AppCompatActivity {

    EditText email;
    Button nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initialize();
        toConfirmTokenActivity();
    }

    private void initialize(){
        email = (EditText) findViewById(R.id.email);
        nextActivity = (Button) findViewById(R.id.nextActivity);
    }

    private void toConfirmTokenActivity(){
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(forgotPassword.this, ConfirmTokenActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
