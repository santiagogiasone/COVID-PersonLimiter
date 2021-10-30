package com.example.covid_personlimiter.view;

public interface LoginViewInterface {
    public void onClearText();
    public void onLoginResult(Boolean result, int code);
    public void onSetProgressBarVisibility(int visibility);
}