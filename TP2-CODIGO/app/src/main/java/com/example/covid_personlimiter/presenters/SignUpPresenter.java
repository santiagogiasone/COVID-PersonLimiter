package com.example.covid_personlimiter.presenters;

import android.util.Log;

import com.example.covid_personlimiter.views.SignUpActivity;

public class SignUpPresenter {

    private SignUpActivity activity;

    public SignUpPresenter(SignUpActivity activity) {
        this.activity = activity;
    }

    public void setData(String name, String lastName, String dni, String mail, String password, String confirmPassword) {
        Log.d("name ", name);
        Log.d("lastname ", lastName);
        Log.d("DNI ", dni);
        Log.d("mail ", mail);
        Log.d("password ", password);
        Log.d("confirmPassword ", confirmPassword);
        return;
    }

}
