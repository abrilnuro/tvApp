package com.example.aimew.tvshowapp.events;

import com.example.aimew.tvshowapp.models.LogIn;

public class SuccessLogInEvent {

    public LogIn logIn;

    public SuccessLogInEvent(LogIn logIn) {
        this.logIn = logIn;
    }

    public LogIn getLogIn() {
        return logIn;
    }

    public void setLogIn(LogIn logIn) {
        this.logIn = logIn;
    }
}
