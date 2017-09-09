package com.example.administrator.baseproject.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.baseproject.R;

/**
 * Created by Administrator on 2017/9/4.
 */

public class PullZoomVG extends LinearLayout{

    private Context mContext;

    private ImageView targetView;

    private float nowY;

    private int height,width;

    private float a, b;

    public PullZoomVG(Context context) {
        this(context, null);
    }

    public PullZoomVG(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullZoomVG(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mContext = getContext();
        setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        targetView = new ImageView(mContext);

        targetView.setImageResource(R.drawable.test);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        targetView.setLayoutParams(params);
        targetView.setAdjustViewBounds(true);

        addView(targetView, 0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                nowY = event.getY();
                if(nowY > height)
                    return false;
                break;
            case MotionEvent.ACTION_MOVE:
                if(height == 0){
                    return false;
                }
                float scale = (event.getY() - nowY) / (float) height + 1f;
                if(scale <= 1.0f)
                    scale = 1.0f;
                if(scale >= 2.0f)
                    scale = 2.0f;
                targetView.setScaleType(ImageView.ScaleType.CENTER);
                targetView.setPivotX(width / 2);
                targetView.setPivotY(0);
                targetView.setScaleX(scale);
                targetView.setScaleY(scale);

                float distance = scale * (float) height;

//                float distance = nowY - height;
                if (distance < 0)
                    distance = 0;
                getChildAt(1).setTranslationY(distance - height);
                break;
            case MotionEvent.ACTION_UP:
                targetView.setScaleX(1.0f);
                targetView.setScaleY(1.0f);
                getChildAt(1).setTranslationY(0);
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        height = getChildAt(0).getMeasuredHeight();
        height = getChildAt(1).getTop();
        width = getChildAt(1).getRight();
    }
}
