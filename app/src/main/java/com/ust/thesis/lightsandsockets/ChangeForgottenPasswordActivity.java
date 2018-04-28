package com.ust.thesis.lightsandsockets;

import android.content.Context;
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

public class ChangeForgottenPasswordActivity extends AppCompatActivity {

    EditText new_password;
    EditText confirm_password;
    CheckBox show;
    Button change;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_forgotten_password);
        initialize();
        enableCheckBox();
        showPassword();
    }

    public void initialize(){
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);
        show = findViewById(R.id.checkBox);
        change = findViewById(R.id.changePasswordButton);
        context = getApplicationContext();
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
}
