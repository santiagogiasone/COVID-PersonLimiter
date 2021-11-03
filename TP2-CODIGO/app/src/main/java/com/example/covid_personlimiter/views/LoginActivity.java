package com.example.covid_personlimiter.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.model.UserModel;
import com.example.covid_personlimiter.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginViewInterface, View.OnClickListener {

    private EditText editUser;
    private TextView userRequired;
    private EditText editPass;
    private TextView passwordRequired;
    private Button   btnLogin;
    private Button   btnSignUp;
    private ProgressBar progressBar;
    private LoginPresenter loginPresenter;
    private int loginSuccess;
    private int loginFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        loginSuccess = 0;
        loginFailed = 0;

        //find view
        editUser = (EditText) this.findViewById(R.id.mail);
        editPass = (EditText) this.findViewById(R.id.password);
        btnLogin = (Button) this.findViewById(R.id.login);
        btnSignUp = (Button) this.findViewById(R.id.signup);
        progressBar = (ProgressBar) this.findViewById(R.id.loading);
        passwordRequired = (TextView) this.findViewById(R.id.password_required);
        userRequired = (TextView) this.findViewById(R.id.email_required);

        //set listener
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        //init
        loginPresenter = new LoginPresenter(this);
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {
        String editUserText = editUser.getText().toString();
        String editPassText = editPass.getText().toString();
        if (v.getId() == R.id.login){
            if (editUserText.isEmpty()) {
                userRequired.setVisibility(View.VISIBLE);
                return;
            }
            if (editPassText.isEmpty()) {
                passwordRequired.setVisibility(View.VISIBLE);
                return;
            }
            userRequired.setVisibility(View.GONE);
            passwordRequired.setVisibility(View.GONE);
            loginPresenter.setProgressBarVisiblity(View.VISIBLE);
            dissableButton(btnLogin);
            loginPresenter.checkConnection(this.getBaseContext());
            loginPresenter.doLogin(editUserText, editPassText);
        }
        else if (v.getId() == R.id.signup) {
            Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClearText() {
        editUser.setText("");
        editPass.setText("");
    }

    @Override
    public void onLoginResult(Boolean success, String msg, UserModel user) {

        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);
        btnLogin.setEnabled(true);
        btnSignUp.setEnabled(true);
        if (success){
            onClearText();
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            this.loginSuccess += 1;
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("loginSuccess",this.loginSuccess);
            intent.putExtra("loginFailed",this.loginFailed);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            this.loginFailed += 1;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onStart() {
        loginSuccess = 0;
        loginFailed = 0;
        super.onStart();
    }

    @Override
    public void onResume() {
        loginSuccess = 0;
        loginFailed = 0;
        super.onResume();
    }



    @Override
    public void enableButton(Button button) {
        button.setEnabled(true);
    }
    @Override
    public void dissableButton(Button button) {
        button.setEnabled(false);
    }

    public Button getBtnLogin() {
        return this.btnLogin;
    }
    public Button getBtnSignUp() {
        return this.btnSignUp;
    }
}
