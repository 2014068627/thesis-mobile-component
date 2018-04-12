package com.ust.thesis.lightsandsockets;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class fragmentContainer extends AppCompatActivity
                 implements profileFragment.OnFragmentInteractionListener{

    private BottomNavigationView bttmNav;
    private FrameLayout frameNav;
    private thingsFragment TF;
    private ActivityFragment AF;
    private profileFragment PF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_nav);

        bttmNav = (BottomNavigationView) findViewById(R.id.main_nav);
        frameNav = (FrameLayout) findViewById(R.id.main_frame);
        TF = new thingsFragment();
        AF = new ActivityFragment();
        PF = new profileFragment();

        setFragment(TF);
        bttmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch(item.getItemId()){
                    case R.id.things :
                        setFragment(TF);
                        return true;
                    case R.id.date:
                        setFragment(AF);
                        return true;
                    case R.id.profile :
                        setFragment(PF);
                        return true;
                    default :
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }

}
