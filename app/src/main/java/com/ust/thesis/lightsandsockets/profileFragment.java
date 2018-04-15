package com.ust.thesis.lightsandsockets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by BINS on 15/04/2018.
 */

public class profileFragment extends Fragment{

    public profileFragment(){
    }

    Button changePassButton;
    Button logoutButton;
    Button aboutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Initialize(view);
        ChangePassword(changePassButton);
        ToAboutActivity(aboutButton);
        Logout(logoutButton);
        return view;
    }

    public void Initialize(View view){
        changePassButton = (Button) view.findViewById(R.id.changePassButton);
        logoutButton = (Button) view.findViewById(R.id.logoutButton);
        aboutButton = (Button) view.findViewById(R.id.aboutButton);
    }

    public void ChangePassword(Button changePassButton){
        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(profileFragment.this.getActivity(), ChangePasswordActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void ToAboutActivity(Button about){
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(profileFragment.this.getActivity(), AboutActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void Logout(Button logout){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
