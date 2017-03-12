package com.example.xxy.lovell.mvp.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.xxy.lovell.constants.Constant;
import com.example.xxy.lovell.httpUtils.HttpUtils;
import com.example.xxy.lovell.httpUtils.bean.dynamic_item;
import com.example.xxy.lovell.httpUtils.bean.get_usersig;
import com.example.xxy.lovell.httpUtils.bean.logintoken;
import com.example.xxy.lovell.listener.IDataRequestListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xxy on 2017/1/26.
 */

public class CircleModel {
    public CircleModel(){
        //
    }

    public void loadData(final IDataRequestListener listener,Map<String,String> params){
        requestDynamicDataFromInternet(listener,params);
    }

    public void deleteCircle( final IDataRequestListener listener) {
        requestDynamicData(listener);
    }

    public void addFavort( final IDataRequestListener listener) {
        requestDynamicData(listener);
    }

    public void deleteFavort(final IDataRequestListener listener) {
        requestDynamicData(listener);
    }

    public void addComment( final IDataRequestListener listener) {
        requestDynamicData(listener);
    }

    public void deleteComment( final IDataRequestListener listener) {
        requestDynamicData(listener);
    }

    /**
     *
     * @Title: requestServer
     * @Description: 与后台交互, 因为demo是本地数据，不做处理
     * @param  listener    设定文件
     * @return void    返回类型
     * @throws
     */
    private void requestDynamicData(final IDataRequestListener listener) {
        new AsyncTask<Object, Integer, Object>(){
            @Override
            protected Object doInBackground(Object... params) {
                //和后台交互
                return null;
            }

            protected void onPostExecute(Object result) {
                listener.loadSuccess(result);
            };
        }.execute();
    }
    private void requestDynamicDataFromInternet(final IDataRequestListener listener,Map<String,String> params) {
        new AsyncTask<Map<String,String>, Integer, List<dynamic_item>>(){

            @Override
            protected List<dynamic_item> doInBackground(Map<String,String>... params) {

                return HttpUtils.getDynamicData(params[0]);
            }
            protected void onPostExecute(List<dynamic_item> result) {
                if(result!=null){
                    Log.d("异步加载数据","成功  "+result.size());
                }
                else{
                    Log.e("异步加载数据","失败");
                }
                listener.loadSuccess(result);
            };
        }.execute(params);
    }
}
