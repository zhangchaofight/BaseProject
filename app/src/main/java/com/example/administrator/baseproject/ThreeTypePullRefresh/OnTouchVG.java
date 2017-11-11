package com.example.administrator.baseproject.ThreeTypePullRefresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.Toast;

import com.example.administrator.baseproject.view.ZCToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class OnTouchVG extends LinearLayout{

    private Context mContext = getContext();

    private ValueAnimator animator;

    private LayoutParams params;

    private int slop;
    private int lastY;

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
        slop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        setOrientation(LinearLayout.VERTICAL);
        addListView();
    }

    private void addListView() {
        ListView lv = new ListView(mContext);
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lv.setLayoutParams(params);
        List<String> list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            list.add("no: " + i);
        }
        lv.setAdapter(new ArrayAdapter<String>
                (mContext, android.R.layout.simple_expandable_list_item_1, list));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(mContext, "aaa", Toast.LENGTH_SHORT).show();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(mContext, "bbb", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        addView(lv);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int nowY = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = nowY;
                break;
            case MotionEvent.ACTION_MOVE:
                int move = nowY - lastY;
                if (Math.abs(move) <= slop) {
                    break;
                }
                if (isListViewTopShowing() && move > slop) {
                    params.topMargin = 0;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        lastY = nowY;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int move;
                move = lastY - y;
                params = (LayoutParams) getChildAt(0).getLayoutParams();
                params.topMargin -= move;
                getChildAt(0).setLayoutParams(params);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                playAnimation();
                break;
        }
        lastY = y;
        return true;
    }

    private void playAnimation() {
        animator = ValueAnimator.ofInt(params.topMargin, 0);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int i = (int) (valueAnimator.getAnimatedValue());
                params.topMargin = i;
                getChildAt(0).setLayoutParams(params);
            }
        });
        animator.start();
    }

    private boolean isListViewTopShowing() {
        ListView view = (ListView) getChildAt(0);
        int pos = view.getFirstVisiblePosition();
        int topMagin = -1;
        if (null != view.getChildAt(pos)) {
            topMagin = view.getChildAt(pos).getTop();
        }
        return (pos == 0) && (topMagin == 0);
    }
}
