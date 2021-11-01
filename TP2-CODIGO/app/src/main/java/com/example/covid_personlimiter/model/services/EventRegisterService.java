package com.example.covid_personlimiter.model.services;

import com.example.covid_personlimiter.model.requests.EventRegisterRequest;
import com.example.covid_personlimiter.model.responses.EventRegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EventRegisterService {

    @POST("api/event")
    Call<EventRegisterResponse> api_event_register(@Header("Authorization") String token, @Body EventRegisterRequest request);
}
