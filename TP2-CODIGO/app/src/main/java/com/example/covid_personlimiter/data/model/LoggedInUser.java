package com.example.covid_personlimiter.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String token;
    private String refreshToken;


    public LoggedInUser(String userId, String displayName, String token, String refreshToken) {
        this.userId = userId;
        this.displayName = displayName;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}