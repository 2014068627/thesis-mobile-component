package com.ust.thesis.lightsandsockets;



import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class thingsFragment extends Fragment {

    ConstraintLayout light1;
    ConstraintLayout socket1Link;
    ConstraintLayout socket2Link;
    ConstraintLayout socket3Link;
    ConstraintLayout socket4Link;
    TextView socket1;
    TextView socket2;
    TextView socket3;
    TextView socket4;
    TextView appliance1;
    TextView appliance2;
    TextView appliance3;
    TextView appliance4;

    public thingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_things, container, false);
        light1 =  view.findViewById(R.id.Light1Link);
        socket1Link =  view.findViewById(R.id.socket1Link);
        socket2Link =  view.findViewById(R.id.socket2Link);
        socket3Link =  view.findViewById(R.id.socket3Link);
        socket4Link =  view.findViewById(R.id.socket4Link);
        socket1 = view.findViewById(R.id.socket1);
        socket2 = view.findViewById(R.id.socket2);
        socket3 = view.findViewById(R.id.socket3);
        socket4 = view.findViewById(R.id.socket4);
        appliance1 = view.findViewById(R.id.appliance1);
        appliance2 = view.findViewById(R.id.appliance2);
        appliance3 = view.findViewById(R.id.appliance3);
        appliance4 = view.findViewById(R.id.appliance4);

        directToLight(light1);
        directToSocket(socket1Link, socket1, appliance1);
        directToSocket(socket2Link, socket2, appliance2);
        directToSocket(socket3Link, socket3, appliance3);
        directToSocket(socket4Link, socket4, appliance4);
        return view;
    }

    public void directToSocket(ConstraintLayout layout, TextView text, TextView appliances/*temporary*/){
        final String socket = text.getText().toString();
        final String appliance = appliances.getText().toString();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(thingsFragment.this.getActivity(), SocketActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("socket", socket);
                bundle.putString("appliance", appliance); /*temporary*/
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
    }

    public void directToLight(ConstraintLayout layout){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(thingsFragment.this.getActivity(), LightActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
