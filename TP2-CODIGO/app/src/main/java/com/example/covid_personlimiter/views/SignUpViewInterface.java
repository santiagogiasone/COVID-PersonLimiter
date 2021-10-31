package com.example.covid_personlimiter.views;

import com.example.covid_personlimiter.model.UserModel;

public interface SignUpViewInterface {
    public void onClearText();
    public void onRegisterResult(Boolean success, String msg, UserModel user);
    public void onSetProgressBarVisibility(int visibility);
}