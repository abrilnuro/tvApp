package com.example.aimew.tvshowapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class PreferencesManager {

    private SharedPreferences sharedPreferences;

    public PreferencesManager(Context context){
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName(),
                Context.MODE_PRIVATE);
    }

    public void saveConfiguration(String financingRate, String deposit, String deadline){
        clearConfiguration();
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString("financingRate", financingRate);
        editor.putString("deposit", deposit);
        editor.putString("deadline", deadline);
        editor.apply();
    }

    public void clearConfiguration(){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public Bundle getConfiguration(){
        String financingRate = sharedPreferences.getString("financingRate","");
        String deposit = sharedPreferences.getString("deposit","");
        String deadline = sharedPreferences.getString("deadline", "");

        if(financingRate == ""){
            return null;
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("financingRate", financingRate);
            bundle.putString("deposit", deposit);
            bundle.putString("deadline", deadline);

            return bundle;
        }
    }
}
