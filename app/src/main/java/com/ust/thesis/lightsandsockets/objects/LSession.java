package com.ust.thesis.lightsandsockets.objects;

import android.content.Context;
import android.content.SharedPreferences;

public class LSession {
    private final String LPreferences = "UserPref";
    private final String kid = "uID";
    private final String kusername = "uUsername";
    private String username;
    private String id;

    public LSession(String id, String username){
        this.id = id;
        this.username = username;
    }
    public boolean setSession(Context context){
        try{
            SharedPreferences spreference;
            spreference = context.getSharedPreferences(this.LPreferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = spreference.edit();

            edit.putString(this.id, id.trim());
            edit.putString(this.username, username.trim());
            edit.apply();
            return true;
        }catch(Exception e){
            return false;
        }

    }
    public boolean clearSession(Context context){
        try{
            SharedPreferences spreference;
            spreference = context.getSharedPreferences(this.LPreferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = spreference.edit();

            edit.clear();
            edit.apply();
            return true;
        }catch(Exception e){
            return false;
        }

    }

    public boolean clearSession(){

        return true;
    }

    /**
     * GETTERS
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
