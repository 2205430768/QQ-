package com.example.xxy.lovell.adapter.viewholder;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.xxy.lovell.R;
import com.example.xxy.lovell.widget.ExpandTextView;
import com.example.xxy.lovell.widget.TextureVideoView;

/**
 * Created by xxy on 2017/2/6.
 */

public abstract class CircleViewHolder extends RecyclerView.ViewHolder implements VideoLoadMvpView {

    public final static int TYPE_URL = 1;
    public final static int TYPE_IMAGE = 2;
    public final static int TYPE_VIDEO = 3;

    public int viewType;

    public ImageView headIv;
    public TextView nameTv;
    public TextView school;
    /** 动态的内容 */
    public ExpandTextView contentTv;
    public TextView timeTv;
    public Button liaota;
    public LinearLayout comment;
    public LinearLayout praise;
    public TextView comment_count;
    public TextView praise_count;
    public Button deleteStatus;
    public CircleViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;
        ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);
        initSubView(viewType, viewStub);
        headIv = (ImageView) itemView.findViewById(R.id.headIv);
        nameTv = (TextView) itemView.findViewById(R.id.nameTv);
        contentTv = (ExpandTextView) itemView.findViewById(R.id.contentTv);
        school = (TextView) itemView.findViewById(R.id.school);
        timeTv = (TextView) itemView.findViewById(R.id.timeTv);
        liaota= (Button) itemView.findViewById(R.id.liaota);
        comment= (LinearLayout) itemView.findViewById(R.id.comment);
        praise= (LinearLayout) itemView.findViewById(R.id.praise);
        comment_count= (TextView) itemView.findViewById(R.id.comment_count);
        praise_count = (TextView) itemView.findViewById(R.id.praise_count);
        deleteStatus=(Button) itemView.findViewById(R.id.deleteStatus);
    }

    public abstract void initSubView(int viewType, ViewStub viewStub);

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
