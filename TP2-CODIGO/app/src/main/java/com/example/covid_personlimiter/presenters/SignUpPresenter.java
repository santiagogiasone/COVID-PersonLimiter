package com.example.covid_personlimiter.presenters;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.model.UserModel;
import com.example.covid_personlimiter.model.network.RetrofitInstance;
import com.example.covid_personlimiter.model.requests.SignUpRequest;
import com.example.covid_personlimiter.model.responses.SignUpResponse;
import com.example.covid_personlimiter.model.services.ConnectionService;
import com.example.covid_personlimiter.model.services.SignUpService;
import com.example.covid_personlimiter.views.SignUpViewInterface;

import org.json.JSONObject;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpPresenter implements SignUpPresenterInterface {
    private SignUpViewInterface signUpViewInterface;
    private UserModel user;
    private Handler handler;
    private RetrofitInstance retrofitObj;


    public SignUpPresenter(SignUpViewInterface signUpViewInterface) {
        this.signUpViewInterface = signUpViewInterface;
        this.retrofitObj = new RetrofitInstance();
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void clear() {
        signUpViewInterface.onClearText();
    }

    @Override
    public void checkConnection(Context context) {
        ConnectionService connectionService = new ConnectionService();
        //&& connectionService.isInternetAvailable()
        boolean connection = (connectionService.isNetworkConnected(context) );
        if(!connection) {
            signUpViewInterface.enableButton(signUpViewInterface.getBtnSignUp());
            Toast.makeText(context,"Error en la conexion",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void doSignUp(String name, String lastname, Integer dni, String email, String passwd) {
        try {
            Resources resource = signUpViewInterface.getResources();
            SignUpRequest request = new SignUpRequest();
            request.setName(name);
            request.setLastname(lastname);
            request.setDni(dni);
            request.setEmail(email);
            request.setPassword(passwd);
            request.setEnv(resource.getString(R.string.env));
            request.setGroup(resource.getInteger(R.integer.group));
            request.setCommission(resource.getInteger(R.integer.commision));
            Retrofit retrofit = retrofitObj.getRetrofitInstance();
            SignUpService loginService = retrofit.create(SignUpService.class);
            Call<SignUpResponse> call = loginService.api_register(request);
            call.enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                    if (response.isSuccessful()) {
                        user.setDisplayName(email);
                        user.setToken(response.body().getToken());
                        user.setRefreshToken(response.body().getToken_refresh());
                        signUpViewInterface.onRegisterResult(response.body().getSuccess(), "Cuenta Registrada Exitosamente", user);
                    }
                    else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            signUpViewInterface.onRegisterResult(false, jObjError.getString("msg"), user);
                        } catch (Exception e) {
                            signUpViewInterface.onRegisterResult(false, "Se encontro un error en el sistema, contactar con un administrador", user);
                        }
                        signUpViewInterface.onRegisterResult(false, "Credenciales Incorrectas", user);
                    }
                }

                @Override
                public void onFailure(Call<SignUpResponse> call, Throwable t) {
                    signUpViewInterface.onRegisterResult(false, "Se encontro un error en el sistema, contactar con un administrador", user);
                }
            });
        } catch (Exception e)  {
            Log.d("RESPONSE", e.toString());
        }
    }

    @Override
    public void setProgressBarVisiblity(int visiblity){
        signUpViewInterface.onSetProgressBarVisibility(visiblity);
    }

    private void initUser(){
        user = new UserModel(UUID.randomUUID().toString());
    }
}