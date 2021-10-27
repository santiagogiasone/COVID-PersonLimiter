package com.example.covid_personlimiter;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.covid_personlimiter.presenters.MainPresenter;

public class MainActivity extends Activity {

    //Buttons
    private Button buttonPlus;
    private Button buttonMinus;

    //Texts
    private TextView username;
    private TextView counter;
    private TextView capacity;
    private TextView temperature;

    //Presenter
    private MainPresenter presenter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        //Definicion de los botones
        buttonPlus = (Button)findViewById(R.id.buttonPlus);
        buttonMinus = (Button)findViewById(R.id.buttonMinus);

        //Definicion de los textos variables de la app.
        username = (TextView) findViewById(R.id.username);
        counter = (TextView) findViewById(R.id.counter);
        capacity = (TextView) findViewById(R.id.capacity);
        temperature = (TextView) findViewById(R.id.temperature);

        //Acceso al servicio de Sensores.
        //presenter = new MainPresenter( activity: this);

        /*
        buttonMinus.setOnClickListener(v -> {
            presenter.substract();
        });

        buttonPlus.setOnClickListener(v -> {
            presenter.add();
        });
        */
        presenter.setupSensorManager();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //public void setTemperature(int temperature)  { this.temperature.setText(temperature.toString()) }


}