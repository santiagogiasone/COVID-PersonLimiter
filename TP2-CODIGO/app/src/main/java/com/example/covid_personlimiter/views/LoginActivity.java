package com.example.covid_personlimiter.views;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.model.UserModel;
import com.example.covid_personlimiter.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginViewInterface, View.OnClickListener {
    private EditText editUser;
    private TextView userRequired;
    private EditText editPass;
    private TextView passwordRequired;
    private Button   btnLogin;
    private Button   btnSignUp;
    private LoginPresenter loginPresenter;
    private ProgressBar progressBar;
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            Float batteryPct = level * 100 / (float)scale;
            String txt = "Porcentaje Bateria: " +batteryPct.toString() + "%";
            setBatteryInfo(txt);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        //find view
        editUser = (EditText) this.findViewById(R.id.mail);
        editPass = (EditText) this.findViewById(R.id.password);
        btnLogin = (Button) this.findViewById(R.id.login);
        btnSignUp = (Button) this.findViewById(R.id.signup);
        progressBar = (ProgressBar) this.findViewById(R.id.loading);
        passwordRequired = (TextView) this.findViewById(R.id.password_required);
        userRequired = (TextView) this.findViewById(R.id.email_required);

        //set listener
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        //init
        loginPresenter = new LoginPresenter(this);
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);

        //BatteryInfo    //FALTA DESREGISTRAR ESTE RECEIVER
        this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


    }

    @Override
    public void onClick(View v) {
        String editUserText = editUser.getText().toString();
        String editPassText = editPass.getText().toString();
        if (v.getId() == R.id.signup) {
            Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(intent);
        }
        if (editUserText.isEmpty()) {
            userRequired.setVisibility(View.VISIBLE);
            return;
        }
        if (editPassText.isEmpty()) {
            passwordRequired.setVisibility(View.VISIBLE);
            return;
        }
        userRequired.setVisibility(View.GONE);
        passwordRequired.setVisibility(View.GONE);
        if (v.getId() == R.id.login){
            loginPresenter.setProgressBarVisiblity(View.VISIBLE);
            btnLogin.setEnabled(false);
            btnSignUp.setEnabled(false);
            loginPresenter.doLogin(editUserText, editPassText);
        }
    }

    @Override
    public void onClearText() {
        editUser.setText("");
        editPass.setText("");
    }

    @Override
    public void onLoginResult(Boolean success, String msg, UserModel user) {

        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);
        btnLogin.setEnabled(true);
        btnSignUp.setEnabled(true);
        if (success){
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
        else
            Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    public void setBatteryInfo(String txt) {
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }
}
