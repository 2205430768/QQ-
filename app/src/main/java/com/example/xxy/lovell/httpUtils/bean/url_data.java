package com.example.xxy.lovell.httpUtils.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxy on 2017/2/20.
 */

public class url_data implements Serializable {
    private static final long serialVersionUID = 16L;
    List<String> normal;
    List<String> thumb;

    public List<String> getNormal() {
        return normal;
    }

    public List<String> getThumb() {
        return thumb;
    }
}
