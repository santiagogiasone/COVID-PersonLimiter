package com.example.covid_personlimiter.presenters;

import android.content.res.Resources;
import android.util.Log;

import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.model.UserModel;
import com.example.covid_personlimiter.model.network.RetrofitInstance;
import com.example.covid_personlimiter.model.requests.EventRegisterRequest;
import com.example.covid_personlimiter.model.responses.EventRegisterResponse;
import com.example.covid_personlimiter.model.services.EventRegisterService;
import com.example.covid_personlimiter.views.MainActivity;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventRegisterPresenter implements EventRegisterPresenterInterface {

    private RetrofitInstance retrofitObj;
    private UserModel user;
    private MainActivity mainActivity;

    public EventRegisterPresenter(UserModel user, MainActivity mainActivity) {
        this.retrofitObj = new RetrofitInstance();
        this.user = user;
        this.mainActivity = mainActivity;
    }

    @Override
    public void doRegisterEvent(String type_events, String description) {
        try {
            Resources resource = mainActivity.getResources();
            EventRegisterRequest request = new EventRegisterRequest();
            request.setEnv(resource.getString(R.string.env));
            request.setType_events(type_events);
            request.setDescription(description);
            //VER LA LOGICA DE ESTO PORQUE ME PIDE UNA LoggedOnInterface y solo se la puedo mandar desde la view que la implementa
            if(user.isTokenExpired()) {
                user.generateNewToken(mainActivity);
            }
            Retrofit retrofit = retrofitObj.getRetrofitInstance();
            EventRegisterService eventRegisterService = retrofit.create(EventRegisterService.class);
            retrofit2.Call<EventRegisterResponse> call = eventRegisterService.api_event_register("Bearer " + user.getToken(), request);
            call.enqueue(new Callback<EventRegisterResponse>() {
                @Override
                public void onResponse(retrofit2.Call<EventRegisterResponse> call, Response<EventRegisterResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("Registro Evento","Succesful");
                    }
                    else {
                        Log.e("Registro Evento","Failed");
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<EventRegisterResponse> call, Throwable t) {
                    Log.e("Registro Evento","Fallo al registrar evento");
                }
            });
        } catch (Exception e)  {
            Log.d("RESPONSE", e.toString());
        }
    }
}
