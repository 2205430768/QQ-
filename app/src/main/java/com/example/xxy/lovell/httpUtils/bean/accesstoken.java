package com.example.xxy.lovell.httpUtils.bean;

/**
 * Created by xxy on 2017/2/20.
 */

public class accesstoken {
    private static final long serialVersionUID = 2L;
    private int state=0;
    private String username;
    private String AccessToken;

    public String getAccessToken() {
        return AccessToken;
    }

    public int getState() {
        return state;
    }

    public String getUsername() {
        return username;
    }
}
