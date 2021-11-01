package com.example.covid_personlimiter.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.auth0.android.jwt.JWT;
import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.model.UserModel;
import com.example.covid_personlimiter.presenters.MainPresenter;

public class MainActivity extends Activity implements LoggedOnInterface {

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
    private final Integer capacidadMaxima = 10;
    private Intent intent;
    private UserModel user;

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

        intent = getIntent();
        user = (UserModel) intent.getSerializableExtra("user");
        Log.d("RESPONSE:", user.getToken());
        user.generateNewToken(this);
/*
        String txt = "";
        float temperature = 10;
        txt += temperature + "°C";
        setTemperature(txt);
        setAforo(calcularAforo(temperature));
        setCapacityReal(calcularCapacityReal(temperature,getCapacidadMaxima()));

 */
    }
/*
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


 */
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

    public void setAforo(String aforo) {
        Log.d("AFORO: ", aforo);
        this.aforo.setText(" "+aforo);
    }

    public void setCapacityReal(String capacityReal) {
        Log.d("CAPACIDAD REAL: ", capacityReal);
        this.capacityReal.setText(capacityReal);
    }

    public void resetCounter()  {
        contadorPersonas = 0;
        counter.setText(contadorPersonas.toString());
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public int getCapacidadRealActual() {
        return Integer.parseInt(capacityReal.getText().toString());
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

    @Override
    public void goToLogin(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
        finish();
    }
}