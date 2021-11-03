package com.example.covid_personlimiter.model.services;

import com.example.covid_personlimiter.model.requests.LoginRequest;
import com.example.covid_personlimiter.model.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("api/login")
    Call<LoginResponse> api_login(@Body LoginRequest request);
}
