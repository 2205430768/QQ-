package com.example.xxy.lovell.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wang on 16/7/25.
 */
public class MyFragmentPageAdapter extends FragmentPagerAdapter {


    private Context context;
    private List<Fragment> fragments;

    public MyFragmentPageAdapter(FragmentManager fm, Context context, List<Fragment> fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;


    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}

