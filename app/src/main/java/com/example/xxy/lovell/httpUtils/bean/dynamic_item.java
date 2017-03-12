package com.example.xxy.lovell.httpUtils.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxy on 2017/2/19.
 */

public class dynamic_item implements Serializable {
    private static final long serialVersionUID = 8L;
    private String id;
    private String author_id;
    private String content;
    private String createtime;
    private dynamic_image img;
    private String voice;
    private String praise;
    private String type;
    private dynamic_commentbody commentbody;
    private String me_praise;
    private dynamic_author_info author_info;
    private String comment;

    public String getComment() {
        return comment;
    }

    public dynamic_author_info getAuthor_info() {
        return author_info;
    }

    public String getId() {
        return id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public String getContent() {
        return content;
    }

    public String getCreatetime() {
        return createtime;
    }

    public dynamic_image getImg() {
        return img;
    }

    public String getVoice() {
        return voice;
    }

    public String getPraise() {
        return praise;
    }

    public String getType() {
        return type;
    }

    public dynamic_commentbody getCommentbody() {
        return commentbody;
    }

    public String getMe_praise() {
        return me_praise;
    }
}
