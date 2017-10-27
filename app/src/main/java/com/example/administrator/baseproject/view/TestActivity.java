package com.example.administrator.baseproject.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.example.administrator.baseproject.R;

/**
 * 用来测试LayoutParams与ValueAnimator组合
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        addHeader();
    }

    private View mHeader;
    private boolean canTouch = true;
    LinearLayout.LayoutParams params;
    private void addHeader() {
        mHeader = new View(this);
        mHeader.setBackgroundColor(Color.GRAY);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        params.topMargin = 0;
        LinearLayout layout = (LinearLayout) findViewById(R.id.test_linear_layout);
        layout.addView(mHeader, params);
        mHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canTouch) {
                    showAnimation();
                }
            }
        });
    }

    private void showAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 1000);
        animator.setTarget(mHeader);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int loction = (int) valueAnimator.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)
                        mHeader.getLayoutParams();
                params.topMargin = loction;
                mHeader.setLayoutParams(params);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                canTouch = false;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                canTouch = true;
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)
                        mHeader.getLayoutParams();
                params.topMargin = 0;
                mHeader.setLayoutParams(params);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }
}