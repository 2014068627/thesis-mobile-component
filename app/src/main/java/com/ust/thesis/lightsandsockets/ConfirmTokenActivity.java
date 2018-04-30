package com.ust.thesis.lightsandsockets;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSession;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ConfirmTokenActivity extends AppCompatActivity {

    Button nextActivity;
    EditText token;
    Context context;

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
        context = getApplicationContext();
    }

    private void toConfirmTokenActivity(){
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stoken = token.getText().toString();

                String url = getString(R.string.apiserver) + "api/powerboard/confirm_token";
                HashMap json_token = new HashMap();
                json_token.put("token", stoken);
                tokenRequest(url, json_token);
            }
        });
    }

    /**
     * function to confirm token from email to API
     */
    private void tokenRequest(String url, final HashMap json_token){
        final ProgressDialog api_dialog = new ProgressDialog(this);
        api_dialog.setMessage(getString(R.string.api_wait));
        api_dialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(json_token), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
                    String message = json_response.getString("message");
                    if(success){
                        JSONObject json_forgot = response.getJSONObject("forgot");
                        String second_token = json_forgot.getString("second_token");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("second_token", second_token);

                        Intent intent = new Intent(ConfirmTokenActivity.this, ChangeForgottenPasswordActivity.class);
                        intent.putExtras(bundle);
                        api_dialog.dismiss();
                        startActivity(intent);
                    }else{
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    Toast.makeText(context, "Something occured, try again later.", Toast.LENGTH_SHORT).show();
                }
                api_dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                api_dialog.dismiss();
            }
        });
        LSingleton.getInstance(context).addToRequestQueue(request);
    }

    /**
     * user pressed back to logout
     * https://stackoverflow.com/questions/6413700/android-proper-way-to-use-onbackpressed-with-toast
     */
    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Do you want to cancel?")
                .setMessage("Are you sure you want to cancel?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ConfirmTokenActivity.super.onBackPressed();

                        //https://stackoverflow.com/questions/2776830/android-moving-back-to-first-activity-on-button-click
                        Intent intent = new Intent( context, LoginActivity.class );
                        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        startActivity( intent );
                    }
                }).create().show();
    }
}
