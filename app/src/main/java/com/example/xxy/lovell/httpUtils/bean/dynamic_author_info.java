package com.example.xxy.lovell.httpUtils.bean;

import java.io.Serializable;

/**
 * Created by xxy on 2017/2/19.
 */

public class dynamic_author_info implements Serializable {
    private static final long serialVersionUID = 1L;
    private String school;
    private int enteryea;

    public String getSchool() {
        return school;
    }

    public int getEnteryea() {
        return enteryea;
    }

    @Override
    public String toString() {
        return school+" "+enteryea;
    }
}
