package com.ust.thesis.lightsandsockets;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class forgotPassword extends AppCompatActivity {

    EditText email;
    Button nextActivity;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initialize();
        toConfirmTokenActivity();
    }

    private void initialize(){
        email = findViewById(R.id.email);
        nextActivity = findViewById(R.id.nextActivity);
        context = getApplicationContext();
    }

    private void toConfirmTokenActivity(){
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_email = email.getText().toString();
                String url = getString(R.string.apiserver) + "api/powerboard/forgot_password";
                HashMap json_email = new HashMap();
                json_email.put("email", s_email);
                emailRequest(url, json_email);
            }
        });
    }

    /**
     * function to send API to server for email forgot password
     */
    private void emailRequest(String url, HashMap json_email){
        final ProgressDialog api_dialog = new ProgressDialog(this);
        api_dialog.setMessage(getString(R.string.api_wait));
        api_dialog.show();

        JsonObjectRequest request = (JsonObjectRequest) new JsonObjectRequest(Request.Method.POST, url, new JSONObject(json_email), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
                    String message = json_response.getString("message");
                    if(success){
                        Intent myIntent = new Intent(forgotPassword.this, ConfirmTokenActivity.class);
                        api_dialog.dismiss();
                        startActivity(myIntent);
                    }else{
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    Toast.makeText(context, "Something occurred, Try again later.", Toast.LENGTH_SHORT).show();
                }
                api_dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                api_dialog.dismiss();
            }
        })      // https://stackoverflow.com/questions/6330260/finish-all-previous-activities
                .setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        LSingleton.getInstance(context).addToRequestQueue(request);
    }

}
