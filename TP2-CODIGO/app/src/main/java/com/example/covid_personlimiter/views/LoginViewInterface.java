package com.example.covid_personlimiter.views;

import com.example.covid_personlimiter.model.UserModel;

public interface LoginViewInterface {
    public void onClearText();
    public void onLoginResult(Boolean success, String msg, UserModel user);
    public void onSetProgressBarVisibility(int visibility);
}