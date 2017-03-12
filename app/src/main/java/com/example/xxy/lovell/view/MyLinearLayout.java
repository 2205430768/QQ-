package com.example.xxy.lovell.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by a on 2016/5/16.
 */
public class MyLinearLayout extends LinearLayout{
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    /**
     * 判断是否需要中断事件的传递
     * 默认返回false 意思是，不中断
     * 返回true 意思是，中断
     */
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int width=getWidth()/getChildCount();
        //获得listview的个数
        int count=getChildCount();

        event.setLocation(width / 2, event.getY());
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            try {
                child.dispatchTouchEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }
}
