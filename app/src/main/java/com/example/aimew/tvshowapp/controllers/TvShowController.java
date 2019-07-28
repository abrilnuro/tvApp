package com.example.aimew.tvshowapp.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aimew.tvshowapp.App;
import com.example.aimew.tvshowapp.events.ErrorEvent;
import com.example.aimew.tvshowapp.events.SuccessAddTvShow;
import com.example.aimew.tvshowapp.models.LogIn;
import com.example.aimew.tvshowapp.models.TvShow;
import com.example.aimew.tvshowapp.properties.Endpoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TvShowController {

    private static String token;
    private static String userName;

    public static void addTvShow(final Context context, TvShow tvShow) {
        try {
            String json = App.getGSOn().toJson(tvShow);
            JSONObject jsonObject = new JSONObject(json);

            LogIn logIn = App.getSessionStateManager().getCurrentSession();
            token = logIn.getToken();
            userName = logIn.getUserName();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Endpoint.TV_SHOW_ADD, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("RESPONSE: ", "" + response);

                    if(response == null){
                        Toast.makeText(context, "Couldn't add TV show", Toast.LENGTH_SHORT).show();
                    }else{
                        TvShow tvShowResponse = App.getGSOn().fromJson(response.toString(), TvShow.class);
                        App.getBus().post(new SuccessAddTvShow(tvShowResponse));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error Response", error.toString());
                    App.getBus().post(new ErrorEvent(error.getMessage(), 1));
                }
            })

            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("token", token);
                    params.put("username", userName);

                    return params;
                }
            };

            jsonObjectRequest.setRetryPolicy(App.getRetryPolicy());
            App.getRequestQueue().add(jsonObjectRequest);

        } catch (JSONException error) {
            Log.i("Error Response", error.toString());
            App.getBus().post(new ErrorEvent(error.getMessage(), 0));
        }
    }
}
