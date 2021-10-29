package com.example.covid_personlimiter.presenters;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.covid_personlimiter.views.MainActivity;

import java.text.DecimalFormat;

public class MainPresenter implements SensorEventListener {

    //Activity
    private final MainActivity activity;

    //SensorManager
    private SensorManager sensorManager;

    //Models??

    DecimalFormat dosdecimales = new DecimalFormat("###.###");



    public MainPresenter(MainActivity activity) {
        this.activity = activity;
        //model?
    }

    public void setupSensorManager() {
        sensorManager = (SensorManager) this.activity.getSystemService(Context.SENSOR_SERVICE);
    }

    public void iniciarSensores() {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),       SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),             SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pararSensores() {
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String txt = "";
        synchronized (this)
        {
            Log.d("sensor", event.sensor.getName());

            switch(event.sensor.getType())
            {
                case Sensor.TYPE_ACCELEROMETER :
                    txt += "Acelerometro:\n";
                    txt += "x: " + dosdecimales.format(event.values[0]) + " m/seg2 \n";
                    txt += "y: " + dosdecimales.format(event.values[1]) + " m/seg2 \n";
                    txt += "z: " + dosdecimales.format(event.values[2]) + " m/seg2 \n";
                    //acelerometro.setText(txt);

                    if ((event.values[0] > 25) || (event.values[1] > 25) || (event.values[2] > 25)) {
                        activity.resetCounter();
                    }
                    break;

                case Sensor.TYPE_AMBIENT_TEMPERATURE :
                    txt += event.values[0] + " C \n";
                    activity.setTemperature(txt);
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public int substract(int counter) {
        return ( (counter == 0) ? 0 : counter - 1 );
    }

    public int add(int counter) {
        return counter + 1;
    }

}
