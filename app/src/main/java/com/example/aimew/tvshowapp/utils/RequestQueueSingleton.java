package com.example.aimew.tvshowapp.utils;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {
    private static RequestQueueSingleton requestQueSingleton;
    private RequestQueue requestQueue;
    private static Context context;

    private RequestQueueSingleton(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (requestQueSingleton == null) {
            requestQueSingleton = new RequestQueueSingleton(context);
        }
        return requestQueSingleton;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
