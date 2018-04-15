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

    private ConstraintLayout light1;
    private ConstraintLayout socket1Link;
    private ConstraintLayout socket2Link;
    private ConstraintLayout socket3Link;
    private ConstraintLayout socket4Link;
    private TextView socket1;
    private TextView socket2;
    private TextView socket3;
    private TextView socket4;
    private TextView appliance1;
    private TextView appliance2;
    private TextView appliance3;
    private TextView appliance4;

    public thingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_things, container, false);
        light1 = (ConstraintLayout) view.findViewById(R.id.Light1Link);
        socket1Link = (ConstraintLayout) view.findViewById(R.id.socket1Link);
        socket2Link = (ConstraintLayout) view.findViewById(R.id.socket2Link);
        socket3Link = (ConstraintLayout) view.findViewById(R.id.socket3Link);
        socket4Link = (ConstraintLayout) view.findViewById(R.id.socket4Link);
        socket1 = (TextView) view.findViewById(R.id.socket1);
        socket2 = (TextView) view.findViewById(R.id.socket2);
        socket3 = (TextView) view.findViewById(R.id.socket3);
        socket4 = (TextView) view.findViewById(R.id.socket4);
        appliance1 = (TextView) view.findViewById(R.id.appliance1);
        appliance2 = (TextView) view.findViewById(R.id.appliance2);
        appliance3 = (TextView) view.findViewById(R.id.appliance3);
        appliance4 = (TextView) view.findViewById(R.id.appliance4);

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
