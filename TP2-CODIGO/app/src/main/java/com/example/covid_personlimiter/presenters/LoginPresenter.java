package com.example.covid_personlimiter.presenters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.covid_personlimiter.model.UserModel;
import com.example.covid_personlimiter.model.network.RetrofitInstance;
import com.example.covid_personlimiter.model.requests.LoginRequest;
import com.example.covid_personlimiter.model.responses.LoginResponse;
import com.example.covid_personlimiter.model.services.ConnectionService;
import com.example.covid_personlimiter.model.services.LoginService;
import com.example.covid_personlimiter.views.LoginViewInterface;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPresenter implements LoginPresenterInterface {
    LoginViewInterface iLoginView;
    UserModel user;
    Handler handler;
    RetrofitInstance retrofitObj;

    public LoginPresenter(LoginViewInterface iLoginView) {
        this.iLoginView = iLoginView;
        this.retrofitObj = new RetrofitInstance();
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void checkConnection(Context context) {
        ConnectionService connectionService = new ConnectionService();
        boolean connection = (connectionService.isNetworkConnected(context) && connectionService.isInternetAvailable());
        if(!connection) {
            Toast.makeText(context,"Error en la conexion",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void doLogin(String email, String passwd) {
        try {
            LoginRequest request = new LoginRequest();
            request.setEmail(email);
            request.setPassword(passwd);
            Retrofit retrofit = retrofitObj.getRetrofitInstance();
            LoginService loginService = retrofit.create(LoginService.class);
            Call<LoginResponse> call = loginService.api_login(request);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        user.setDisplayName(email);
                        user.setToken(response.body().getToken());
                        user.setRefreshToken(response.body().getToken_refresh());
                        iLoginView.onLoginResult(response.body().getSuccess(), "Cuenta Autenticada Exitosamente", user);
                    }
                    else {
                        iLoginView.onLoginResult(false, "Credenciales Incorrectas", user);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    iLoginView.onLoginResult(false, "Se encontro un error en el sistema, contactar con un administrador", user);
                }
            });
        } catch (Exception e)  {
            Log.d("RESPONSE", e.toString());
        }
    }

    @Override
    public void setProgressBarVisiblity(int visiblity){
        iLoginView.onSetProgressBarVisibility(visiblity);
    }

    private void initUser(){
        user = new UserModel(UUID.randomUUID().toString());
    }
}