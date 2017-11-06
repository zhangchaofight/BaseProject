package com.example.administrator.baseproject.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.baseproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class OWLodingView extends View {

    private static final float K = 1.73f;

    private float width;
    private float space;

    private float totalWidth;
    private float totalHeight;

    private List<ZCShapeView> mList;

    public OWLodingView(Context context) {
        super(context);
        init(context);
    }

    public OWLodingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OWLodingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            mList.add(new ZCShapeView(context));
        }

        width = context.getResources().getDimension(R.dimen.OWLoading_width);
        space = context.getResources().getDimension(R.dimen.OWLoading_space);
        totalWidth = 6 * width + 2 * space;
        totalHeight = 10 * width / K + space * K;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) totalWidth, (int) totalHeight);
    }

    private ValueAnimator animator;
    private void getAnimatation () {

    }

    public void play() {
        
    }

    public void pause() {

    }

    public void restart() {

    }

    public void stop() {

    }
}
