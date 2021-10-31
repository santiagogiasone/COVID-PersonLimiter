package com.example.covid_personlimiter.presenters;

public interface SignUpPresenterInterface {
    void clear();
    void doSignUp(String name, String lastname, Integer dni, String email, String passwd);
    void setProgressBarVisiblity(int visiblity);
}