package com.example.xxy.lovell.fragment;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xxy.lovell.R;
import com.example.xxy.lovell.activity.MainActivity;
import com.example.xxy.lovell.adapter.CircleAdapter;
import com.example.xxy.lovell.bean.CircleItem;
import com.example.xxy.lovell.bean.CommentConfig;
import com.example.xxy.lovell.bean.CommentItem;
import com.example.xxy.lovell.bean.FavortItem;
import com.example.xxy.lovell.constants.Constant;
import com.example.xxy.lovell.httpUtils.HttpUtils;
import com.example.xxy.lovell.httpUtils.bean.dynamic_item;
import com.example.xxy.lovell.libcore.io.DiskLruCache;
import com.example.xxy.lovell.mvp.CircleContract;
import com.example.xxy.lovell.mvp.CirclePresenter;
import com.example.xxy.lovell.widget.DivItemDecoration;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xxy on 2017/1/23.
 */

public class DynamicStatusFragment extends Fragment implements CircleContract.View{
    View mView;
    private SuperRecyclerView recyclerView;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private CirclePresenter presenter;
    private LinearLayoutManager layoutManager;
    private LinearLayout edittextbody;
    private CircleAdapter circleAdapter;
    private final static int TYPE_PULLREFRESH = 1;
    private final static int TYPE_UPLOADREFRESH = 2;
    public MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dynamic_status, container, false);
        presenter = new CirclePresenter(this);
        initView();
        recyclerView.getSwipeToRefresh().post(new Runnable(){
            @Override
            public void run() {
                recyclerView.setRefreshing(true);//执行下拉刷新的动画
                refreshListener.onRefresh();//执行数据加载操作
            }
        });
        return mView;
    }

    private void initView() {
        edittextbody = (LinearLayout)mView.findViewById(R.id.editTextBodyLl);
        recyclerView = (SuperRecyclerView)mView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DivItemDecoration(2, false));
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.loadData(TYPE_PULLREFRESH);
                    }
                }, 2000);
            }
        };
        recyclerView.setRefreshListener(refreshListener);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    Glide.with(getContext()).resumeRequests();
                }else{
                    Glide.with(getContext()).pauseRequests();
                }

            }
        });
        circleAdapter = new CircleAdapter(mainActivity);
        circleAdapter.setCirclePresenter(presenter);
        //saveData();
        loadDataFromDisk();
        recyclerView.setAdapter(circleAdapter);



    }

    @Override
    public void update2DeleteCircle(String circleId) {

    }

    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {

    }

    @Override
    public void update2DeleteFavort(int circlePosition, String favortId) {

    }

    @Override
    public void update2AddComment(int circlePosition, CommentItem addItem) {

    }

    @Override
    public void update2DeleteComment(int circlePosition, String commentId) {

    }

    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {

    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        }
        else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public String getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
            Log.e("磁盘路径1",cachePath);
        } else {
            cachePath = context.getCacheDir().getPath();
            Log.e("磁盘路径2",cachePath);
        }
        Log.e("磁盘路径   获取磁盘",cachePath);
        return cachePath + File.separator + uniqueName;
    }
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("动态销毁","保存数据");
      //  saveData();

    }

    @Override
    public void onStop() {
        super.onStop();
        saveData();
    }

    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    public void saveData(){
        List<dynamic_item> datas=circleAdapter.getDatas();
       // Log.e("eeeeeee从磁盘保存数据","保存数据");
        if(datas!=null&&datas.size()>0){
            try{
                if(isSdCardExist()){
                    //Log.e("从磁盘保存数据1","保存数据 ");
                    String outFile=getDiskCacheDir(getContext(), Constant.DYNAMIC_OUT);
                   // String outFile=Environment.getExternalStorageDirectory()+"/"+Constant.DYNAMIC_OUT;
                    //Log.e("从磁盘保存数据2","保存数据 "+outFile);
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outFile));
                    //Log.e("从磁盘保存数据3","保存数据 "+outFile);
                    out.writeObject(datas);
                    //Log.e("从磁盘保存数据4","保存数据 "+outFile);
                   // Log.e("从磁盘保存数据","保存数据成功");
                    //out.writeObject(datas);
                    out.close();

                }
                else{
                    Log.e("从磁盘保存数据","SD卡不存在");
                }

            } catch (Exception e) {
                Log.e("从磁盘保存数据","保存异常");
                e.printStackTrace();
            }

        }


    }
    public void loadDataFromDisk(){
          ObjectInputStream in=null;
            try{
                if(isSdCardExist()){

               // String outFile=Environment.getExternalStorageDirectory()+"/"+Constant.DYNAMIC_OUT;
               String outFile=getDiskCacheDir(getContext(), Constant.DYNAMIC_OUT);
                File f=new File(outFile);
                if(f.exists()) {
                    in = new ObjectInputStream(new FileInputStream(outFile));
                    List<dynamic_item> datas= (List<dynamic_item>) in.readObject();
                    if(datas!=null&& datas.size()>0){
                        circleAdapter.getDatas().addAll(datas);
                        //circleAdapter.notifyDataSetChanged();
                    }
                }
                   /* List<dynamic_item> datas = (ArrayList<dynamic_item>) in.readObject();
                    if(datas!=null&& datas.size()>0){
                        circleAdapter.getDatas().addAll(datas);
                        Log.e("从磁盘保存数据","加载数据成功");
                    }*/

                }
            } catch (Exception e) {
                Log.e("从磁盘加载数据","加载异常");

                e.printStackTrace();
            }
        finally {
                if(in!=null){
                    try {
                        in.close();
                    }
                    catch (Exception ee){
                        Log.e("从磁盘加载数据","关闭文件异常");
                    }

                }
            }



    }

    @Override
    public void update2loadData(int loadType, List<dynamic_item> datas ) {
        if(datas.size()==0){
            if(!isNetworkAvailable(getContext())){
                Toast.makeText(getContext(), "请检查网络,网络不可用", Toast.LENGTH_SHORT).show();
            }
           if(circleAdapter.getDatas().size()==0) {
               loadDataFromDisk();
           }

        }
        else{
            if (loadType == TYPE_PULLREFRESH){
                recyclerView.setRefreshing(false);
                circleAdapter.setDatas(datas);
            }else if(loadType == TYPE_UPLOADREFRESH){
                circleAdapter.getDatas().addAll(datas);
            }
        }


        circleAdapter.notifyDataSetChanged();

        if(circleAdapter.getDatas().size()<45 + CircleAdapter.HEADVIEW_SIZE){
            recyclerView.setupMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.loadData(TYPE_UPLOADREFRESH);
                        }
                    }, 2000);

                }
            }, 1);
        }else{
            recyclerView.removeMoreListener();
            recyclerView.hideMoreProgress();
        }

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }
}
