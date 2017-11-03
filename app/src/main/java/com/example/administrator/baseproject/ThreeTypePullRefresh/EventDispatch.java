package com.example.administrator.baseproject.ThreeTypePullRefresh;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

import com.example.administrator.baseproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */

public class EventDispatch extends ViewGroup{

    private final int STATUS_START = 0;
    private final int STATUS_NORMAL = 1;
    private final int STATUS_HIDE_HEADER = 2;
    private final int STATUS_RELEASE_REFRESH = 3;
    private final int STATUS_REFRESHING = 4;

    private final int REFRESH_DISTANCE = 200;
    private final int MAX_DISTANCE = 500;

    private int status = STATUS_START;

    public EventDispatch(Context context) {
        this(context, null);
    }

    public EventDispatch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventDispatch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Scroller mScroller;
    private void init() {
        mScroller = new Scroller(mContext);
        addHeader();
        addListView();
    }

    private View mHeader;
    private final int HEADER_HEIGHT = (int) getResources().getDimension(R.dimen.hide_header_height);
    private void addHeader() {
        mHeader = new View(getContext());
        mHeader.setBackgroundColor(Color.GRAY);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                -HEADER_HEIGHT);
        addView(mHeader, params);
    }

    private Context mContext = getContext();
    private void addListView() {
        ListView lv = new ListView(mContext);
        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lv.setLayoutParams(params0);
        List<String> list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            list.add("no: " + i);
        }
        lv.setAdapter(new ArrayAdapter<String>
                (mContext, android.R.layout.simple_expandable_list_item_1, list));
        addView(lv, 1);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
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

    private int mTotalHeight = 0;
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childCount = getChildCount();
        View child;
        for (int index = 0; index < childCount; index++) {
            child = getChildAt(index);
            if (child == mHeader) {
                child.layout(0, child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
            } else {
                child.layout(0, mTotalHeight, child.getMeasuredWidth(),
                        child.getMeasuredHeight() + mTotalHeight);
                mTotalHeight += child.getMeasuredHeight();
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isIntercept;
    }

    private boolean isMoving;
    private int lastY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isMoving = true;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int move = lastY - y;
                scrollBy(0, move);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isMoving = false;
                scrollTo(0 , 0);
                break;
        }
        lastY = y;
        return true;
    }

    //orientation true->down   false ->up
    private boolean isIntercept = false;
    private boolean setIsIntercept(boolean orientation) {
        switch (status) {
            case STATUS_START:
                isIntercept = orientation;
                break;
            case STATUS_NORMAL:
                isIntercept = false;
                break;
            case STATUS_HIDE_HEADER:
            case STATUS_RELEASE_REFRESH:
            case STATUS_REFRESHING:
                isIntercept = true;
                break;
            default:
                break;
        }
        return isIntercept;
    }

    private int getLVFirstItemTop() {
        int top = 0, pos, itemHeight;
        View child = getChildAt(1);
        AbsListView lv = (AbsListView) child;
        pos = lv.getFirstVisiblePosition();
        top = lv.getChildAt(0).getTop();
        itemHeight = lv.getChildAt(0).getMeasuredHeight();
        return pos * itemHeight - top;
    }

    private boolean isLVTopVisible() {
        return getLVFirstItemTop() == 0 ? true : false;
    }

    private void setStatus(int status) {
        this.status = status;
    }

    private int getMoveDistance(int move, int top) {
        int result = 0;
        if (top >= MAX_DISTANCE && move > 0){
            result = 0;
        }
        return result;
    }
}
