package com.example.covid_personlimiter.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesThread extends Thread {

    private static final String SHAREDPREFERENCESFILE = "sharedPreferencesFile";
    private SharedPreferences sharedPreferences;
    private int loginSuccesfull;
    private int loginFailed;

    public SharedPreferencesThread(Context context, int loginSuccesfull, int loginFailed) {
        this.sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESFILE, context.MODE_PRIVATE);
        this.loginSuccesfull = loginSuccesfull;
        this.loginFailed = loginFailed;
    }

    public void savePreferences() {
        run();
    }
    public String getPreferences() {
        Integer loginSuccesfull = this.sharedPreferences.getInt("Logueos Exitosos",1);
        Integer loginFailed = this.sharedPreferences.getInt("Logueos Fallidos",0);
        String loginsSuccesfull = "Logueos exitosos: "+loginSuccesfull.toString();
        String loginsFailed = " y Logueos fallidos: "+ loginFailed.toString();
        return loginsSuccesfull+loginsFailed;
    }

    public void run() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt("Logueos Exitosos",this.loginSuccesfull);
        editor.putInt("Logueos Fallidos",this.loginFailed);
        editor.apply();
    }
}
