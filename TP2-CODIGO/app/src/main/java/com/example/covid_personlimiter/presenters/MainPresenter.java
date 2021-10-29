package com.example.covid_personlimiter.presenters;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.EditText;

import com.example.covid_personlimiter.views.MainActivity;

public class MainPresenter {

    //Activity
    private MainActivity activity;

    //SensorManager
    private SensorManager sensorManager;

    //Models??

    public MainPresenter(MainActivity activity) {
        this.activity = activity;
        //model?
    }

    public void setupSensorManager() {
        sensorManager = (SensorManager) this.activity.getSystemService(Context.SENSOR_SERVICE);
    }

    public void listenerSensors() {
        Sensor temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        //Agregar sensor para el shake (contador en 0)
        //sensorManager.registerListener(listener: this, temperatureSensor);
    }

    public int substract(int counter) {
        return ( (counter == 0) ? 0 : counter - 1 );
    }

    public int add(int counter) {
        return counter + 1;
    }
}
