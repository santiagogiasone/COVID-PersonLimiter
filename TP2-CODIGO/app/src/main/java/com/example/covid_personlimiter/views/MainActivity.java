package com.example.covid_personlimiter.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.model.SharedPreferencesThread;
import com.example.covid_personlimiter.model.UserModel;
import com.example.covid_personlimiter.presenters.EventRegisterPresenter;
import com.example.covid_personlimiter.presenters.MainPresenter;

public class MainActivity extends Activity implements LoggedOnInterface {

    //Buttons
    private Button buttonPlus;
    private Button buttonMinus;
    private Button buttonClearLogins;


    //Texts
    private TextView counter;
    private TextView capacityMax;
    private TextView temperature;
    private TextView aforo;
    private TextView capacityReal;
    private TextView loginSuccess;
    private TextView loginFailed;


    //Contador
    private Integer contadorPersonas = 0;
    private final Integer capacidadMaxima = 10;

    private Intent intent;
    private UserModel user;

    //Presenter
    private MainPresenter mainPresenter;
    private EventRegisterPresenter eventRegisterPresenter;

    //SharedPreferences
    private SharedPreferencesThread spThread;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        //Instanciacion del presentador
        mainPresenter = new MainPresenter(this);
        mainPresenter.setupSensorManager();


        //Definicion de los botones
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonClearLogins = (Button) findViewById(R.id.buttonClearLogins);


        //Definicion de los textos variables de la app.
        counter = (TextView) findViewById(R.id.counter);
        counter.setText(contadorPersonas.toString());

        capacityMax = (TextView) findViewById(R.id.capacityMax);
        capacityMax.setText(" " + capacidadMaxima.toString());

        temperature = (TextView) findViewById(R.id.temperature);
        aforo = (TextView) findViewById(R.id.aforo);
        capacityReal = (TextView) findViewById(R.id.capacityReal);

        intent = getIntent();
        Bundle extras = intent.getExtras();
        user = (UserModel) intent.getSerializableExtra("user");
        Log.d("RESPONSE:", user.getUserId());
        Log.d("RESPONSE:", user.getDisplayName());
        Log.d("RESPONSE:", user.getToken());
        Log.d("RESPONSE:", user.getRefreshToken());

        eventRegisterPresenter = new EventRegisterPresenter(user, this);
        eventRegisterPresenter.doRegisterEvent("LOGIN","Se autentica en la aplicacion (Login)", this.getBaseContext());


        //SharedPreferences
        spThread = new SharedPreferencesThread(this.getBaseContext());
        spThread.savePreferences(extras.getInt("loginSuccess"),extras.getInt("loginFailed"));
        String a = spThread.getPreferences();
        Log.d("LOGUEOS:",a);

        loginSuccess = (TextView) findViewById(R.id.loginSuccess);
        setLoginsSuccess(spThread.getLoginsSuccess());
        loginFailed = (TextView) findViewById(R.id.loginFailed);
        setLoginsFailed(spThread.getLoginsFailed());
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

        mainPresenter.iniciarSensores();

        buttonMinus.setOnClickListener(v -> {
            eventRegisterPresenter.doRegisterEvent("INTERACTION","Ingreso de una persona al lugar", this.getBaseContext());
            contadorPersonas = mainPresenter.substract(contadorPersonas);
            counter.setText(contadorPersonas.toString());
        });

        buttonPlus.setOnClickListener(v -> {
            eventRegisterPresenter.doRegisterEvent("INTERACTION","Egreso de una persona del lugar", this.getBaseContext());
            contadorPersonas = mainPresenter.add(contadorPersonas);
            counter.setText(contadorPersonas.toString());
        });

        buttonClearLogins.setOnClickListener(v -> {
            eventRegisterPresenter.doRegisterEvent("INTERACTION","Se limpia el archivo de SharedPreference", this.getBaseContext());
            spThread.clearFile();
            setLoginsSuccess(0);
            setLoginsFailed(0);
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

    public void setLoginsSuccess(int loginsSuccess) {
        Integer lS = loginsSuccess;
        Log.d("LOGUEOS EXITOSOS: ", lS.toString());
        this.loginSuccess.setText(lS.toString());
    }

    public void setLoginsFailed(int loginsFailed) {
        Integer lF = loginsFailed;
        Log.d("LOGUEOS FALLIDOS: ", lF.toString());
        this.loginFailed.setText(lF.toString());
    }

    public void resetCounter()  {
        this.contadorPersonas = 0;
        Integer c = contadorPersonas;
        Log.d("Contador reiniciado",c.toString());
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
        mainPresenter.iniciarSensores();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        mainPresenter.pararSensores();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mainPresenter.pararSensores();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mainPresenter.pararSensores();
        super.onDestroy();
    }

    @Override
    public void goToLogin(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
        finish();
    }
}