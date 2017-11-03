package com.example.administrator.baseproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        this.setBackgroundColor(Color.WHITE);
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
        Log.d(TAG, "onMeasure: executed");
        int childCount = getChildCount();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    int x,y;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int) event.getX();
        y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: x -> " + x + "  y -> " + y);
                invalidate();
                return true;
        }
        return false;
    }

    boolean isFirst;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw:  executed");
        if (!isFirst) {
            isFirst = true;
            return;
        }
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        RectF rectF = new RectF(x, y , x + 50, y + 50);
        canvas.drawRect(rectF, paint);
    }
}
