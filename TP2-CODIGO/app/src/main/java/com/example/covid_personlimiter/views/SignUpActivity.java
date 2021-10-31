package com.example.covid_personlimiter.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.model.UserModel;
import com.example.covid_personlimiter.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUpViewInterface, View.OnClickListener {

    private Toolbar toolbar;

    //Buttons
    private Button signUpButton;

    //InputTexts
    private EditText name;
    private EditText lastName;
    private EditText dni;
    private EditText mail;
    private EditText password;
    private EditText confirmPassword;

    //Presenter
    private SignUpPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.et_name);
        lastName = (EditText) findViewById(R.id.et_lastname);
        dni = (EditText) findViewById(R.id.et_dni);
        mail = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        confirmPassword = (EditText) findViewById(R.id.et_confirm_password);
        signUpButton = (Button) findViewById(R.id.button_signup);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        signUpButton.setOnClickListener(this);
        toolbar.setOnClickListener(this);

        presenter = new SignUpPresenter(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_signup){
            presenter.setProgressBarVisiblity(View.VISIBLE);
            signUpButton.setEnabled(false);
            toolbar.setEnabled(false);
            presenter.doSignUp(name.getText().toString(), lastName.getText().toString(), Integer.parseInt(dni.getText().toString()), mail.getText().toString(), password.getText().toString());
        }
        else if (v.getId() == R.id.toolbar) {
            finish();
        }
    }

    @Override
    public void onClearText() {

    }

    @Override
    public void onRegisterResult(Boolean success, String msg, UserModel user) {
        presenter.setProgressBarVisiblity(View.INVISIBLE);
        signUpButton.setEnabled(true);
        toolbar.setEnabled(true);
        if (success){
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
        else
            Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {

    }
}
