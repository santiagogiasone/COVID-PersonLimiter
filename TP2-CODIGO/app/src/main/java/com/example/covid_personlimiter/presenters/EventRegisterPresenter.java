package com.example.covid_personlimiter.presenters;

import android.util.Log;

import com.example.covid_personlimiter.model.UserModel;
import com.example.covid_personlimiter.model.network.RetrofitInstance;
import com.example.covid_personlimiter.model.requests.EventRegisterRequest;
import com.example.covid_personlimiter.model.requests.LoginRequest;
import com.example.covid_personlimiter.model.responses.EventRegisterResponse;
import com.example.covid_personlimiter.model.responses.LoginResponse;
import com.example.covid_personlimiter.model.services.EventRegisterService;
import com.example.covid_personlimiter.model.services.LoginService;
import com.example.covid_personlimiter.views.LoggedOnInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventRegisterPresenter implements EventRegisterPresenterInterface {

    private RetrofitInstance retrofitObj;
    private UserModel user;

    public EventRegisterPresenter(UserModel user) {
        this.retrofitObj = new RetrofitInstance();
        this.user = user;
    }

    @Override
    public void doRegisterEvent(String type_events, String description) {
        try {
            EventRegisterRequest request = new EventRegisterRequest();
            request.setEnv("TEST");
            request.setType_events(type_events);
            request.setDescription(description);
            Retrofit retrofit = retrofitObj.getRetrofitInstance();
            EventRegisterService eventRegisterService = retrofit.create(EventRegisterService.class);
            Call<EventRegisterResponse> call = eventRegisterService.api_event_register("Bearer " + user.getToken(), request);
            call.enqueue(new Callback<EventRegisterResponse>() {
                @Override
                public void onResponse(Call<EventRegisterResponse> call, Response<EventRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("Registro Evento","Succesful");
                    }
                    else {
                        Log.e("Registro Evento","Failed");
                    }
                }

                @Override
                public void onFailure(Call<EventRegisterResponse> call, Throwable t) {
                    Log.e("Registro Evento","Fallo al registrar evento");
                }
            });
        } catch (Exception e)  {
            Log.d("RESPONSE", e.toString());
        }
    }
}
