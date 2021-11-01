package com.example.covid_personlimiter.presenters;

import com.example.covid_personlimiter.model.UserModel;

public interface EventRegisterPresenterInterface {
    void doRegisterEvent(String type_events, String description);
}
