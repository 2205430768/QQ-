package com.example.xxy.lovell.adapter.viewholder;

import android.media.MediaPlayer;

import com.example.xxy.lovell.widget.TextureVideoView;

/**
 * Created by xxy on 2017/2/6.
 */

public interface VideoLoadMvpView {
    TextureVideoView getVideoView();

    void videoBeginning();

    void videoStopped();

    void videoPrepared(MediaPlayer player);

    void videoResourceReady(String videoPath);
}
