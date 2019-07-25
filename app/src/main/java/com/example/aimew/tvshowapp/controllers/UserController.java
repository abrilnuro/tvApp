package com.example.aimew.tvshowapp.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aimew.tvshowapp.events.ErrorEvent;
import com.example.aimew.tvshowapp.events.SuccessLogInEvent;
import com.example.aimew.tvshowapp.models.LogIn;
import com.example.aimew.tvshowapp.properties.Endpoint;
import com.example.aimew.tvshowapp.App;
import com.example.aimew.tvshowapp.utils.JSONKeys;
import com.example.aimew.tvshowapp.utils.SessionStateManager;

import org.json.JSONException;
import org.json.JSONObject;

public class UserController {

    public static void logIn(final Context context, String userName, String password, String token) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JSONKeys.USER_NAME, userName);
            jsonObject.put(JSONKeys.PASSWORD, password);
            jsonObject.put(JSONKeys.TOKEN, token);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Endpoint.USER_LOGIN, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("RESPONSE: ", "" + response);

                    if(response == null){
                        Toast.makeText(context, "Contraseña y/o correo invalido", Toast.LENGTH_SHORT).show();
                    }else{
                        LogIn logIn = App.getGSOn().fromJson(response.toString(), LogIn.class);
                        SessionStateManager sessionStateManager = new SessionStateManager(context);
                        sessionStateManager.saveSession(logIn);
                        App.getBus().post(new SuccessLogInEvent(logIn));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error Response", error.toString());
                    App.getBus().post(new ErrorEvent(error.getMessage(), 1));
                }
            });

            jsonObjectRequest.setRetryPolicy(App.getRetryPolicy());
            App.getRequestQueue().add(jsonObjectRequest);

        } catch (JSONException error) {
            Log.i("Error Response", error.toString());
            App.getBus().post(new ErrorEvent(error.getMessage(), 0));
        }
    }
}
