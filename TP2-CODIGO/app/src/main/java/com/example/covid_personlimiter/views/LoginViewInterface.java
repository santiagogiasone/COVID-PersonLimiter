package com.example.covid_personlimiter.views;

import android.widget.Button;

import com.example.covid_personlimiter.model.UserModel;

public interface LoginViewInterface {
    public void onClearText();
    public void onLoginResult(Boolean success, String msg, UserModel user);
    public void onSetProgressBarVisibility(int visibility);
    public void enableButton(Button button);
    public void dissableButton(Button button);
    public Button getBtnSignUp();
    public Button getBtnLogin();
    }