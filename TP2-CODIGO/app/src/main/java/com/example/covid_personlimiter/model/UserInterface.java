package com.example.covid_personlimiter.model;

import com.example.covid_personlimiter.views.LoggedOnInterface;

public interface UserInterface {

    String getDisplayName();

    String getUserId();

    String getToken();

    String getRefreshToken();

    void setToken(String token);

    void setRefreshToken(String refreshToken);

    void setDisplayName(String displayName);

    Boolean isTokenExpired();

    void generateNewToken(LoggedOnInterface ilogged);
}