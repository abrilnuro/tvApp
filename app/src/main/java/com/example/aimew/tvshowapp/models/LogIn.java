package com.example.aimew.tvshowapp.models;

import com.example.aimew.tvshowapp.utils.JSONKeys;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LogIn implements Serializable {

    @SerializedName(JSONKeys.ID)
    private Integer id;

    @SerializedName(JSONKeys.TOKEN)
    private String token;

    @SerializedName(JSONKeys.USER_NAME)
    private String userName;

    @SerializedName(JSONKeys.PASSWORD)
    private String password;

    public LogIn() {
        this.id = 0;
        this.token = "";
        this.userName = "";
        this.password = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
