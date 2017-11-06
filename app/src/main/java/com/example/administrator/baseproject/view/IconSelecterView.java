package com.example.administrator.baseproject.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.baseproject.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class IconSelecterView extends LinearLayout {

    private float totalWidth;
    private float totalHeight;
    private float iconSize;
    private float centerDistance;
    private float leftMargin;
    
    private int defaultIconNum = 6;
    
    private MarginLayoutParams params;

    private int slop;
    
    private List<Bitmap> bitmapList; 
    
    public IconSelecterView(Context context) {
        super(context);
        initParams(context);
        setOrientation(HORIZONTAL);
    }

    public IconSelecterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParams(context);
        setOrientation(HORIZONTAL);
    }

    public IconSelecterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context);
        setOrientation(HORIZONTAL);
    }

    private void initParams(Context context) {
        totalHeight = context.getResources().getDimension(R.dimen.total_height);
        totalWidth = context.getResources().getDimension(R.dimen.total_width);
        centerDistance = context.getResources().getDimension(R.dimen.center_distance);
        iconSize = context.getResources().getDimension(R.dimen.icon_size);
        leftMargin = context.getResources().getDimension(R.dimen.left_margin);

        slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    }

    private ImageView getImageView() {
        ImageView iv = new ImageView(getContext());
        LayoutParams params = new LayoutParams((int) centerDistance, (int) centerDistance);
        iv.setLayoutParams(params);
        return iv;
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < defaultIconNum; i++) {
            addView(getImageView());
        }
        params = (MarginLayoutParams) getChildAt(0).getLayoutParams();
        params.leftMargin = (int) leftMargin;
        getChildAt(0).setLayoutParams(params);
    }

    private int lastX;
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

    // TODO: 2017/11/6 need improve 
    private int calculateDeltaX(int startX) {
        startX -= leftMargin;
        int maxX = (int) (centerDistance);
        if (startX >= maxX) {
            return maxX + (int) leftMargin;
        }
        int maxNum;
        if (null != bitmapList && bitmapList.size() > 0) {
            maxNum = bitmapList.size();
        } else {
            maxNum = defaultIconNum;
        }
        int minX = -(maxNum - 2) * (int) centerDistance;
        if (startX <= minX) {
            return minX + (int) leftMargin;
        }

        for (int i = 0; i < maxNum; i++) {
            int currentLeft = (int) centerDistance * i + params.leftMargin;
            if (currentLeft >= 0.5 * centerDistance && currentLeft < 1.5 * centerDistance) {
                return currentLeft;
            }
        }

        return 0;
    }

    private int calculateX(int startX) {
        int currentIndex = 0;
        if (getImageViewIndex(startX) < 0) {
            currentIndex = 0;
        }
        int itemCount = 0;
        if (bitmapList != null && bitmapList.size() > 0) {
            itemCount = bitmapList.size();
        } else {
            itemCount = defaultIconNum;
        }
        if (getImageViewIndex(startX) >= itemCount) {
            currentIndex = itemCount;
        }

        return 0;
    }

    private int currentIndex = 0;
    private int getImageViewIndex(int left) {
        left -= this.leftMargin;
        if (left == 0) {
            currentIndex = 0;
        } else {
            currentIndex =  -left / (int) centerDistance;
        }
        return currentIndex;
    }

    //
    private void doScale(int leftMargin, boolean orientation) {

    }

    private final int DURATION = 1000;
    private void playAnimation(int startX, int endX) {
        ValueAnimator animator = ValueAnimator.ofInt(startX, endX);
        int duration = DURATION;
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

            }
        });
        animator.start();
    }
}
