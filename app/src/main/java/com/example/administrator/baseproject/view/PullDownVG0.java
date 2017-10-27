package com.example.administrator.baseproject.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.administrator.baseproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */

public class PullDownVG0 extends LinearLayout{

    private final String TAG = "PullDownVG0";

    private final int MAX_PULLDOWN_DISTANCE = 500;

    private final int REFRESH_DISTANCE = 300;

    private Context mContext;

    private Scroller mScroller;

    public PullDownVG0(Context context) {
        this(context, null);
    }

    public PullDownVG0(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullDownVG0(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mScroller = new Scroller(context);
        setOrientation(LinearLayout.VERTICAL);
        addTextView();
        addListView();
    }

    int xxxx = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int xx = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xxxx = (int) event.getY();
            case MotionEvent.ACTION_MOVE:
                scrollTo(0, xxxx - xx);
                Rect rect = new Rect();
                getChildAt(0).getGlobalVisibleRect(rect);
                Log.d(TAG, "onTouchEvent: laalalalalal   " +
                        rect.centerY());
                return true;
            case MotionEvent.ACTION_UP:
                scrollTo(0, 0);
                return true;
            default:
                break;
        }
        return false;
    }

//    RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
//    RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
    int now;
    int move;
    int last;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        now = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                last = (int) ev.getY();
                if (getChildAt(1).canScrollVertically(-1) && getChildAt(1).canScrollVertically(1)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                move = now - last;

                //move > 0 down
                //move < 0 up
                boolean ifIntercept;
                if (move > 0) {
                    ifIntercept = isInterceptPullDown();
                } else {
                    ifIntercept = false;
                }
                return ifIntercept;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return false;
    }



    private void addTextView() {
        TextView tv = new TextView(mContext);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        tv.setLayoutParams(params);
        tv.setText("laerla!");
        tv.setTextSize(30);
        tv.setBackgroundColor(getResources().getColor(R.color.green));
        tv.setGravity(Gravity.CENTER);
        addView(tv, 0);
        Log.d(TAG, "init: " + tv.getTop());
    }

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
        addView(lv);
    }

    //是否拦截下拉
    // listview第一个item头部可见，且布局移动的没有达到最大值
    private boolean isInterceptPullDown() {
        ListView lv = (ListView) getChildAt(1);
        if (!lv.canScrollVertically(-1)) {
            return true;
        }
        return false;
    }

    //是否拦截上拉
    //listview头部
    private boolean isInterceptPullUp() {
        if (getChildAt(1).getTop() > 0 && getChildAt(1).getTop() < MAX_PULLDOWN_DISTANCE) {
            return true;
        }
        return false;
    }
}
