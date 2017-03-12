package com.example.xxy.lovell.mvp;

import com.example.xxy.lovell.bean.CircleItem;
import com.example.xxy.lovell.bean.CommentConfig;
import com.example.xxy.lovell.bean.CommentItem;
import com.example.xxy.lovell.bean.FavortItem;
import com.example.xxy.lovell.httpUtils.bean.dynamic_item;

import java.util.List;

/**
 * Created by xxy on 2017/1/26.
 */

public interface CircleContract {
    interface View extends BaseView{
        void update2DeleteCircle(String circleId);
        void update2AddFavorite(int circlePosition, FavortItem addItem);
        void update2DeleteFavort(int circlePosition, String favortId);
        void update2AddComment(int circlePosition, CommentItem addItem);
        void update2DeleteComment(int circlePosition, String commentId);
        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
        void update2loadData(int loadType, List<dynamic_item> datas);
    }

    interface Presenter extends BasePresenter{
        void loadData(int loadType);
        void deleteCircle(final String circleId);
        void addFavort(final int circlePosition);
        void deleteFavort(final int circlePosition, final String favortId);
        void deleteComment(final int circlePosition, final String commentId);

    }
}
