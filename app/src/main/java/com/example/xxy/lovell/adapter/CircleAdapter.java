package com.example.xxy.lovell.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.xxy.lovell.R;
import com.example.xxy.lovell.activity.CommentActivity;
import com.example.xxy.lovell.activity.ImagePagerActivity;
import com.example.xxy.lovell.activity.MainActivity;
import com.example.xxy.lovell.activity.PublishedActivity;
import com.example.xxy.lovell.adapter.viewholder.CircleViewHolder;
import com.example.xxy.lovell.adapter.viewholder.ImageViewHolder;
import com.example.xxy.lovell.bean.CircleItem;
import com.example.xxy.lovell.bean.PhotoInfo;
import com.example.xxy.lovell.httpUtils.bean.dynamic_data;
import com.example.xxy.lovell.httpUtils.bean.dynamic_item;
import com.example.xxy.lovell.mvp.CirclePresenter;
import com.example.xxy.lovell.utils.GlideCircleTransform;
import com.example.xxy.lovell.utils.UrlUtils;
import com.example.xxy.lovell.widget.MultiImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by xxy on 2017/2/5.
 */

public class CircleAdapter extends BaseRecycleViewAdapter{
    public final static int TYPE_HEAD = 0;
    private static final int STATE_IDLE = 0;
    private static final int STATE_ACTIVED = 1;
    private static final int STATE_DEACTIVED = 2;
    private int videoState = STATE_IDLE;
    public static final int HEADVIEW_SIZE = 1;

    int curPlayIndex=-1;

    private CirclePresenter presenter;
    private MainActivity context;
    List<PhotoInfo> photos;
    public CircleAdapter(MainActivity context){


        this.context = context;

    }
    @Override
    public int getItemViewType(int position) {
        /*if(position == 0){
            return TYPE_HEAD;
        }
        int itemType = 0;
        CircleItem item = (CircleItem) datas.get(position-1);
        if (CircleItem.TYPE_URL.equals(item.getType())) {
            itemType = CircleViewHolder.TYPE_URL;
        } else if (CircleItem.TYPE_IMG.equals(item.getType())) {
            itemType = CircleViewHolder.TYPE_IMAGE;
        } else if(CircleItem.TYPE_VIDEO.equals(item.getType())){
            itemType = CircleViewHolder.TYPE_VIDEO;
        }*/
        int itemType = 0;
        dynamic_item item = (dynamic_item) datas.get(position);
        if (item.getType().equals("1")) {
            itemType = CircleViewHolder.TYPE_IMAGE;
        }

        return itemType;



    }
    public void setCirclePresenter(CirclePresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_circle_item, parent, false);
        if(viewType == CircleViewHolder.TYPE_IMAGE){
                viewHolder = new ImageViewHolder(view);
        }
        return viewHolder;
    }
    public  String TimeStamp2Date(String timestampString) {
        String reuslt="";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        Date dynamicDate = new Date(timestamp);
        Date currentDate = new Date(System.currentTimeMillis());
        if(dynamicDate.getYear()!=currentDate.getYear()){
            reuslt=(currentDate.getYear()-dynamicDate.getYear())+"年前";
        }
        else if(dynamicDate.getMonth()!=currentDate.getMonth()){
            reuslt=(currentDate.getMonth()-dynamicDate.getMonth())+"月前";
        }
        else if(dynamicDate.getDay()!=currentDate.getDay()){
            reuslt=(currentDate.getDay()-dynamicDate.getDay())+"天前";
        }
        else if(dynamicDate.getMinutes()!=currentDate.getMinutes()){
            reuslt=(currentDate.getMinutes()-dynamicDate.getMinutes())+"分钟前";
        }
        else{
            reuslt="1分钟前";
        }
        return reuslt;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Log.e("初始化item1","");
        final CircleViewHolder holder = (CircleViewHolder) viewHolder;
        final dynamic_item  di= (dynamic_item) datas.get(position);
        String content=di.getContent();
        holder.contentTv.setText(UrlUtils.formatUrlString(content));
        holder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
        holder.timeTv.setText(TimeStamp2Date(di.getCreatetime()));
        holder.school.setText(di.getAuthor_info().getSchool()+" "+di.getAuthor_info().getEnteryea());
        holder.comment_count.setText(di.getComment());
        holder.praise_count.setText(di.getPraise());
        final List<String> imageUrls=di.getImg().getUrl().getThumb();
        final List<PhotoInfo> photos = new ArrayList<>();
        String mm="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487606542800&di=1529e0c4147f52c14ac202d29d04bb3c&imgtype=0&src=http%3A%2F%2Fimg457.ph.126.net%2F-cH_fsW6Yd6BSaKfCkDrXA%3D%3D%2F1575415444650233776.jpg";
        String mm1="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487602843577&di=4084e40ff0d6fc2fc6b7e61c1d2f169f&imgtype=0&src=http%3A%2F%2Ffun.youth.cn%2Fyl24xs%2F201702%2FW020170206491228078614.png";
       // String   mm="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487601598192&di=15641ef6c1b11895d39634af7bdba793&imgtype=0&src=http%3A%2F%2Fwx4.sinaimg.cn%2Fmw690%2F47145978ly1fbest7m7frj22io1ogkjm.jpg";
        for(String url:imageUrls){
            Log.e("图片获取",url);
            PhotoInfo p=new PhotoInfo(url);
            photos.add(p);
        }

        Glide.with(context).load(mm1).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.headIv);
        Log.e("初始化item2","");
        PhotoInfo p=new PhotoInfo(mm);
       // photos.add(p);
        holder.deleteStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(position);
                notifyDataSetChanged();
            }
        });
        if(holder instanceof ImageViewHolder){

              if (photos != null && photos.size() > 0) {
                         ((ImageViewHolder)holder).multiImageView.setVisibility(View.VISIBLE);
                         ((ImageViewHolder)holder).multiImageView.setList(photos);
                        ((ImageViewHolder)holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                     @Override
                     public void onItemClick(View view, int position) {
                         //imagesize是作为loading时的图片size
                         ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                         ImagePagerActivity.startImagePagerActivity(context,imageUrls , position, imageSize);

                                }
                            });
                        } else {
                            ((ImageViewHolder)holder).multiImageView.setVisibility(View.GONE);
                        }
        }
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dynamic_data",di);
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                context.startActivityForResult(intent, 0);
            }
        });


    }
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
