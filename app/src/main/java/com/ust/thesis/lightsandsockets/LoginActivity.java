package com.ust.thesis.lightsandsockets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button fgPasswordBttn;
    private Button loginBttn;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login();
        toFGmenu();
    }

    public void toFGmenu(){
        fgPasswordBttn = (Button) findViewById(R.id.fpbutton);
        fgPasswordBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, forgotPassword.class);
                startActivity(myIntent);
            }
        });
    }

    public void Login(){
        loginBttn = (Button) findViewById(R.id.login);
        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameEdit = (EditText) findViewById(R.id.username);
                passwordEdit = (EditText) findViewById(R.id.password);
                username = usernameEdit.getText().toString();
                password = passwordEdit.getText().toString();
                Intent myIntent = new Intent(LoginActivity.this, fragmentContainer.class);
                if(username.equals("username") && password.equals("password")){
                    startActivity(myIntent);
                }
            }
        });
    }

}
