package com.example.covid_personlimiter.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.presenters.SignUpPresenter;

public class SignUpActivity extends Activity {

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

        presenter = new SignUpPresenter(this);
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

            signUpButton.setOnClickListener(v -> {
                presenter.setData(name.getText().toString(), lastName.getText().toString(), dni.getText().toString(),
                        mail.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());
            });
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
}
