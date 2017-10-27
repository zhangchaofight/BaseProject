package com.example.administrator.baseproject.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.administrator.baseproject.R;

/**
 * Created by Administrator on 2017/9/4.
 */

public class PullDownVG extends LinearLayout{

    private final String TAG = "daidai";

    private final String TOUCH = "touch";

    private final int TOOBAR_HEIGHT = 56;

    private Context mContext;

    private Scroller mScroller;

    private int nowY, lastY, moveY, headerHeight = 300;

    public PullDownVG(Context context) {
        this(context, null);
    }

    public PullDownVG(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public PullDownVG(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        Log.v(TAG,"PullDownVG");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.v(TAG,"onFinishInflate");

        //try to add a header
        TextView tv = new TextView(mContext);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        tv.setLayoutParams(params);
        tv.setText("laerla!");
        tv.setTextSize(30);
        tv.setBackgroundColor(getResources().getColor(R.color.green));
        tv.setGravity(Gravity.CENTER);
        addView(tv, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        hide();
        Log.v(TAG,"onLayout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.v(TAG,"onMeasure");
    }

    public void init(){
        mContext = getContext();
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new Scroller(mContext);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        nowY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = nowY - lastY;

                if(moveY < 0)
                    moveY /= 3;
                else{
                    int loc[] = new int[2];
                    //获取第一个view的top位置
                    getChildAt(0).getLocationOnScreen(loc);
                    int y = loc[1];
                    if(y > TOOBAR_HEIGHT)
                        moveY = 0;
                }
                scrollBy(0, - moveY);
                break;
            case MotionEvent.ACTION_UP:
                hide();
                break;

        }
        lastY = nowY;
        return true;
    }

    private void hide(){
        scrollTo(0, headerHeight);
    }

    public void setHeader(View view){
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        view.setLayoutParams(params);
        addView(view, 0);
    }

    /**判断是否可以下拉
     * 当第二个view为空或者不是listview的子类时，阻断事件的分发，进行下拉操作
     * 当第三个view为listview的子类时，如果可以下拉不阻断，否则阻断
     */
    private boolean canScrollDown(){
        View view = getChildAt(1);
        if(view == null)
            return true;
        if(view instanceof AbsListView){
            if(view.canScrollVertically(1)){
                return false;
            }else{
                return true;
            }
        }else {
            return true;
        }
    }

    interface PullDownHeader{

        void onPulling();

        void onLoading();

        void onRelease();
    }
}
