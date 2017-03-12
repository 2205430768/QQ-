package com.example.xxy.lovell.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.xxy.lovell.MyApplication;
import com.example.xxy.lovell.R;
import com.example.xxy.lovell.adapter.MyFragmentPageAdapter;
import com.example.xxy.lovell.fragment.CommunityFragment;
import com.example.xxy.lovell.fragment.DynamicStatusFragment;
import com.example.xxy.lovell.image_process.Bimp;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener{

    ViewPager mViewPager;
    List<Fragment> fragments;
    Button people;
    Button status;
    RadioGroup bottomRadioGroup;
    RadioButton weChatBt, phoneBt, findBt, mineBt;
    MyFragmentPageAdapter myFragmentPageAdapter;
    Button searchOrSendStaus;
    private boolean  isInPeoplePage;
    DynamicStatusFragment dsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        people= (Button) findViewById(R.id.people);
        status= (Button) findViewById(R.id.status);
        initData();
        initView();
        MyApplication.setContext(getApplicationContext());
    }

   private void initView() {
        myFragmentPageAdapter = new MyFragmentPageAdapter(
                getSupportFragmentManager(),  this, fragments);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
       // tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager.setAdapter(myFragmentPageAdapter);
        //tabLayout.setupWithViewPager(mViewPager);
        bottomRadioGroup = (RadioGroup) findViewById(R.id.bottom_tab);
        weChatBt = (RadioButton) findViewById(R.id.bottom_tab_wechat_bt);
        phoneBt = (RadioButton) findViewById(R.id.bottom_tab_phone_bt);
        findBt = (RadioButton) findViewById(R.id.bottom_tab_find_bt);
        mineBt = (RadioButton) findViewById(R.id.bottom_tab_mine_bt);
        bottomRadioGroup.setOnCheckedChangeListener(this);
        mViewPager.addOnPageChangeListener(this);
        searchOrSendStaus= (Button) findViewById(R.id.searchorsendstatus);
        searchOrSendStaus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(isInPeoplePage){

               }
               else{
                   Intent intent = new Intent(MainActivity.this,
                           PublishedActivity.class);
                   startActivity(intent);
                   Bimp.act_bool = false;
               }

           }
       });
    }

    private void initData() {
         isInPeoplePage=true;
         fragments = new ArrayList<>();
         fragments.add(new CommunityFragment());
         dsp=new DynamicStatusFragment();
         dsp.mainActivity=this;
         fragments.add(dsp);


    }
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        Log.e("MainActivity","requestCode:"+requestCode+"  resultCode"+resultCode);
        switch ( resultCode ) {
            case RESULT_OK :

                break;
            default :
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (mViewPager.getCurrentItem()) {
                case 0:
                    isInPeoplePage=true;
                    searchOrSendStaus.setBackgroundResource(R.drawable.search);
                    weChatBt.setChecked(true);
                    people.setTextColor(0xff000000);
                    status.setTextColor(0xff999999);
                    break;
                case 1:
                    isInPeoplePage=false;
                    searchOrSendStaus.setBackgroundResource(R.drawable.camera);

                    phoneBt.setChecked(true);
                    people.setTextColor(0xff999999);
                    status.setTextColor(0xff000000);
                    break;

            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Activity 销毁","保存数据");
        //dsp.saveData();//保存动态的内容到磁盘中










    }
}
