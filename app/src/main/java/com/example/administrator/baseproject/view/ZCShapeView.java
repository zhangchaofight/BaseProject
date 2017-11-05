package com.example.administrator.baseproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/2.
 */

public class ZCShapeView extends View {

    private float width = 60f;
    private float k = 1.73f;

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
        Path path = new Path();
        path.moveTo(width, 0);
        path.lineTo(3 * width, 0);
        path.lineTo(4 * width, width * k);
        path.lineTo(3 * width, 2 * width * k);
        path.lineTo(width, 2 * width * k);
        path.lineTo(0, width * k);
        path.lineTo(width, 0);
        path.close();
        canvas.drawPath(path, paint);
    }
}
