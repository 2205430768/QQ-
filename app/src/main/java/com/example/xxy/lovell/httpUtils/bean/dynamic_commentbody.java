package com.example.xxy.lovell.httpUtils.bean;

import java.io.Serializable;

/**
 * Created by xxy on 2017/2/19.
 */

public class dynamic_commentbody implements Serializable {
    private static final long serialVersionUID = 3L;
    private int main_rows;
    private String record;

    public String getRecord() {
        return record;
    }

    public int getMain_rows() {
        return main_rows;
    }
}
