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

/**
 * Created by Administrator on 2017/11/6.
 */

public class OWLodingView extends LinearLayout{

    private final int STATUS_ORIGIN = 0;
    private final int STATUS_PLAYING = 1;
    private final int STATUS_PAUSE = 2;
    private final int STATUS_END = 3;

    private ValueAnimator animator;

    private int status = 0;
    private int index = 0;

    private int dp000;
    private int dp010;
    private int dp050;
    private int dp100;
    private int dp150;
    private int dp200;
    private int dp250;
    private int dp300;

    private boolean isLayouted = false;
    private boolean isCircle = false;

    public OWLodingView(Context context) {
        this(context,null);
    }

    public OWLodingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
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
        ZCShapeView view;
        LayoutParams params = new LayoutParams(ScreenUtils.dp2px(context, 100),
                ScreenUtils.dp2px(context, 100));
        for (int i = 0; i < 7; i++) {
            view = new ZCShapeView(context);
            view.setLayoutParams(params);
            view.setRotation(90f);
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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!isLayouted) {
            isLayouted = true;
            getChildAt(0).layout(dp050, dp000 + dp010, dp150, dp100 + dp010);
            getChildAt(1).layout(dp150, dp000 + dp010, dp250, dp100 + dp010);
            getChildAt(5).layout(dp000, dp100, dp100, dp200);
            getChildAt(6).layout(dp100, dp100, dp200, dp200);
            getChildAt(2).layout(dp200, dp100, dp300, dp200);
            getChildAt(4).layout(dp050, dp200 - dp010, dp150, dp300 - dp010);
            getChildAt(3).layout(dp150, dp200 - dp010, dp250, dp300 - dp010);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (status) {
                case STATUS_ORIGIN:
                    play();
                    break;
                case STATUS_PLAYING:
                    pause();
                    break;
                case STATUS_PAUSE:
                    resume();
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void initAnimatation () {
        animator = ValueAnimator.ofFloat(1, 0);
        animator.setDuration(400);
        animator.setRepeatCount(-1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (float) valueAnimator.getAnimatedValue();
                if (isCircle) {
                    scale = 1- scale;
                }
                changeScaleAlpha(scale);
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
        });
    }

    private void changeScaleAlpha(float value) {
        getChildAt(index).setScaleX(value);
        getChildAt(index).setScaleY(value);
        getChildAt(index).setAlpha(value);
    }

    public void play() {
        if (animator != null) {
            animator.start();
            status = STATUS_PLAYING;
        }
    }

    public void pause() {
        if (animator != null) {
            animator.pause();
            status = STATUS_PAUSE;
        }
    }

    public void resume() {
        if (animator != null) {
            animator.resume();
            status = STATUS_PLAYING;
        }
    }

    public void end() {
        if (animator != null) {
            animator.end();
            status = STATUS_END;
        }
    }
}
