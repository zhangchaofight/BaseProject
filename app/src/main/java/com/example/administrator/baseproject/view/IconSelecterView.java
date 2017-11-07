package com.example.administrator.baseproject.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    
    private int defaultIconNum = 5;
    
    private MarginLayoutParams params;

    private int slop;
    
    private List<Bitmap> bitmapList; 

    private int leftBorder;
    private int rightBorder;

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
        totalHeight = context.getResources().getDimension(R.dimen.total_height);
        totalWidth = context.getResources().getDimension(R.dimen.total_width);
        centerDistance = context.getResources().getDimension(R.dimen.center_distance);
        iconSize = context.getResources().getDimension(R.dimen.icon_size);
        leftMargin = context.getResources().getDimension(R.dimen.left_margin);

        slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        initBorder();
    }

    private void initBorder() {
        leftBorder = (int) (0.5 * centerDistance + leftMargin);
        rightBorder = (int) (1.5 * centerDistance + leftMargin);
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

    private TextView getTextView() {
        TextView iv = new TextView(getContext());
        LayoutParams params = new LayoutParams((int) centerDistance, (int) centerDistance);
        params.gravity = Gravity.CENTER;
        iv.setLayoutParams(params);
        iv.setScaleX(0.5f);
        iv.setScaleY(0.5f);
        return iv;
    }

    boolean isLayouted = false;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d("zzzzz", "onLayout: executed");

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
            isLayouted = true;
        }
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
//                doScale(getChildAt(currentIndex), calculateDelta(params.leftMargin));
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                params.leftMargin = calculateX(params.leftMargin);
                getChildAt(0).setLayoutParams(params);
                break;
        }
        lastX = nowX;
        return true;
    }

    private int calculateX(int startX) {
        if (startX >= 1.5 * centerDistance + leftMargin) {
            return (int) centerDistance + (int) leftMargin;
        }
        float min = -(float) (((float) defaultIconNum - 0.5) * centerDistance - leftMargin);
        if (startX <= min) {
            return -(int) (((float) defaultIconNum - 1) * centerDistance - leftMargin);
        }
        int index = 0;
        int leftBorder = (int) (0.5 * centerDistance + leftMargin);
        int rightBorder = (int) (1.5 * centerDistance + leftMargin);
        for (int i = 0; i < defaultIconNum; i++) {
            int itemLeft = (int) (startX + i * centerDistance);
            if (itemLeft >= leftBorder && itemLeft < rightBorder) {
                index = i;
                break;
            }
        }
        return -(int) ((index - 0.5) * centerDistance - leftMargin);
    }

    private int currentIndex = 0;
    private int getImageViewIndex(int left) {
        left -= leftMargin;
        if (left == 0) {
            currentIndex = 0;
        } else {
            currentIndex =  -left / (int) centerDistance;
        }
        return currentIndex;
    }

    //true->right  false->left
    private void doScale(View view, int delta) {
        float scale;
        if (delta == 0) {
            scale = 2;
        } else {
            scale = - (float) delta / centerDistance + 2f;
        }
        view.setScaleX(scale);
        view.setScaleY(scale);
    }

    private int calculateDelta(int startX) {
        int itemLeft = 0;
        int leftBorder = (int) (0.5 * centerDistance + leftMargin);
        int rightBorder = (int) (1.5 * centerDistance + leftMargin);
        for (int i = 0; i < defaultIconNum; i++) {
            itemLeft = (int) (startX + i * centerDistance);
            if (itemLeft >= leftBorder && itemLeft < rightBorder) {
                currentIndex = i;
                break;
            }
        }
        return Math.abs(itemLeft - leftBorder);
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

    public void setImages(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            if (null != bitmapList.get(i)) {
                ((ImageView) getChildAt(i)).setImageBitmap(bitmapList.get(i));
            }
        }
    }
}
