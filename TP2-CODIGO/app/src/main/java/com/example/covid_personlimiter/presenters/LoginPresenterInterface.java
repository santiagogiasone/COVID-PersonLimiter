package com.example.covid_personlimiter.presenters;

import android.content.Context;

public interface LoginPresenterInterface {
    void clear();
    void checkConnection(Context context);
    void doLogin(String name, String passwd);
    void setProgressBarVisiblity(int visiblity);
}