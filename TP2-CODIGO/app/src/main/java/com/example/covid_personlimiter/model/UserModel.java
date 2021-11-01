package com.example.covid_personlimiter.model;

import android.app.Activity;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.example.covid_personlimiter.model.UserInterface;
import com.example.covid_personlimiter.model.network.RetrofitInstance;
import com.example.covid_personlimiter.model.requests.LoginRequest;
import com.example.covid_personlimiter.model.responses.LoginResponse;
import com.example.covid_personlimiter.model.responses.RefreshResponse;
import com.example.covid_personlimiter.model.services.LoginService;
import com.example.covid_personlimiter.model.services.RefreshService;
import com.example.covid_personlimiter.views.LoggedOnInterface;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    public Boolean isTokenExpired() {
        JWT jwt = new JWT(token);
        return jwt.isExpired(30);
    }

    @Override
    public void generateNewToken(LoggedOnInterface ilogged) {
        Log.d("RESPONSE", "WASD");
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        Retrofit retrofit = retrofitInstance.getRetrofitInstance();
        RefreshService refreshService = retrofit.create(RefreshService.class);
        Call<RefreshResponse> call = refreshService.api_refresh("Bearer " + refreshToken);
        call.enqueue(new Callback<RefreshResponse>() {
            @Override
            public void onResponse(Call<RefreshResponse> call, Response<RefreshResponse> response) {
                if (response.isSuccessful()) {
                    token = (response.body().getToken());
                    refreshToken = (response.body().getToken_refresh());
                }
                else {
                    ilogged.goToLogin("Sesion Expirada");
                }
            }

            @Override
            public void onFailure(Call<RefreshResponse> call, Throwable t) {
                Log.d("RESPONSE", "SALIO TODO MAL 2");
                ilogged.goToLogin("Sesion Expirada");
            }
        });
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