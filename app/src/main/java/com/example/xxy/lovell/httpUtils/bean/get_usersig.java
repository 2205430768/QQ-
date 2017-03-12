package com.example.xxy.lovell.httpUtils.bean;

import java.io.Serializable;

/**
 * Created by xxy on 2017/2/20.
 */

public class get_usersig implements Serializable {
    private int state=0;
    private String username;
    private String usersig;

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
