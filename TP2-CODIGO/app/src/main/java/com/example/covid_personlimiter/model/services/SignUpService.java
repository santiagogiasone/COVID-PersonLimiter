package com.example.covid_personlimiter.model.services;

import com.example.covid_personlimiter.model.requests.SignUpRequest;
import com.example.covid_personlimiter.model.responses.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpService {

    @POST("api/register")
    Call<SignUpResponse> api_register(@Body SignUpRequest request);
}
