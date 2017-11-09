package com.example.administrator.baseproject.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
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

    private final static int DURATION = 200;

    private int currentIndex = 1;
    private int defaultIconNum = 5;

    private MarginLayoutParams params;
    private float centerDistance;
    private float leftMargin;
    private int slop;
    private int lastX;
    boolean isLayouted = false;
    
    private List<Bitmap> bitmapList;

    public IconSelecterView(Context context) {
        super(context);
        initParams(context);
        setOrientation(HORIZONTAL);
        initView();
    }

    public IconSelecterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParams(context);
        setOrientation(HORIZONTAL);
        initView();
    }

    public IconSelecterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context);
        setOrientation(HORIZONTAL);
        initView();
    }

    private void initParams(Context context) {
        centerDistance = context.getResources().getDimension(R.dimen.center_distance);
        leftMargin = context.getResources().getDimension(R.dimen.left_margin);

        slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private void initView() {
        for (int i = 0; i < defaultIconNum; i++) {
            addView(getImageView());
        }
    }

    private ImageView getImageView() {
        ImageView iv = new ImageView(getContext());
        LayoutParams params = new LayoutParams((int) centerDistance, (int) centerDistance);
        params.gravity = Gravity.CENTER;
        iv.setLayoutParams(params);
        iv.setScaleX(0.5f);
        iv.setScaleY(0.5f);
        return iv;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!isLayouted) {
            Resources res = getContext().getResources();
            ((ImageView)getChildAt(0)).
                    setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.a));
            ((ImageView)getChildAt(1)).
                    setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.b));
            ((ImageView)getChildAt(2)).
                    setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.c));
            ((ImageView)getChildAt(3)).
                    setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.d));
            ((ImageView)getChildAt(4)).
                    setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.e));

            params = (MarginLayoutParams) getChildAt(0).getLayoutParams();
            params.leftMargin = (int) leftMargin;
            getChildAt(0).setLayoutParams(params);
            getChildAt(1).setScaleX(1f);
            getChildAt(1).setScaleY(1f);
            isLayouted = true;
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
                doScale(params.leftMargin);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                playActionUpAnimation(params.leftMargin);
                break;
        }
        lastX = nowX;
        return true;
    }

    private int calculateX(int startX) {
        if (startX >= centerDistance / 2) {
            return (int) centerDistance;
        }
        if (startX <= -centerDistance * (defaultIconNum - 2)) {
            return -(int) centerDistance * (defaultIconNum - 2);
        }
        int index = Math.abs(startX) / (int) centerDistance;
        int delta = Math.abs(startX) % (int) centerDistance;
        if (delta > centerDistance / 2) {
            index++;
        }
        return -index * (int) centerDistance;
    }

    private void doScale(int leftMargin) {
        int index = -leftMargin / (int) centerDistance + 1;
        int delta = Math.abs(leftMargin) % (int) centerDistance;
        float scale = 1 - (float) delta / (2 * centerDistance);
        if (scale < 0.5f) {
            scale = 0.5f;
        }
        if (leftMargin >= 0) {
            index--;
            scale = 1.5f - scale;
        }
        currentIndex = index;
        if (index >= 0 && index < defaultIconNum) {
            getChildAt(index).setScaleX(scale);
            getChildAt(index).setScaleY(scale);
        }
        index++;
        if (index >= 0 && index < defaultIconNum) {
            getChildAt(index).setScaleX(1.5f - scale);
            getChildAt(index).setScaleY(1.5f - scale);
        }
    }

    private void playActionUpAnimation(final int leftMargin) {
        int endMargin = calculateX(leftMargin);
        ValueAnimator animator = ValueAnimator.ofInt(leftMargin, endMargin);
        int duration = DURATION;
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int left = (int) valueAnimator.getAnimatedValue();
                params.leftMargin = left;
                getChildAt(0).setLayoutParams(params);
                doScale(left);
            }
        });
        animator.start();
    }

    public void setImages(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            if (null != bitmapList.get(i)) {
                ((ImageView) getChildAt(i)).setImageBitmap(bitmapList.get(i));
            }
        }
    }

    public int getCurrentIndex() {
        if (currentIndex < 0) {
            return 0;
        } else if (currentIndex >= defaultIconNum) {
            return defaultIconNum - 1;
        }
        return currentIndex;
    }
}
