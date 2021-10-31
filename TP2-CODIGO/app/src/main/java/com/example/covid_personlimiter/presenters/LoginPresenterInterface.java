package com.example.covid_personlimiter.presenters;

public interface LoginPresenterInterface {
    void clear();
    void doLogin(String name, String passwd);
    void setProgressBarVisiblity(int visiblity);
}