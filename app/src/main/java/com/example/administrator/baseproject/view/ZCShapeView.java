package com.example.administrator.baseproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.baseproject.R;

/**
 * Created by Administrator on 2017/11/2.
 */

public class ZCShapeView extends View {

    private static final int DEFAULT_WIDTH = 200;
    private static final float K = 1.73f;

    private float mWidth;
    private int mColor;

    public ZCShapeView(Context context) {
        super(context);
        mColor = context.getResources().getColor(R.color.orange);
        mWidth = context.getResources().getDimension(R.dimen.ZCShapeViewSize) / 4;
        getAttrs(context, null);
    }

    public ZCShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mColor = context.getResources().getColor(R.color.orange);
        mWidth = context.getResources().getDimension(R.dimen.ZCShapeViewSize) / 4;
        getAttrs(context, attrs);
    }

    public ZCShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mColor = context.getResources().getColor(R.color.orange);
        mWidth = context.getResources().getDimension(R.dimen.ZCShapeViewSize) / 4;
        getAttrs(context, attrs);
    }

    private void getAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.
                obtainStyledAttributes(attributeSet, R.styleable.ZCShapeView);
        mWidth = typedArray.getDimensionPixelSize(R.styleable.ZCShapeView_size, DEFAULT_WIDTH) / 4;
        mColor = typedArray.getColor(R.styleable.ZCShapeView_color, mColor);
        typedArray.recycle();
    }

    private int getWidthHeight(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        int mSize = DEFAULT_WIDTH;
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                mSize = DEFAULT_WIDTH;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                mSize = size;
                break;
        }
        return mSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getWidthHeight(widthMeasureSpec);
        int height = getWidthHeight(heightMeasureSpec);

        int size = width <= height ? width : height;
        mWidth = size / 4;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(mColor);
        Path path = new Path();
        path.moveTo(mWidth, (float) 0.27 * mWidth);
        path.lineTo(3 * mWidth, (float) 0.27 * mWidth);
        path.lineTo(4 * mWidth, mWidth * K + (float) 0.27 * mWidth);
        path.lineTo(3 * mWidth, 2 * mWidth * K + (float) 0.27 * mWidth);
        path.lineTo(mWidth, 2 * mWidth * K + (float) 0.27 * mWidth);
        path.lineTo(0, mWidth * K + (float) 0.27 * mWidth);
        path.lineTo(mWidth, 0 + (float) 0.27 * mWidth);
        path.close();
        canvas.drawPath(path, paint);
    }

    public void setColor(int color) {
        this.mColor = color;
    }
}
