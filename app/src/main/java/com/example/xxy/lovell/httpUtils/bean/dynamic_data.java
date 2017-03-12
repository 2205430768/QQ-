package com.example.xxy.lovell.httpUtils.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxy on 2017/2/19.
 */

public class dynamic_data implements Serializable {
    private static final long serialVersionUID = 4L;
    private int state;
    private int rows;
    List<dynamic_item> data=new ArrayList<>();

    public int getState() {
        return state;
    }

    public int getRows() {
        return rows;
    }

    public List<dynamic_item> getData() {
        return data;
    }
}
