package com.ust.thesis.lightsandsockets;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ChangeForgottenPasswordActivity extends AppCompatActivity {

    EditText new_password;
    EditText confirm_password;
    CheckBox show;
    Button change;
    Context context;
    Bundle bundle;

    String second_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_forgotten_password);
        initialize();
        second_token = bundle.getString("second_token");

        enableCheckBox();
        showPassword();
        buttonConfirmPassword();
    }

    public void initialize(){
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);
        show = findViewById(R.id.checkBox);
        change = findViewById(R.id.changePasswordButton);
        context = getApplicationContext();
        bundle = getIntent().getExtras();
        show.setEnabled(false);
    }

    public void showPassword(){
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show.isChecked()){
                    new_password.setInputType(InputType.TYPE_CLASS_TEXT );
                    confirm_password.setInputType(InputType.TYPE_CLASS_TEXT );
                }else{
                    new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD );
                    new_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                    confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void enableCheckBox(){
        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!new_password.getText().toString().equals("") || !confirm_password.getText().toString().equals("")) {
                    show.setEnabled(true);
                }else{
                    show.setEnabled(false);
                }
            }
        };
        new_password.addTextChangedListener(fieldValidatorTextWatcher);
        confirm_password.addTextChangedListener(fieldValidatorTextWatcher);
    }

    public void buttonConfirmPassword(){
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_pass = new_password.getText().toString();
                String con_pass = confirm_password.getText().toString();
                if(new_pass.equals(con_pass) && new_pass.length() > 3 && new_pass.length() < 50){
                    String url = getString(R.string.apiserver) + "api/powerboard/forgot_change";
                    HashMap json_changeforgot = new HashMap();
                    json_changeforgot.put("second_token", second_token);
                    json_changeforgot.put("new_password", new_pass);
                    json_changeforgot.put("con_password", con_pass);
                    changeforgotRequest(url, json_changeforgot);
                }else{
                    if(!new_pass.equals(con_pass)){
                        Toast.makeText(context, "New Password does not match", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Password must not be less than 4 characters", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void changeforgotRequest(String url, HashMap json_changeforgot){
        final ProgressDialog api_dialog = new ProgressDialog(this);
        api_dialog.setMessage(getString(R.string.api_wait));
        api_dialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(json_changeforgot), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
                    String message = json_response.getString("message");
                    if(success){
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        //https://stackoverflow.com/questions/2776830/android-moving-back-to-first-activity-on-button-click
                        Intent intent = new Intent( context, LoginActivity.class );
                        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        startActivity( intent );
                    }else{
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    Toast.makeText(context, "Something occurred, Try again later", Toast.LENGTH_SHORT).show();
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
                        ChangeForgottenPasswordActivity.super.onBackPressed();

                        //https://stackoverflow.com/questions/2776830/android-moving-back-to-first-activity-on-button-click
                        Intent intent = new Intent( context, LoginActivity.class );
                        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        startActivity( intent );
                    }
                }).create().show();
    }
}
