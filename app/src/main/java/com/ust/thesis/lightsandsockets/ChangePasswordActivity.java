package com.ust.thesis.lightsandsockets;

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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText opassword;
    EditText npassword;
    EditText cpassword;
    CheckBox show;
    Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Initialize();
        ShowPassword();
        EnableCheckBox();
        changePassword();
    }

    public void Initialize(){
        opassword = (EditText) findViewById(R.id.old_password);
        npassword = (EditText) findViewById(R.id.new_password);
        cpassword = (EditText) findViewById(R.id.confirm_password);
        show = (CheckBox) findViewById(R.id.checkBox);
        change = (Button) findViewById(R.id.changePasswordButton);
        show.setEnabled(false);
    }

    public void ShowPassword(){
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show.isChecked()){
                    opassword.setInputType(InputType.TYPE_CLASS_TEXT );
                    npassword.setInputType(InputType.TYPE_CLASS_TEXT );
                    cpassword.setInputType(InputType.TYPE_CLASS_TEXT );
                }else{
                    opassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                    opassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    npassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD );
                    npassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    cpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                    cpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void EnableCheckBox(){
        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!opassword.getText().toString().equals("") || !npassword.getText().toString().equals("") || !cpassword.getText().toString().equals("")) {
                    show.setEnabled(true);
                }else{
                    show.setEnabled(false);
                }
            }

        };
        opassword.addTextChangedListener(fieldValidatorTextWatcher);
        npassword.addTextChangedListener(fieldValidatorTextWatcher);
        cpassword.addTextChangedListener(fieldValidatorTextWatcher);
    }

    public void changePassword(){
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*DO THINGS TO CHANGE PASSWORD*/
            }
        });
    }

}
