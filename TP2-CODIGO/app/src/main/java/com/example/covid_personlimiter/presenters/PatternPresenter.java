package com.example.covid_personlimiter.presenters;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.covid_personlimiter.model.services.BatteryInfoService;

public class PatternPresenter {

    public PatternPresenter() {
    }


    public void getBatteryInfo(Context baseContext) {
        baseContext.registerReceiver(new BatteryInfoService(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
}
