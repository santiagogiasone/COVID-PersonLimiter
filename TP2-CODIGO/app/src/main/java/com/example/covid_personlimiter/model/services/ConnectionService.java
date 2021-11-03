package com.example.covid_personlimiter.model.services;

import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

public class ConnectionService {

    static final String BASE_URL = "http://so-unlam.net.ar";

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName(BASE_URL);
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }
}
