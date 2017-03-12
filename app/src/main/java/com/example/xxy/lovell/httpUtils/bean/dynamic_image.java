package com.example.xxy.lovell.httpUtils.bean;

import java.io.Serializable;

/**
 * Created by xxy on 2017/2/19.
 */

public class dynamic_image implements Serializable {
     private static final long serialVersionUID = 6L;
     private int rows;
     private url_data url;

     public int getRows() {
          return rows;
     }

     public url_data getUrl() {
          return url;
     }
}
