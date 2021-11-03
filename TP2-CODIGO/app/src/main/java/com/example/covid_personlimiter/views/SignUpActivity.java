package com.example.covid_personlimiter.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.model.UserModel;
import com.example.covid_personlimiter.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUpViewInterface, View.OnClickListener {

    private Toolbar toolbar;

    //Buttons
    private Button signUpButton;
    private Button goBackButton;

    //InputTexts
    private EditText name;
    private EditText lastName;
    private EditText dni;
    private EditText mail;
    private EditText password;
    private EditText confirmPassword;

    private TextView nameRequiered;
    private TextView lastNameRequiered;
    private TextView dniRequiered;
    private TextView mailRequeired;
    private TextView passRequeired;
    private TextView confirmPassRequeried;

    //Presenter
    private SignUpPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.et_name);
        lastName = (EditText) findViewById(R.id.et_lastname);
        dni = (EditText) findViewById(R.id.et_dni);
        mail = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        confirmPassword = (EditText) findViewById(R.id.et_confirm_password);
        signUpButton = (Button) findViewById(R.id.button_signup);
        //goBackButton = (Button) findViewById(R.id.btnGoBack);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        nameRequiered = (TextView) this.findViewById(R.id.nameRequiered);
        lastNameRequiered = (TextView) this.findViewById(R.id.lastNameRequiered);
        dniRequiered = (TextView) this.findViewById(R.id.dniRequiered);
        mailRequeired = (TextView) this.findViewById(R.id.mailRequeired);
        passRequeired = (TextView) this.findViewById(R.id.passRequeired);
        confirmPassRequeried = (TextView) this.findViewById(R.id.confirmPassRequeried);

        signUpButton.setOnClickListener(this);
        toolbar.setOnClickListener(this);

        presenter = new SignUpPresenter(this );
    }

    @Override
    public void onClick(View v) {
        String editName = name.getText().toString();
        String editLastName = lastName.getText().toString();
        String editDni = dni.getText().toString();
        String editMail = mail.getText().toString();
        String editPassword = password.getText().toString();
        String editConfirmPassword = confirmPassword.getText().toString();

        if (v.getId() == R.id.button_signup) {
            if (editName.isEmpty()) {
                nameRequiered.setVisibility(View.VISIBLE);
                return;
            }
            nameRequiered.setVisibility(View.GONE);

            if (editLastName.isEmpty()) {
                lastNameRequiered.setVisibility(View.VISIBLE);
                return;
            }
            lastNameRequiered.setVisibility(View.GONE);

            if (editDni.isEmpty()) {
                dniRequiered.setVisibility(View.VISIBLE);
                return;
            }
            dniRequiered.setVisibility(View.GONE);

            if (editMail.isEmpty()) {
                mailRequeired.setVisibility(View.VISIBLE);
                return;
            }
            mailRequeired.setVisibility(View.GONE);

            if (editPassword.isEmpty()) {
                passRequeired.setVisibility(View.VISIBLE);
                return;
            }
            passRequeired.setVisibility(View.GONE);

            if (editConfirmPassword.isEmpty()) {
                confirmPassRequeried.setVisibility(View.VISIBLE);
                return;
            }
            confirmPassRequeried.setVisibility(View.GONE);

            presenter.setProgressBarVisiblity(View.VISIBLE);
            dissableButton(signUpButton);
            toolbar.setEnabled(false);
            presenter.checkConnection(this.getBaseContext());
            presenter.doSignUp(editName, editLastName, Integer.parseInt(editDni), editMail, editPassword);
        }
        else if (v.getId() == R.id.toolbar) {
            finish();
        }
    }

    @Override
    public void onClearText() {
        this.name.setText("");
        this.lastName.setText("");
        this.dni.setText("");
        this.mail.setText("");
        this.password.setText("");
        this.confirmPassword.setText("");
    }

    @Override
    public void onRegisterResult(Boolean success, String msg, UserModel user) {
        presenter.setProgressBarVisiblity(View.INVISIBLE);
        signUpButton.setEnabled(true);
        toolbar.setEnabled(true);
        if (success){
            onClearText();
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {

    }

    @Override
    public void enableButton(Button button) {
        button.setEnabled(true);
    }

    @Override
    public void dissableButton(Button button) {
        button.setEnabled(false);
    }

    @Override
    public Button getBtnSignUp() {
        return this.signUpButton;
    }
}
