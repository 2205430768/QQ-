package com.example.xxy.lovell.adapter.viewholder;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xxy.lovell.R;
import com.example.xxy.lovell.widget.ExpandTextView;
import com.example.xxy.lovell.widget.TextureVideoView;

/**
 * Created by xxy on 2017/2/17.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder implements VideoLoadMvpView {
    public final static int TYPE_TEXT = 0;
    public final static int TYPE_URL = 1;
    public final static int TYPE_IMAGE = 2;
    public final static int TYPE_VIDEO = 3;


    public int viewType;
    public View itemView;
    public ImageView headImage;
    public TextView commentName;
    public TextView commentTime;
    public ExpandTextView commentContent;
    public TextView comment1;
    public TextView comment2;
    public Button moreComment;
    public CommentViewHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        viewType=0;
        initView();
    }



    public CommentViewHolder(View itemView,int viewType) {
        super(itemView);
        this.viewType=viewType;
        this.itemView=itemView;
        initView();
    }
    private void initView() {

        headImage= (ImageView) itemView.findViewById(R.id.comment_headIv);
        commentName= (TextView) itemView.findViewById(R.id.comment_name);
        commentTime= (TextView) itemView.findViewById(R.id.comment_time);
        commentContent= (ExpandTextView) itemView.findViewById(R.id.comment_content);
        comment1= (TextView) itemView.findViewById(R.id.comment1);
        comment2= (TextView) itemView.findViewById(R.id.comment2);
        moreComment= (Button) itemView.findViewById(R.id.more_comment);
    }
    @Override
    public TextureVideoView getVideoView() {
        return null;
    }

    @Override
    public void videoBeginning() {

    }

    @Override
    public void videoStopped() {

    }

    @Override
    public void videoPrepared(MediaPlayer player) {

    }

    @Override
    public void videoResourceReady(String videoPath) {

    }
}
