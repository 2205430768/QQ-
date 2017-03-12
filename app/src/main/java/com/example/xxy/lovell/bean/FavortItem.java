package com.example.xxy.lovell.bean;

import java.io.Serializable;

/**
 * Created by xxy on 2017/1/26.
 */

public class FavortItem implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private User user;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
