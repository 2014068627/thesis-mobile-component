package com.ust.thesis.lightsandsockets.objects;

import android.content.Context;
import android.content.SharedPreferences;

public class LSession {
    private final String LPreferences = "UserPref";
    private final String kid = "uID";
    private final String kusername = "uUsername";
    private String username;
    private String id;

    /**
     * public constructors
     */
    public LSession(String id, String username){
        this.id = id;
        this.username = username;
    }
    public LSession(){}

    /**
     * function to add user id and user username to shared preference
     */
    public boolean setSession(Context context){
        try{
            SharedPreferences spreference;
            spreference = context.getSharedPreferences(this.LPreferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = spreference.edit();

            edit.putString(this.kid, id.trim());
            edit.putString(this.kusername, username.trim());
            edit.apply();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * function to clear user session
     */
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

    /**
     * function to return a string array containing user id and user username in shared preference
     */
    public String[] getUserSession(Context context){
        SharedPreferences preference = context.getSharedPreferences(this.LPreferences, Context.MODE_PRIVATE);
        String id = preference.getString(kid, "");
        String username = preference.getString(kusername, "");
        String[] ar = {id, username};
        return ar;
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
