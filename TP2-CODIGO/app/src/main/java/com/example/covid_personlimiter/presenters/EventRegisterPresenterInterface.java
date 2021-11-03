package com.example.covid_personlimiter.presenters;

import android.content.Context;

public interface EventRegisterPresenterInterface {
    void doRegisterEvent(String type_events, String description, Context context);
    void checkConnection(Context context);

}
