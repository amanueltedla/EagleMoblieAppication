package com.tedla.amanuel.eagleapp.model;

/**
 * Created by dVentus-hq on 7/1/2017.
 */
public class LoginResponseModel {
    private String token;
    private LoginUserModel user;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginUserModel getUser() {
        return user;
    }

    public void setUser(LoginUserModel user) {
        this.user = user;
    }

}
