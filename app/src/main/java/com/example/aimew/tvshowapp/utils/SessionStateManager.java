package com.example.aimew.tvshowapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.aimew.tvshowapp.models.LogIn;

public class SessionStateManager {

    private SharedPreferences sharedPreferences;

    public SessionStateManager(Context context){
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName(),
                Context.MODE_PRIVATE);
    }

    public void saveSession(LogIn logIn){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(JSONKeys.ID, logIn.getId());
        editor.putString(JSONKeys.TOKEN, logIn.getToken());
        editor.putString(JSONKeys.USER_NAME, logIn.getUserName());
        editor.apply();
    }

    public void finishSession(){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.remove(JSONKeys.ID);
        editor.remove(JSONKeys.TOKEN);
        editor.remove(JSONKeys.USER_NAME);
        editor.apply();
    }

    public LogIn getCurrentSession(){
        Integer id = sharedPreferences.getInt(JSONKeys.ID,0);
        String token = sharedPreferences.getString(JSONKeys.TOKEN,"");
        String userName = sharedPreferences.getString(JSONKeys.USER_NAME, "");

        if(token.isEmpty()){
            return null;
        } else {
            LogIn logIn = new LogIn();
            logIn.setUserName(userName);
            logIn.setToken(token);
            logIn.setId(id);
            return logIn;
        }


    }
}
