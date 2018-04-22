package com.ust.thesis.lightsandsockets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ust.thesis.lightsandsockets.objects.LSession;

public class fragmentContainer extends AppCompatActivity {

    BottomNavigationView bttmNav;
    FrameLayout frameNav;
    thingsFragment TF;
    ActivityFragment AF;
    profileFragment PF;

    Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_nav);
        appContext = getApplicationContext();
        bttmNav = findViewById(R.id.main_nav);
        frameNav = findViewById(R.id.main_frame);
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
                    case R.id.activity :
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
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 300){
            bttmNav.setSelectedItemId(R.id.profile);
            setFragment(PF);
        }
    }

    /**
     * user pressed back to logout
     * https://stackoverflow.com/questions/6413700/android-proper-way-to-use-onbackpressed-with-toast
     */
    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Do you want to logout?")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fragmentContainer.super.onBackPressed();
//                        Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
                        LSession session = new LSession();
                        if(session.clearSession(getApplicationContext())){
                            Toast.makeText(getApplicationContext(),"Session is empty",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Error in session",Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                }).create().show();
    }
}
