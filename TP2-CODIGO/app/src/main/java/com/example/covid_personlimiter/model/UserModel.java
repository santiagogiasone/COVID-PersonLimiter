package com.example.covid_personlimiter.model;

import com.example.covid_personlimiter.model.UserInterface;

import java.io.Serializable;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class UserModel implements UserInterface, Serializable {

    private String userId;
    private String displayName;
    private String token;
    private String refreshToken;

    public UserModel(String userId) {
        this.userId = userId;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}