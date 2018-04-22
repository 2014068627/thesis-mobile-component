package com.ust.thesis.lightsandsockets;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class CancelScheduleFragment extends Fragment {

    TextView textViewSchedule;
    Button cancelSchedButton;
    CancelScheduleFragment CSF;
    SwitchButtonFragment SBF;
    Bundle bundle;

    String socket_id;
    String date_sched;
    String sched_id;

    public CancelScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancel_schedule, container, false);
        initialize(view);
        CancelSchedule(cancelSchedButton);
        return view;
    }

    private void initialize(View view){
        textViewSchedule = view.findViewById(R.id.textViewSchedule);
        cancelSchedButton = view.findViewById(R.id.cancelSchedButton);
        bundle = getArguments();
        socket_id = bundle.getString("socket_id");
        date_sched = bundle.getString("date_sched");
        sched_id = bundle.getString("sched_id");

        textViewSchedule.setText("Socket "+ socket_id + " is scheduled for turn off in "+date_sched);
        CSF = new CancelScheduleFragment();
        SBF = new SwitchButtonFragment();
        SBF.setArguments(bundle);
    }

    private void CancelSchedule(Button bttn){
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LSession user_session = new LSession();
                String [] user_arr = user_session.getUserSession(getActivity());
                String user_id = user_arr[0];
                String user_username = user_arr[1];

                HashMap json_cancel = new HashMap();
                json_cancel.put("sched_id", sched_id);
                json_cancel.put("socket", socket_id);
                json_cancel.put("user_id", user_id);
                json_cancel.put("user_username", user_username);
                String url = getString(R.string.apiserver) + "api/powerboard/cancel_sched";
                cancelScheduleRequest(url, json_cancel);
            }
        });
    }

    private void setFragmentSchedActive(Fragment fragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FragmentContainerSched, fragment);
        fragmentTransaction.commit();
    }

    private void cancelScheduleRequest(String url, HashMap json_cancel){
        //dialog box for loading
        final ProgressDialog api_dialog = new ProgressDialog(this.getActivity());
        api_dialog.setMessage(getString(R.string.api_wait));
        api_dialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(json_cancel), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject json_response = response.getJSONObject("response");
                    boolean success = json_response.getBoolean("success");
                    if(success){
                        setFragmentSchedActive(SBF);
                    }
                }catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(),"Failed to cancel schedule",Toast.LENGTH_SHORT).show();
                }
                api_dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                api_dialog.dismiss();
            }
        }){
            // https://stackoverflow.com/questions/43486027/how-to-send-request-header-is-content-typeapplication-json-when-get-on-voll
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        LSingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

}
