package com.example.covid_personlimiter.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesThread extends Thread {

    private static final String SHAREDPREFERENCESFILE = "sharedPreferencesFile";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesThread(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESFILE, context.MODE_PRIVATE);
    }

    public synchronized void savePreferences(int loginSuccess, int loginFailed) {
        int loginsSuccess = getLoginsSuccess() + loginSuccess;
        int loginsFailed = getLoginsFailed() + loginFailed;
        run(loginsSuccess, loginsFailed);
    }

    public synchronized String getPreferences() {
        Integer loginSuccesfull = getLoginsSuccess();
        Integer loginFailed = getLoginsFailed();
        String loginsSuccesfull = "Logueos exitosos: "+loginSuccesfull.toString();
        String loginsFailed = " y Logueos fallidos: "+ loginFailed.toString();
        return loginsSuccesfull+loginsFailed;
    }

    public synchronized int getLoginsSuccess () {
        return this.sharedPreferences.getInt("Logueos Exitosos",0);
    }

    public synchronized int getLoginsFailed() {
        return this.sharedPreferences.getInt("Logueos Fallidos",0);
    }

    public synchronized void run(int loginSuccess, int loginFailed) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt("Logueos Exitosos",loginSuccess);
        editor.putInt("Logueos Fallidos",loginFailed);
        editor.apply();
    }

    public synchronized void clearFile() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt("Logueos Exitosos",0);
        editor.putInt("Logueos Fallidos",0);
        editor.clear();
        editor.apply();
    }
}
