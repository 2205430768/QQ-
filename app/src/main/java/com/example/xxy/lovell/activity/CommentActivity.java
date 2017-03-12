package com.example.xxy.lovell.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.xxy.lovell.R;
import com.example.xxy.lovell.adapter.CommentAdapter;
import com.example.xxy.lovell.adapter.MyItemDecoration;
import com.example.xxy.lovell.adapter.viewholder.ImageViewHolder;
import com.example.xxy.lovell.bean.CommentConfig;
import com.example.xxy.lovell.bean.PhotoInfo;
import com.example.xxy.lovell.emotioninput.ColorTextSpan;
import com.example.xxy.lovell.emotioninput.EmoticonSpan;
import com.example.xxy.lovell.emotioninput.SmileyDataSet;
import com.example.xxy.lovell.emotioninput.SmileyInputRoot;
import com.example.xxy.lovell.httpUtils.bean.dynamic_data;
import com.example.xxy.lovell.httpUtils.bean.dynamic_item;
import com.example.xxy.lovell.utils.CommonUtils;
import com.example.xxy.lovell.utils.GlideCircleTransform;
import com.example.xxy.lovell.utils.UrlUtils;
import com.example.xxy.lovell.view.MyRecyclerView;
import com.example.xxy.lovell.widget.CommentListView;
import com.example.xxy.lovell.widget.DivItemDecoration;
import com.example.xxy.lovell.widget.ExpandTextView;
import com.example.xxy.lovell.widget.MultiImageView;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    Handler h=new Handler();
    private MyRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    CommentAdapter commentAdapter;
    dynamic_item mdynamic_item=null;
    int position;
    private ImageView headImage;
    private Button returnBack;
    private TextView nameTv;
    private TextView timeTv;
    private TextView school;
    private ExpandTextView contentTv;
    private MultiImageView multiImageView;
    private TextView praise_count;
    private TextView comment_count;
    private  Button liaota;
    private LinearLayout comment;
    private LinearLayout bottom_layout;
    private SmileyInputRoot rootView;
    private int selectCircleItemH;
    private LinearLayout edit_layout;
    private LinearLayout comment_all;
    private  EditText input;
    private Map<String,String> pictureMaps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        mdynamic_item= (dynamic_item) bundle.getSerializable("dynamic_data");
        position=bundle.getInt("position");

        initView();
        initData();
    }
    public void insertSmiley(TextView text,String s, Drawable drawable) {
        if (drawable != null) {
            EmoticonSpan emoticonSpan = new EmoticonSpan(drawable);
            int start = text.getSelectionStart();
            int end = text.getSelectionEnd();
            Editable editableText = text.getEditableText();
            // Insert the emoticon.
            editableText.replace(start, end, s);
            editableText.setSpan(emoticonSpan, start, start + s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
    public void insertString(TextView text,String s) {
        int start = text.getSelectionStart();
        int end = text.getSelectionEnd();
        Editable editableText = text.getEditableText();

        editableText.replace(start, end, s);
        editableText.setSpan(new ColorTextSpan(), start, start + s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    private void initData() {
        String mm1="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487602843577&di=4084e40ff0d6fc2fc6b7e61c1d2f169f&imgtype=0&src=http%3A%2F%2Ffun.youth.cn%2Fyl24xs%2F201702%2FW020170206491228078614.png";
        Glide.with(getApplicationContext()).load(mm1).diskCacheStrategy(DiskCacheStrategy.ALL).
                placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(getApplicationContext())).into(headImage);
        timeTv.setText(TimeStamp2Date(mdynamic_item.getCreatetime()));
        school.setText(mdynamic_item.getAuthor_info().getSchool());
        contentTv.setText(UrlUtils.formatUrlString(mdynamic_item.getContent()));
        contentTv.setVisibility(TextUtils.isEmpty(mdynamic_item.getContent()) ? View.GONE : View.VISIBLE);
        praise_count.setText("赞 "+mdynamic_item.getPraise());
        comment_count.setText("评论 "+mdynamic_item.getComment());
        pictureMaps=new HashMap<>();
        String[] smileyArray1 = getApplicationContext().getResources().getStringArray( R.array.smiley_tieba);
        String[] smileyArray2 = getApplicationContext().getResources().getStringArray( R.array.smiley_acn);
        for (String aSmileyArray : smileyArray1) {
            pictureMaps.put(aSmileyArray.split(",")[1],SmileyDataSet.SMILEY_BASE+aSmileyArray.split(",")[0]);
        }
        for (String aSmileyArray : smileyArray2) {
            pictureMaps.put(aSmileyArray.split(",")[1],SmileyDataSet.SMILEY_BASE+aSmileyArray.split(",")[0]);
        }

    }

    private void initView() {
        recyclerView= (MyRecyclerView) findViewById(R.id.comment_recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());
       // recyclerView.addItemDecoration(new DivItemDecoration(2, true));
        //recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               /* if (edittextbody.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE, null);
                    return true;
                }*/
                return false;
            }
        });
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //recyclerView.setRefreshing(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 2000);
            }
        };
        //recyclerView.setRefreshListener(refreshListener);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                //recyclerView.setRefreshing(false);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){

                    Glide.with(getApplicationContext()).resumeRequests();
                   // bottom_layout.setVisibility(View.VISIBLE);
                }else{
                    Glide.with(getApplicationContext()).pauseRequests();
                   // bottom_layout.setVisibility(View.GONE);

                }

            }
        });
        /*recyclerView.getSwipeToRefresh().post(new Runnable(){
            @Override
            public void run() {
                recyclerView.setRefreshing(true);//执行下拉刷新的动画
                refreshListener.onRefresh();//执行数据加载操作
            }
        });*/
        recyclerView.setMyRecyclerViewListener(new MyRecyclerView.MyRecyclerViewListener() {
            @Override
            public void onRefresh() {
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setRefreshComplete();
                    }
                },1500);
            }

            @Override
            public void onLoadMore() {
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        recyclerView.setLoadMoreComplete();
                    }
                },1000);
            }
        });
        commentAdapter=new CommentAdapter(getApplicationContext());
        recyclerView.setAdapter(commentAdapter);
        recyclerView.clearAnimation();

        headImage= (ImageView) findViewById(R.id.headIv);
        returnBack= (Button) findViewById(R.id.comment_return);
        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(-1);
                finish();
            }
        });
        nameTv= (TextView) findViewById(R.id.nameTv);
        timeTv= (TextView) findViewById(R.id.timeTv);
        school= (TextView) findViewById(R.id.school);
        contentTv = (ExpandTextView) findViewById(R.id.contentTv);
        ViewStub viewStub = (ViewStub) findViewById(R.id.viewStub);
        if(mdynamic_item.getType().equals("1")){
            viewStub.setLayoutResource(R.layout.viewstub_imgbody);
            View subView = viewStub.inflate();
            MultiImageView multiImageView = (MultiImageView) subView.findViewById(R.id.multiImagView);
            if(multiImageView != null){
                 this.multiImageView = multiImageView;
                 final List<String> imageUrls=mdynamic_item.getImg().getUrl().getThumb();
                 final List<PhotoInfo> photos = new ArrayList<>();
                 for(String url:imageUrls){
                     PhotoInfo p=new PhotoInfo(url);
                     photos.add(p);
                 }
                if (photos != null && photos.size() > 0) {
                        multiImageView.setVisibility(View.VISIBLE);
                        multiImageView.setList(photos);
                        multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            //imagesize是作为loading时的图片size
                            ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                            ImagePagerActivity.startImagePagerActivity(CommentActivity.this,imageUrls , position, imageSize);

                        }
                    });
                   }
                   else {
                         multiImageView.setVisibility(View.GONE);
                     }
            }
        }

        praise_count= (TextView) findViewById(R.id.praise_count);
        comment_count= (TextView) findViewById(R.id.comment_count);
        liaota= (Button) findViewById(R.id.liaota);
        comment= (LinearLayout) findViewById(R.id.comment);
        bottom_layout= (LinearLayout) findViewById(R.id.bottom_layout);
        edit_layout= (LinearLayout) findViewById(R.id.edit_layout);
        input = (EditText) findViewById(R.id.ed_comment);
        View smileyBtn = findViewById(R.id.btn_emotion);
        View btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     Log.e("获取的内容是","content "+input.getText());
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEditTextBodyVisible(View.VISIBLE);
            }
        });
        rootView = (SmileyInputRoot) findViewById(R.id.root);
        rootView.initSmiley(input, smileyBtn, btnSend);
        comment_all= (LinearLayout) findViewById(R.id.comment_all);
        comment_all.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (input.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE);
                    return true;
                }
                return false;
            }
        });
    }

    public void updateEditTextBodyVisible(int visibility) {
        edit_layout.setVisibility(visibility);

        if(View.VISIBLE==visibility){
            input.requestFocus();
            //弹出键盘
            CommonUtils.showSoftInput(input.getContext(),  input);

        }else if(View.GONE==visibility){
            //隐藏键盘
            CommonUtils.hideSoftInput( input.getContext(),  input);
        }
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
}
