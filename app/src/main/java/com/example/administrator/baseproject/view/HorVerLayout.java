package com.example.administrator.baseproject.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/11/11.
 * 横向纵向组合的viewgroup
 */

public class HorVerLayout extends LinearLayout {

    private LayoutParams params;

    private int slop;
    private int lastY;
    private int lastX;

    public HorVerLayout(Context context) {
        this(context, null);
    }

    public HorVerLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorVerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        slop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int nowX = (int) ev.getRawX();
        int nowY = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = nowX;
                lastY = nowY;
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = nowX - lastX;
                int moveY = nowY - lastY;
                int move = (int) Math.sqrt(nowX * nowX + nowY * nowY);
                if (move >= slop && Math.abs(moveX) < Math.abs(moveY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        lastX = nowX;
        lastY = nowY;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int nowX = (int) ev.getRawX();
        int nowY = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = nowX;
                lastY = nowY;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        lastX = nowX;
        lastY = nowY;
        return true;
    }
}
