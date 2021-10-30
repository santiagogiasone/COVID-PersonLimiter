package com.example.covid_personlimiter.presenters;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.covid_personlimiter.data.model.UserModel;
import com.example.covid_personlimiter.model.UserInterface;
import com.example.covid_personlimiter.model.network.RetrofitInstance;
import com.example.covid_personlimiter.model.requests.LoginRequest;
import com.example.covid_personlimiter.model.responses.LoginResponse;
import com.example.covid_personlimiter.model.services.LoginService;
import com.example.covid_personlimiter.view.LoginViewInterface;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPresenter implements LoginPresenterInterface {
    LoginViewInterface iLoginView;
    UserInterface user;
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
    public void doLogin(String email, String passwd) {
        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(passwd);
        Retrofit retrofit = retrofitObj.getRetrofitInstance();
        LoginService loginService = retrofit.create(LoginService.class);
        Call<LoginResponse> call = loginService.api_login(request);
        Boolean isLoginSuccess = true;
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("RESPONSE", "hola");
                Log.d("RESPONSE", response.body().getSuccess().toString());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("RESPONSE", "quemal");
            }
        });
    }

    @Override
    public void setProgressBarVisiblity(int visiblity){
        iLoginView.onSetProgressBarVisibility(visiblity);
    }

    private void initUser(){
        user = new UserModel(UUID.randomUUID().toString());
    }
}