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

    //Sensores
    private Sensor sensorTemperature;
    private Sensor sensorAccelerometer;

    //Models??

    DecimalFormat dosdecimales = new DecimalFormat("###.###");



    public MainPresenter(MainActivity activity) {
        this.activity = activity;
        //model?
    }

    public void setupSensorManager() {
        sensorManager = (SensorManager) this.activity.getSystemService(Context.SENSOR_SERVICE);
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensorTemperature != null) {
            Log.e("SENSOR: ","Usted TIENE el sensor de temperatura en su telefono");
        } else
            Log.e("SENSOR: ","Usted NO TIENE el sensor de temperatura en su telefono");
        if(sensorAccelerometer != null) {
            Log.e("SENSOR: ","Usted TIENE el sensor de acelerometro en su telefono");
        } else
            Log.e("SENSOR: ","Usted NO TIENE el sensor de acelerometro en su telefono");
    }

    public void iniciarSensores() {
        sensorManager.registerListener(this, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pararSensores() {
        sensorManager.unregisterListener(this, sensorTemperature);
        sensorManager.unregisterListener(this, sensorAccelerometer);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        String txt = "";
        float temperature;
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

                    if ((Math.abs(event.values[0]) > 25) || (Math.abs(event.values[1]) > 25) || (Math.abs(event.values[2]) > 25)) {
                        activity.resetCounter();
                    }
                    break;

                case Sensor.TYPE_AMBIENT_TEMPERATURE :
                    temperature = event.values[0];
                    txt += temperature + "°C";
                    activity.setTemperature(txt);
                    activity.setAforo(calcularAforo(temperature));
                    activity.setCapacityReal(calcularCapacityReal(temperature,activity.getCapacidadMaxima()));
                    break;
            }
        }
    }

    private String calcularCapacityReal(float temperature, int capacidadMaxima) {
        if (temperature < 5) {
            //Aforo del 30% cuando la temperatura es menor a 5°C
            return Integer.toString((int) ((float) capacidadMaxima * 0.3));
        } else if (temperature <= 15) {
            //Aforo del 50% cuando la temperatura esta entre 5°C y 15°C
            return Integer.toString((int) ((float) capacidadMaxima * 0.5));
        } else
            //Aforo del 100% cuando la temperatura es mayor a 15°C
            return Integer.toString((int) ((float) capacidadMaxima * 1));
    }

    private String calcularAforo(float temperature) {
        if (temperature < 5) {
            //Aforo del 30% cuando la temperatura es menor a 5°C
            return "30%";
        } else if (temperature <= 15) {
            //Aforo del 50% cuando la temperatura esta entre 5°C y 15°C
            return "50%";
        } else
            //Aforo del 100% cuando la temperatura es mayor a 15°C
            return "100%";
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public int substract(int counter) {
        return ( (counter == 0) ? counter : counter - 1 );
    }

    public int add(int counter) {
        return ( (counter == activity.getCapacidadRealActual()) ? counter : counter + 1 );
    }

}
