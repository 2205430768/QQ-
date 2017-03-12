package com.example.xxy.lovell.mvp;

import com.example.xxy.lovell.bean.CircleItem;
import com.example.xxy.lovell.bean.FavortItem;
import com.example.xxy.lovell.httpUtils.bean.dynamic_item;
import com.example.xxy.lovell.listener.IDataRequestListener;
import com.example.xxy.lovell.mvp.model.CircleModel;
import com.example.xxy.lovell.utils.DatasUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xxy on 2017/1/26.
 */

public class CirclePresenter implements CircleContract.Presenter{
    private CircleModel circleModel;
    private CircleContract.View view;

    public CirclePresenter(CircleContract.View view){
        circleModel = new CircleModel();
        this.view = view;
    }
    @Override
    public void loadData(final int loadType) {
        Map<String,String> map=new HashMap<>();
        circleModel.loadData(new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                List<dynamic_item>  danamicDatas1=new ArrayList<dynamic_item>();
                List<dynamic_item> danamicDatas= (List<dynamic_item>) object;
                if(view!=null){
                    if(danamicDatas!=null&&danamicDatas.size()>0){
                        view.update2loadData(loadType, danamicDatas);
                    }
                   else{
                        view.update2loadData(loadType, danamicDatas1);
                    }
                }
            }
        },map);

    }

    /**
     *
     * @Title: deleteCircle
     * @Description: 删除动态
     * @param  circleId
     * @return void    返回类型
     * @throws
     */
    @Override
    public void deleteCircle(final String circleId){
        circleModel.deleteCircle(new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                if(view!=null){
                    view.update2DeleteCircle(circleId);
                }
            }
        });
    }
    /**
     *
     * @Title: addFavort
     * @Description: 点赞
     * @param  circlePosition
     * @return void    返回类型
     * @throws
     */
    @Override
    public void addFavort(final int circlePosition) {
        circleModel.addFavort(new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                FavortItem item = DatasUtil.createCurUserFavortItem();
                if(view !=null ){
                    view.update2AddFavorite(circlePosition, item);
                }

            }
        });
    }
    /**
     *
     * @Title: deleteFavort
     * @Description: 取消点赞
     * @param @param circlePosition
     * @param @param favortId
     * @return void    返回类型
     * @throws
     */
    @Override
    public void deleteFavort(final int circlePosition, final String favortId) {
        circleModel.deleteFavort(new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                if(view !=null ){
                    view.update2DeleteFavort(circlePosition, favortId);
                }
            }
        });
    }
    /**
     *
     * @Title: deleteComment
     * @Description: 删除评论
     * @param @param circlePosition
     * @param @param commentId
     * @return void    返回类型
     * @throws
     */
    @Override
    public void deleteComment(final int circlePosition, final String commentId) {
        circleModel.deleteComment(new IDataRequestListener(){

            @Override
            public void loadSuccess(Object object) {
                if(view!=null){
                    view.update2DeleteComment(circlePosition, commentId);
                }
            }

        });
    }
    /**
     * 清除对外部对象的引用，反正内存泄露。
     */
    public void recycle(){
        this.view = null;
    }
}
