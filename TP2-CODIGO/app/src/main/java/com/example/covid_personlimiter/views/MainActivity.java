package com.example.covid_personlimiter.views;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.presenters.MainPresenter;

public class MainActivity extends Activity {

    //Buttons
    private Button buttonPlus;
    private Button buttonMinus;

    //Texts
    private TextView counter;
    private TextView capacityMax;
    private TextView temperature;
    private TextView aforo;
    private TextView capacityReal;

    //Contador
    private Integer contadorPersonas = 0;
    private Integer capacidadMaxima = 300;
    private Integer temperaturaActual;
    private Integer aforoActual;
    private Integer capacidadRealActual = 4;

    //Presenter
    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        //Instanciacion del presentador
        presenter = new MainPresenter(this);
        presenter.setupSensorManager();


        //Definicion de los botones
        buttonPlus = (Button)findViewById(R.id.buttonPlus);
        buttonMinus = (Button)findViewById(R.id.buttonMinus);

        //Definicion de los textos variables de la app.
        counter = (TextView) findViewById(R.id.counter);
        counter.setText(contadorPersonas.toString());

        capacityMax = (TextView) findViewById(R.id.capacityMax);
        capacityMax.setText(" "+capacidadMaxima.toString());

        temperature = (TextView) findViewById(R.id.temperature);

        aforo = (TextView) findViewById(R.id.aforo);

        capacityReal = (TextView) findViewById(R.id.capacityReal);



    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.iniciarSensores();

        buttonMinus.setOnClickListener(v -> {
            contadorPersonas = presenter.substract(contadorPersonas);
            counter.setText(contadorPersonas.toString());
        });

        buttonPlus.setOnClickListener(v -> {
            contadorPersonas = presenter.add(contadorPersonas);
            counter.setText(contadorPersonas.toString());
        });

    }

    public void setTemperature(String temperature)  {
        Log.d("TEMPERATURA: ", temperature);
        this.temperature.setText(" "+temperature);
    }

    public void resetCounter()  {
        contadorPersonas = 0;
        counter.setText(contadorPersonas.toString());
    }

    public int getCapacidadRealActual() {
        return capacidadRealActual;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        presenter.iniciarSensores();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        presenter.pararSensores();
        super.onPause();
    }

    @Override
    protected void onStop() {
        presenter.pararSensores();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.pararSensores();
        super.onDestroy();
    }
}