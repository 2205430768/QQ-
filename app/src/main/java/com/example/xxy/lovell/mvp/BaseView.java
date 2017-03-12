package com.example.xxy.lovell.mvp;

/**
 * Created by xxy on 2017/1/26.
 */

public interface BaseView {
    void showLoading(String msg);
    void hideLoading();
    void showError(String errorMsg);
}
