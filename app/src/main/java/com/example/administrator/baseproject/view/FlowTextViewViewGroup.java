package com.example.administrator.baseproject.view;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17.
 * 使用ViewGroup，自己重写onMearsure，重写onLayout手动指定子元素的位置
 */

public class FlowTextViewViewGroup extends ViewGroup {

    private final String TAG = FlowTextViewViewGroup.class.getSimpleName();

    //默认的布局宽度
    private int MAX_LENGTH = 600;

    //统计当前行的长度以及布局总高度
    private int width = 0;
    private int height = 0;

    //左右间距
    private int paddingWidth = 40;
    //上下间距
    private int paddingHeight = 30;

    //设置TextView的显示样式
    private GradientDrawable gradientDrawable;

    public FlowTextViewViewGroup(Context context) {
        this(context, null);
    }

    public FlowTextViewViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowTextViewViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        addStringList();
    }

    //默认宽度全屏
    private void init() {
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        MAX_LENGTH = dm.widthPixels;
        Log.d(TAG, "init: " + MAX_LENGTH);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int padding = paddingWidth;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            int h = view.getMeasuredHeight();
            int w = view.getMeasuredWidth();
            if (w + 2 * paddingWidth >= MAX_LENGTH + 1) {
                Log.e(TAG, "error : onLayout: this view over limit : " + w);
                continue;
            }
            if (width + w + padding <= MAX_LENGTH) {
                view.layout(width + padding, height + paddingHeight,
                        width + w + padding, height + h + paddingHeight);
                Log.d(TAG, "onLayout: " + i + " " + width + " " + height);
                width += w;
                padding += paddingWidth;
            } else {
                padding = paddingWidth;
                width = 0;
                height = height + h + paddingHeight;
                view.layout(width + padding, height + paddingHeight,
                        width + w + padding, height + h + paddingHeight);
                Log.d(TAG, "onLayout: " + i + " " + width + " " + height);
                width += w;
                padding += paddingWidth;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void addTextView(String content) {

        int strokeWidth = 5;
        int roundRadius = 15;
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        int strokeColor = Color.parseColor("#2E3135");//边框颜色
        int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);

        TextView tv = new TextView(getContext());
        LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setText(content);
        tv.setSingleLine(true);
        tv.setLayoutParams(params);
        tv.setBackground(gd);
        addView(tv);
    }

    //外部设置需要String的List
    public void addStringList(List<String> list) {
        for (String s : list) {
            addTextView(s);
        }
    }

    //测试的字符串list
    private void addStringList(){
        String[] arr = {"当我们在写带有UI的程序的时候", "如果想获取输入事件", "仅仅是写一个回调函数",
                "比如(onKeyEvent,onTouchEvent….)", "输入事件有可能来自按键的", "来自触摸的",
                "也有来自键盘的", "其实软键盘也是一种独立的输入事件", "那么为什么我能通过回调函数获取这些输入事件呢",
                "系统是如何精确的让程序获得输入事件并去响应的呢",
                "为什么系统只能同一时间有一个界面去获得触摸事件呢？ 下面我们通过Android系统输入子系统的分析来回答这些问题"};
        for (String s : arr) {
            addTextView(s);
        }
    }
}
