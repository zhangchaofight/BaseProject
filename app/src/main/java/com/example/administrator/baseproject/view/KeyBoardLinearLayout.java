package com.example.administrator.baseproject.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/11/18.
 * 带监控键盘弹出和隐藏的LinearLayout
 * 回调接口OnKeyBoardChangeListener
 * activity的windowSoftInputMode属性必须为adjustResize
 */

public class KeyBoardLinearLayout  extends LinearLayout {

    private final String TAG = KeyBoardLinearLayout.class.getSimpleName();

    private int height;

    private OnKeyBoardChangeListener onKeyBoardChangeListener;

    public KeyBoardLinearLayout(Context context) {
        super(context);
        init();
    }

    public KeyBoardLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KeyBoardLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        height = dm.heightPixels;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (null == onKeyBoardChangeListener) {
            return;
        }
        Log.d(TAG, "onSizeChanged: " + h + " oldh" + oldh);
        if (h - oldh > 0 && h - oldh < height / 2) {
            Log.d(TAG, "onSizeChanged: hide");
            onKeyBoardChangeListener.onKeyBoardHide();
        } else if (h - oldh < 0 && h - oldh > -height / 2) {
            Log.d(TAG, "onSizeChanged: show");
            onKeyBoardChangeListener.onKeyBoardShow();
        }
    }

    public void setOnKeyBoardChangeListener(OnKeyBoardChangeListener onKeyBoardChangeListener) {
        this.onKeyBoardChangeListener = onKeyBoardChangeListener;
    }

    public interface OnKeyBoardChangeListener{

        void onKeyBoardShow();

        void onKeyBoardHide();
    }
}