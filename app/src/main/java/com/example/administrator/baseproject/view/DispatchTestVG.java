package com.example.administrator.baseproject.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/2.
 */

public class DispatchTestVG extends ViewGroup{

    private static final String TAG = "eyuzainali";

    public DispatchTestVG(Context context) {
        super(context);
        initView();
    }

    public DispatchTestVG(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DispatchTestVG(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        Button button = new Button(getContext());
        button.setBackgroundColor(Color.BLUE);
        button.setText("CLICK");
        button.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(500, 300);
        button .setLayoutParams(params);
        addView(button, 0);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ZCToast.showToast(getContext(), "Button click!");
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        view.layout(200, 200, 200 +
                view.getMeasuredWidth(), 200 + view.getMeasuredHeight());
        int i = 0;
        if (getChildCount() > 3) {
            i = getChildCount() -2;
        }
        Log.d(TAG, "onLayout: " + getChildAt(i).getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: x -> " + x + "  y -> " + y);
                drawPicture(x, y);
                return true;
        }
        return false;
    }

    private void drawPicture(int x, int y) {
//        ZCShapeView view = new ZCShapeView(getContext());
//        DispatchTestVG.this.addView(view);
//        view.layout(x, y, x + view.getMeasuredWidth(), y + view.getMeasuredHeight());

        TextView tv = new TextView(getContext());
        tv.setText("aaaa");
        tv.setBackgroundColor(Color.BLACK);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        addView(tv, params);
//        tv.layout(x, y, x + tv.getMeasuredWidth(), y + tv.getMeasuredHeight());


    }
}
