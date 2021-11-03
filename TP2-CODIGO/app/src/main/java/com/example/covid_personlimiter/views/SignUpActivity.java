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

    //Errors labels
    private TextView nameRequired;
    private TextView lastnameRequired;
    private TextView mailRequired;
    private TextView dniRequired;
    private TextView passwordRequired;
    private TextView passwordMatch;

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
        nameRequired = (TextView) this.findViewById(R.id.name_required);
        lastnameRequired = (TextView) this.findViewById(R.id.lastname_required);
        mailRequired = (TextView) this.findViewById(R.id.email_required);
        dniRequired = (TextView) this.findViewById(R.id.dni_required);
        passwordRequired = (TextView) this.findViewById(R.id.password_required);
        passwordMatch = (TextView) this.findViewById(R.id.confirm_password_does_not_match);

        signUpButton.setOnClickListener(this);

        presenter = new SignUpPresenter(this );
    }

    public boolean areInputsValid() {
        Boolean nameNotExists = name.getText().toString().isEmpty();
        Boolean lastnameNotExists = lastName.getText().toString().isEmpty();
        Boolean dniNotExists = dni.getText().toString().isEmpty();
        Boolean mailNotExists = mail.getText().toString().isEmpty();
        Boolean passwordNotExists = password.getText().toString().isEmpty();
        Boolean passwordDoesMatch = password.getText().toString().equals(confirmPassword.getText().toString());
        nameRequired.setVisibility(nameNotExists ? View.VISIBLE : View.GONE);
        lastnameRequired.setVisibility(lastnameNotExists ? View.VISIBLE : View.GONE);
        dniRequired.setVisibility(dniNotExists ? View.VISIBLE : View.GONE);
        mailRequired.setVisibility(mailNotExists ? View.VISIBLE : View.GONE);
        passwordRequired.setVisibility(passwordNotExists ? View.VISIBLE : View.GONE);
        passwordMatch.setVisibility(passwordDoesMatch ? View.GONE : View.VISIBLE);
        return !nameNotExists && !lastnameNotExists && !dniNotExists && !mailNotExists && passwordDoesMatch && !passwordNotExists;
    }

    @Override
    public void onClick(View v) {
        if (areInputsValid()) {
            if (v.getId() == R.id.button_signup) {
                presenter.setProgressBarVisiblity(View.VISIBLE);
                signUpButton.setEnabled(false);
                presenter.doSignUp(name.getText().toString(), lastName.getText().toString(), Integer.parseInt(dni.getText().toString()), mail.getText().toString(), password.getText().toString());
            }
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
        if (success){
            onClearText();
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
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
