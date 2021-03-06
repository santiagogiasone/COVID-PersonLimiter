package com.example.covid_personlimiter.presenters;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.model.RefreshCallback;
import com.example.covid_personlimiter.model.UserModel;
import com.example.covid_personlimiter.model.network.RetrofitInstance;
import com.example.covid_personlimiter.model.requests.EventRegisterRequest;
import com.example.covid_personlimiter.model.responses.EventRegisterResponse;
import com.example.covid_personlimiter.model.services.ConnectionService;
import com.example.covid_personlimiter.model.services.EventRegisterService;
import com.example.covid_personlimiter.views.MainActivity;

import retrofit2.Call;
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
    public void doRegisterEvent(String type_events, String description, Context context) {
        try {
            checkConnection(context);
            RefreshCallback callback = new RefreshCallback() {
                @Override
                public void done() {
                    Resources resource = mainActivity.getResources();
                    Log.d("RESPONSE", "ME ACTUALIZARON? TOKEN");
                    Log.d("RESPONSE", user.getToken());
                    EventRegisterRequest request = new EventRegisterRequest();
                    request.setEnv(resource.getString(R.string.env));
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
                }
            };
            user.verifyToken(mainActivity, callback);
        } catch (Exception e)  {
            Log.d("RESPONSE", e.toString());
        }
    }

    @Override
    public void checkConnection(Context context) {
        ConnectionService connectionService = new ConnectionService();
        //&& connectionService.isInternetAvailable()
        boolean connection = (connectionService.isNetworkConnected(context));
        if(!connection) {
            Toast.makeText(context,"Error en la conexion",Toast.LENGTH_SHORT).show();
        }
    }
}