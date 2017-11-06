package com.example.administrator.baseproject.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/3.
 */

public class MyImageChooserView extends LinearLayout{

    private final String TAG = MyImageChooserView.class.getSimpleName();

    private int viewWidth;

    public MyImageChooserView(Context context) {
        super(context);
        initView();
    }

    public MyImageChooserView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyImageChooserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    int slop;
    private void initView() {
        setOrientation(HORIZONTAL);
        slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        addView();
    }

    private MarginLayoutParams params;
    private void addView() {
        int[] colors = {Color.BLACK, Color.BLUE, Color.GREEN, Color.GRAY};
        for (int i = 0; i < 4; i++) {
            TextView view = new TextView(getContext());
            view.setText("" + i);
            view.setGravity(Gravity.CENTER);
            LayoutParams params = new LayoutParams(800, 500);
            view.setLayoutParams(params);
            view.setBackgroundColor(colors[i]);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: ");
                }
            });
            addView(view);
        }
        params = (MarginLayoutParams) getChildAt(0).getLayoutParams();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        viewWidth = getChildAt(0).getMeasuredWidth();
        getTotalWidth();
        getViewsCenter();
    }

    private int totalWidth = 0;
    private void getTotalWidth() {
        totalWidth = viewWidth * getChildCount();
    }

    int[] centersX;
    private void getViewsCenter() {
        int count = getChildCount();
        int delta = viewWidth / 2;
        centersX = new int[count];
        for (int i = 0; i < count; i++) {
            centersX[i] = delta * (i * 2 - 1);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int now = (int) ev.getRawX();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = now;
                break;
            case MotionEvent.ACTION_MOVE:
                int move = lastX - now;
                move = Math.abs(move);
                if (move >= slop) {
                    return true;
                }
        }
        lastX = now;
        return super.onInterceptTouchEvent(ev);
    }

    int lastX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int nowX = (int) event.getRawX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = nowX;
                break;
            case MotionEvent.ACTION_MOVE:
                int move = nowX - lastX;
                params.leftMargin += move;
                getChildAt(0).setLayoutParams(params);
                Log.d(TAG, "onTouchEvent: " + getCurrentIndex(params.leftMargin));
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                params.leftMargin = calculateDeltaX(params.leftMargin);
                getChildAt(0).setLayoutParams(params);
                break;
        }
        lastX = nowX;
        return true;
    }

    private int getCurrentIndex(int movedX) {
        return -movedX / viewWidth;
    }

    private int calculateDeltaX(int movedX) {
        if (movedX >= 0) {
            return 0;
        }
        int min = -(getChildCount() - 1) * viewWidth;
        if (movedX < min) {
            return -(getChildCount() - 1) * viewWidth;
        }
        int result = 0 ;
        for (int i = 0; i < getChildCount(); i++) {
            if (params.leftMargin + viewWidth * i >= -0.5 * viewWidth &&
                    params.leftMargin + viewWidth * i < 0.5 * viewWidth) {
                result = i;
                break;
            }
        }
        return - result * viewWidth;
    }

    boolean isHide = true;
    private void playAnimation() {
        final LinearLayout.LayoutParams params = new LayoutParams(0, 0);
        int start = 1;
        int end = 0;
        final int DURATION = 500;
        if (isHide) {
            int temp = end;
            end = start;
            start = temp;
        }
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                params.topMargin = (int) valueAnimator.getAnimatedValue();
            }
        });
        animator.start();
    }
}
