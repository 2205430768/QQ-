package com.example.xxy.lovell.httpUtils.bean;

/**
 * Created by xxy on 2017/2/20.
 */

public class logintoken {
    private int state=0;
    private String username;
    private String usersig;
    private String LoginToken;

    public String getLoginToken() {
        return LoginToken;
    }
    public String getUsersig() {
        return usersig;
    }

    public int getState() {
        return state;
    }

    public String getUsername() {
        return username;
    }
}
