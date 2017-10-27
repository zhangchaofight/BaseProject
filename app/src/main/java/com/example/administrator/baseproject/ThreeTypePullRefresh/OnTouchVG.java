package com.example.administrator.baseproject.ThreeTypePullRefresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class OnTouchVG extends LinearLayout{

    private final String TAG = OnTouchVG.class.getSimpleName();

    public OnTouchVG(Context context) {
        this(context, null);
    }

    public OnTouchVG(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnTouchVG(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d(TAG, "init: EXECUTED");
        setOrientation(LinearLayout.VERTICAL);
        addListView();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d(TAG, "onScrollChanged: " + getScrollY());
    }

    private Context mContext = getContext();
    private Scroller mScroller = new Scroller(getContext());
    private void addListView() {
        ListView lv = new ListView(mContext);
        LayoutParams params0 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lv.setLayoutParams(params0);
        List<String> list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            list.add("no: " + i);
        }
        lv.setAdapter(new ArrayAdapter<String>
                (mContext, android.R.layout.simple_expandable_list_item_1, list));
        lv.setPressed(false);
        lv.setFocusable(false);
        lv.setFocusableInTouchMode(false);
        addView(lv);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: EXECUTED");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    int last = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getRawY();;
        Log.d(TAG, "onTouchEvent: EXECUTED");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                last = (int) event.getRawY();
                Log.d(TAG, "onTouchEvent: DOWN EXECUTED");
                break;
            case MotionEvent.ACTION_MOVE:
                scrollBy(0, last - y);
                Log.d(TAG, "onTouchEvent: MOVE EXECUTED distance : " + getScrollY());
                Log.d(TAG, "onTouchEvent: MOVE EXECUTED");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "onTouchEvent: UP EXECUTED");
                playAnimation(getScrollY());
                break;
        }
        last = y;
        return true;
    }

    private void playAnimation(int start) {
        ValueAnimator animator = ValueAnimator.ofInt(start, 0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int i = (int) (valueAnimator.getAnimatedValue());
                OnTouchVG.this.scrollTo(0, i);
            }
        });
        animator.start();
    }
}
