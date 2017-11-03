package com.example.administrator.baseproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/2.
 */

public class ZCShapeView extends View {

    public ZCShapeView(Context context) {
        super(context);
    }

    public ZCShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZCShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        canvas.drawCircle(0, 0, 20, paint);
    }
}
