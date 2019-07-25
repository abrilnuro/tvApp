package com.example.aimew.tvshowapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aimew.tvshowapp.controllers.UserController;
import com.example.aimew.tvshowapp.events.ErrorEvent;
import com.example.aimew.tvshowapp.events.SuccessLogInEvent;
import com.example.aimew.tvshowapp.models.LogIn;
import com.squareup.otto.Subscribe;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editUserName;
    private EditText editPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        App.getBus().register(this);

        editUserName = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        buttonLogin = (Button) findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_login){
            String userName = editUserName.getText().toString();
            String password = editPassword.getText().toString();
            UserController.logIn(this, userName, password, "");
        }
    }

    @Subscribe
    public void onError(ErrorEvent errorEvent) {
        Log.e("OnError Event", errorEvent.getMessage() + "code:"+ errorEvent.getCode() );
    }

    @Subscribe
    public void onSuccessLogInEvent(SuccessLogInEvent successLogInEvent) {
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
