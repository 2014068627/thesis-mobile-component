package com.ust.thesis.lightsandsockets;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ust.thesis.lightsandsockets.objects.LSingleton;

import org.json.JSONObject;

import java.util.HashMap;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText old_password;
    EditText new_password;
    EditText confirm_password;
    CheckBox show;
    Button change;
    Context context;

    Bundle bundle;
    String username;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initialize();
        showPassword();
        enableCheckBox();
        changePassword();
        goBack();
    }

    /**
     * function to initialize UI
     */
    public void initialize(){
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);
        show = findViewById(R.id.checkBox);
        change = findViewById(R.id.changePasswordButton);
        context = getApplicationContext();
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        id = bundle.getString("id");

        show.setEnabled(false);
    }

    /**
     * function for clicking the checkbox for show password
     */
    public void showPassword(){
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show.isChecked()){
                    old_password.setInputType(InputType.TYPE_CLASS_TEXT );
                    new_password.setInputType(InputType.TYPE_CLASS_TEXT );
                    confirm_password.setInputType(InputType.TYPE_CLASS_TEXT );
                }else{
                    old_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                    old_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD );
                    new_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                    confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    /**
     * function to check if checkbox is enabled to call the showPassword()
     */
    public void enableCheckBox(){
        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!old_password.getText().toString().equals("") || !new_password.getText().toString().equals("") || !confirm_password.getText().toString().equals("")) {
                    show.setEnabled(true);
                }else{
                    show.setEnabled(false);
                }
            }

        };
        old_password.addTextChangedListener(fieldValidatorTextWatcher);
        new_password.addTextChangedListener(fieldValidatorTextWatcher);
        confirm_password.addTextChangedListener(fieldValidatorTextWatcher);
    }

    /**
     * function for button to change password
     */
    public void changePassword(){
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_pass = old_password.getText().toString();
                String new_pass = new_password.getText().toString();
                String con_pass = confirm_password.getText().toString();
                if(old_pass.length() > 3 && old_pass.length() < 50 && new_pass.length() > 3 && new_pass.length() < 50){
                    if(!new_pass.equals(old_pass)){
                        if(new_pass.equals(con_pass)){
                            HashMap map_change = new HashMap();
                            map_change.put("user_id", id);
                            map_change.put("user_username", username);
                            map_change.put("old_password", old_pass);
                            map_change.put("new_password", new_pass);
                            map_change.put("con_password", con_pass);

                            String url = getString(R.string.apiserver) + "api/powerboard/changepassword";
                            changePasswordRequest(url, map_change);
                        }else{
                            Toast.makeText(context, "Confirm password is not the same with new password.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "New password should not be same with old password.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Password should not be less than 4 characters", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * function for button back in
     */
    private void goBack(){
        ImageButton button_back = findViewById(R.id.backButton);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("message", "change password activity");
                setResult(300, intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("message", "change password activity");
        setResult(300, intent);
        finish();
    }

    /**
     * function to request API from server and to POST new password
     */
    public void changePasswordRequest(String url, HashMap json_change){
        final ProgressDialog api_dialog = new ProgressDialog(this);
        api_dialog.setMessage(getString(R.string.api_wait));
        api_dialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(json_change), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
                    String message = json_response.getString("message");
                    if(success){
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        JSONObject json_user = response.getJSONObject("user");
                        boolean changed_password = json_user.getBoolean("changed_password");
                        if(changed_password){
                            Intent intent = new Intent();
                            intent.putExtra("message", "change password activity");
                            setResult(300, intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(context, "Something occured, Try again :(", Toast.LENGTH_SHORT).show();
                }
                api_dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                api_dialog.dismiss();
            }
        });
        LSingleton.getInstance(context).addToRequestQueue(request);
    }
}
