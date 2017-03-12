package com.example.xxy.lovell.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xxy.lovell.R;
import com.example.xxy.lovell.adapter.viewholder.CircleViewHolder;
import com.example.xxy.lovell.adapter.viewholder.CommentViewHolder;
import com.example.xxy.lovell.adapter.viewholder.ImageViewHolder;

/**
 * Created by xxy on 2017/2/17.
 */

public class CommentAdapter extends BaseRecycleViewAdapter{
    private Context context;

    public CommentAdapter(Context context) {
          this.context=context;

    }
        @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
            viewHolder=new CommentViewHolder(view);
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }
    @Override
    public int getItemCount() {
        return 10;
    }



}
