package com.example.covid_personlimiter.model.services;

import com.example.covid_personlimiter.model.responses.RefreshResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

public interface RefreshService {
    @Headers({
            "content-type: application/json"
    })
    @PUT("api/refresh")
    Call<RefreshResponse> api_refresh(@Header("Authorization") String token);
}
