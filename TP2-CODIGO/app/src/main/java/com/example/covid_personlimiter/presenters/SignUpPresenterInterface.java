package com.example.covid_personlimiter.presenters;

import android.content.Context;

public interface SignUpPresenterInterface {
    void clear();
    void checkConnection(Context context);
    void doSignUp(String name, String lastname, Integer dni, String email, String passwd);
    void setProgressBarVisiblity(int visiblity);
}