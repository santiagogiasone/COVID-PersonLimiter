package com.example.covid_personlimiter.views;

public interface LoginViewInterface {
    public void onClearText();
    public void onLoginResult(Boolean success, String msg);
    public void onSetProgressBarVisibility(int visibility);
}