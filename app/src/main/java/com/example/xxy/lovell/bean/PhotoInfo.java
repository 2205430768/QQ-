package com.example.xxy.lovell.bean;

/**
 * Created by xxy on 2017/1/26.
 */

public class PhotoInfo {

    public String url;
    public int w;
    public int h;

    public PhotoInfo(String url, int w, int h) {
        this.url = url;
        this.w = w;
        this.h = h;
    }
    public PhotoInfo(String url){
          this.url=url;
    }
}
