package com.example.covid_personlimiter.model.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryInfoService extends BroadcastReceiver {

    public BatteryInfoService() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.unregisterReceiver(this);
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        Float batteryPct = level * 100 / (float)scale;
        String txt = "Porcentaje Bateria: " +batteryPct.toString() + "%";
        Toast.makeText(context,txt,Toast.LENGTH_SHORT).show();
    }
}
