package com.example.aimew.tvshowapp;

import android.app.Application;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.example.aimew.tvshowapp.utils.SessionStateManager;
import com.google.gson.Gson;
import com.squareup.otto.Bus;

public class App extends Application {

    private static Bus bus;
    private static RequestQueue requestQueue;
    private static Gson gson;
    private static SessionStateManager sessionStateManager;

    @Override
    public void onCreate(){
        super.onCreate();
        bus = new Bus();
        requestQueue = Volley.newRequestQueue(this);
        gson = new Gson();
        sessionStateManager = new SessionStateManager(this);
    }

    public static Bus getBus() {
        return bus;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static RetryPolicy getRetryPolicy(){
        int socketTimeout = 100000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return policy;
    }

    public static Gson getGSOn(){
        return gson;
    }

    public static SessionStateManager getSessionStateManager() {
        return sessionStateManager;
    }
}
