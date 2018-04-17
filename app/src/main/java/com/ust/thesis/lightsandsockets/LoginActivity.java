package com.ust.thesis.lightsandsockets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSession;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private Button fgPasswordBttn, loginBttn;
    private EditText usernameEdit, passwordEdit;
    private Context appContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appContext = getApplicationContext();
        initialize();
        loginUser();
        toForgotPassword();
    }

    /**
     * function that initializes
     */
    private void initialize(){
        fgPasswordBttn = findViewById(R.id.fpbutton);
        loginBttn = findViewById(R.id.login);
        usernameEdit = findViewById(R.id.profile_username);
        passwordEdit = findViewById(R.id.password);
    }

    /**
     * function to go to activity Forgot password
     */
    private void toForgotPassword(){
        fgPasswordBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, forgotPassword.class);
                startActivity(myIntent);
            }
        });
    }

    /**
     * function to login user using button
     */
    private void loginUser(){
        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String url = getString(R.string.apiserver) + "api/powerboard/login";
                HashMap login_cred = new HashMap();
                login_cred.put("username", username);
                login_cred.put("password", password);
                loginBttn.setEnabled(false);
                loginRequest(url, login_cred);
                loginBttn.setEnabled(true);
            }
        });
    }

    /**
     * function to POST into login API with username and password
     */
    private void loginRequest(String url, HashMap login_cred){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(login_cred), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject jresponse = response.getJSONObject("response");
                    boolean isLogin = jresponse.getBoolean("isLogin");
                    String message = jresponse.getString("message");

                    if(isLogin){
                        JSONObject user = response.getJSONObject("user");
                        String id = user.getString("id");
                        String username = user.getString("username");
                        //session to store user id and username
                        LSession session = new LSession(id, username);
//                        if(session.setSession(appContext));
                        session.setSession(appContext);

                        //clear all text fields
                        usernameEdit.setText("");
                        passwordEdit.setText("");
                        usernameEdit.requestFocus();
                        passwordEdit.requestFocus();

                        //intent to go to next session
                        Intent myIntent = new Intent(LoginActivity.this, fragmentContainer.class);
                        startActivity(myIntent);
                    }else{
                        Toast.makeText(appContext,message,Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(appContext,"AN ERROR OCCURED",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(appContext,"ERROR",Toast.LENGTH_SHORT).show();
            }
        }) {
            // https://stackoverflow.com/questions/43486027/how-to-send-request-header-is-content-typeapplication-json-when-get-on-voll
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        LSingleton.getInstance(appContext).addToRequestQueue(request);
    }

}
