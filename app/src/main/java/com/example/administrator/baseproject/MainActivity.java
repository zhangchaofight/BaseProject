package com.example.administrator.baseproject;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.baseproject.view.IconSelecterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private EditText editText;
//    private TextView textView;
//    private LinearLayout root;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        init();
//    }

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


    final String TAG = MainActivity.class.getSimpleName();
    private IconSelecterView selecterView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        selecterView = (IconSelecterView) findViewById(R.id.icon_selector);
//        List<Bitmap> list = new ArrayList<>();
//        Bitmap b0 = BitmapFactory.decodeResource(getResources(), R.drawable.a);
//        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.b);
//        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.c);
//        Bitmap b3 = BitmapFactory.decodeResource(getResources(), R.drawable.d);
//        Bitmap b4 = BitmapFactory.decodeResource(getResources(), R.drawable.e);
//        list.add(b0);
//        list.add(b1);
//        list.add(b2);
//        list.add(b3);
//        list.add(b4);
//        Log.d(TAG, "onCreate: " + (selecterView == null));
//        selecterView.setImages(list);
    }

//        @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
}
