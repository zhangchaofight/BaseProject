package com.example.administrator.baseproject.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import android.widget.LinearLayout;
import com.example.administrator.baseproject.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class OWLodingView extends LinearLayout{

    private static final float K = 1.73f;

    private int dp000;
    private int dp010;
    private int dp050;
    private int dp100;
    private int dp150;
    private int dp200;
    private int dp250;
    private int dp300;

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
        dp000 = 0;
        dp100 = ScreenUtils.dp2px(context,100);
        dp010 = (int) (0.13 * dp100);
        dp050 = (int) (0.5 * dp100);
        dp150 = (int) (1.5 * dp100);
        dp250 = (int) (2.5 * dp100);
        dp300 = 3 * dp100;
        dp200 = 2 * dp100;

        setOrientation(VERTICAL);
        mList = new ArrayList<>();
        ZCShapeView view;
        for (int i = 0; i < 7; i++) {
            view = new ZCShapeView(context);
            LayoutParams params = new LayoutParams(ScreenUtils.dp2px(context, 100),
                    ScreenUtils.dp2px(context, 100));
            view.setLayoutParams(params);
            view.setRotation(90f);
            mList.add(view);
            addView(view);
        }
        initAnimatation();
    }

    // TODO: 2017/11/11 如何控制整个布局的大小？
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(3 * dp100, 3 * dp100);
    }

    private boolean isLayouted = false;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!isLayouted) {
            isLayouted = true;
            mList.get(0).layout(dp050, dp000 + dp010, dp150, dp100 + dp010);
            mList.get(1).layout(dp150, dp000 + dp010, dp250, dp100 + dp010);
            mList.get(5).layout(dp000, dp100, dp100, dp200);
            mList.get(6).layout(dp100, dp100, dp200, dp200);
            mList.get(2).layout(dp200, dp100, dp300, dp200);
            mList.get(4).layout(dp050, dp200 - dp010, dp150, dp300 - dp010);
            mList.get(3).layout(dp150, dp200 - dp010, dp250, dp300 - dp010);
        }
    }

    private boolean isPlaying = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isPlaying) {
                animator.pause();
            } else {
                animator.start();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * index / 7 % 2
     */

    boolean isCircle = false;
    int index = 0;
    private ValueAnimator animator;
    private void initAnimatation () {
        animator = ValueAnimator.ofFloat(1, 0);
        animator.setDuration(300);
        animator.setRepeatCount(-1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (float) valueAnimator.getAnimatedValue();
                if (isCircle) {
                    scale = 1- scale;
                }
                mList.get(index).setScaleX(scale);
                mList.get(index).setScaleY(scale);
                mList.get(index).setAlpha(scale);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                index = (index + 1) % 7;
                if (index == 0) {
                    isCircle = !isCircle;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mList.get(index).setScaleX(isCircle ? 1 : 0);
                mList.get(index).setScaleY(isCircle ? 1 : 0);
                mList.get(index).setAlpha(isCircle ? 1 : 0);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mList.get(index).setScaleX(!isCircle ? 1 : 0);
                mList.get(index).setScaleY(!isCircle ? 1 : 0);
                mList.get(index).setAlpha(!isCircle ? 1 : 0);
            }
        });
    }

    // TODO: 2017/11/11 动画控制方法待实现？
    public void play() {
        if (animator != null) {
            animator.start();
        }
    }

    public void pause() {
        if (animator != null) {
            animator.pause();
        }
    }

    public void resume() {
        if (animator != null) {
            animator.resume();
        }
    }

    public void end() {
        if (animator != null) {
            animator.end();
        }
    }

    public void cancel() {
        if (animator != null) {
            animator.cancel();
        }
    }
}
