package com.example.administrator.baseproject;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        init();
    }

//    private void init() {
//        editText = (EditText) findViewById(R.id.button_test);
//        textView = (TextView) findViewById(R.id.title_test);
//        root = (LinearLayout) findViewById(R.id.root_linear_layout);
//    }
//
//    private void playAnimation() {
//        final int distance = textView.getMeasuredHeight();
//        ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
//        animator.setDuration(500);
//        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
//                textView.getLayoutParams();
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float value = (float) valueAnimator.getAnimatedValue();
//                textView.setScaleX(value);
//                textView.setScaleY(value);
//                textView.setAlpha((int) (value * 255));
//                params.topMargin = (int) (distance * (value - 1));
//                textView.setLayoutParams(params);
//            }
//        });
//        animator.start();
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        playAnimation();
//        editText.clearFocus();
//        root.requestFocus();
//        return super.onTouchEvent(event);
//    }
}
